package com.koscom.cexpert.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koscom.cexpert.dto.*;
import com.koscom.cexpert.exception.CommonException;
import com.koscom.cexpert.model.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor
public class OpenAiService implements LLMService {
    private final StockService stockService;
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    static final String ETC = "나머지";

    @Override
    public List<ClassificationResponse> classifyStocks(ClassificationRequest request) {
        List<Stock> stockList = stockService.getAllStocksByUserId(request.getUserId());

        Prompt prompt = getPrompt(stockList, request.getKeyword());
        log.info("### prompt message :== {}", prompt);

        String res = callChatApi(prompt);
        log.info("### chat api res :== {}", res);
        Map<String, LLMStockDto> resMap = resToMap(res);

        return stockList.stream().map(s -> ClassificationResponse.builder()
                        .ticker(s.getTicker())
                        .averagePurchasePrice(s.getAveragePurchasePrice())
                        .quantity(s.getQuantity())
                        .newCategory(resMap.get(s.getTicker()).getNewCategory())
                        .reason(resMap.get(s.getTicker()).getReason())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<RebalanceResponse> rebalanceCategories(List<RebalanceRequest> req) {
        Prompt prompt = getRebalancePrompt(req);
        log.info("### prompt message :== {}", prompt);

        String res = callChatApi(prompt);
        log.info("### chat api res :== {}", res);

        ObjectMapper objectMapper = new ObjectMapper();
        List<RebalanceResponse> responseList;
        try {
            responseList = objectMapper.readValue(res, objectMapper.getTypeFactory().constructCollectionType(List.class, RebalanceResponse.class));
        } catch (Exception e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, "!! LLM response parsing error");
        }

        return responseList;

    }

    private Prompt getPrompt(List<Stock> stockList, String keyword) {
        String command = """
                1. {stocks}
                2. {keyword}
                
                1번에 대한 주식들을 2번 키워드로 분류해줘.
                1번의 각 주식이 훨씬 더 다양하게 분류될 수록 좋아.
                
                1번와 2번이 위와 같이 주어진 경우
                key가 1번이고 2번이 분류키워드야.
                value는
                \\{"newCategory" : "Apple", "reason" : "삼성전자는 "\\}
                으로 너가 분류햔 이유도 reason value로 적어줘
                
                응답은 json으로만 줘 바로 json으로 리포맷팅할거야 아래가 예시야
                \\{"Apple": \\{"newCategory" : "AI", "reason" : "삼성전자는 "\\}, "Microsoft":\\{"newCategory" : "AI", "reason" : "삼성전자는 "\\}, "Pfizer": \\{"newCategory" : "기타", "reason" : "삼성전자는 "\\}\\}
                
                1번에 있는 값들로만 응답을 줘
               
                """;

        PromptTemplate template = new PromptTemplate(command);

        String rawStocks = stockList.stream()
                .map(Stock::getTicker)
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(", "));

        if (stockList.isEmpty()) {
            rawStocks = "";
        }

        Message message = template.createMessage(Map.of("stocks", rawStocks, "keyword", keyword));
        return new Prompt(List.of(message));

    }

    private Prompt getRebalancePrompt(List<RebalanceRequest> categories) {
        String command = """
                [{categories}]
                위 배열 내의 원소들은 json 객체를 의미해.
                각 객체의, "category" 속성은 "주식의 분류"를 의미하고, "percentage" 속성은 "해당 분류의 주식이, 전체 보유 주식 중에서 차지하는 비율"을 퍼센트로 나타낸 것을 의미해.
                즉, 이건 나의 주식 포트폴리오야.
                
                너가 할 일은, 각 객체의 "category" 속성과 "percentage" 속성을 확인해서, 주식의 분류의 비율을 어떻게 조정할지 판단하는 거야.
                각 분류에 대해서 다음 척도의 점수를 매겨줘.
                1: 비율을 많이 줄인다.
                2: 비율을 약간 줄인다.
                3: 현재 비율이 적당하다.
                4: 비율을 약간 늘린다.
                5: 비율을 많이 늘린다.
                
                판단 과정에서 다음의 주의사항을 적용해줘.
                가장 중요한 건, "가장 최근의 주식 시황과 최신 뉴스를 기반으로 평가하는거야."
                비율이 늘어나는 분류가 있다면, 비율이 줄어드는 분류도 있어야 해. 그리고 비율이 줄어드는 분류가 있다면, 비율이 늘어나는 분류도 있어야 해.
                
                응답에는 json만 포함해줘.
                바로 json으로 변환할거야.
                
                json 배열 내 객체에는, "category" 속성과 "level" 속성을 입력해줘.
                여기서 "category" 속성에는 주식의 분류를 입력하고, "level" 속성에는 해당 분류에 대해 너가 판단한 점수를 입력해줘.
                
                아래가 예시야.
                [\\{"category": "반도체", "level": 3\\}, \\{"category": "부동산", "level": 1\\}, \\{"category": "방산", "level": 5\\}, \\{"category": "식료품", "level": 2\\}, \\{"category": "바이오", "level": 4\\}]
                """;

        PromptTemplate template = new PromptTemplate(command);

        String rawCategories = categories.stream()
                .map(s -> "{\"category\": \"" + s.getCategory() + "\", \"percentage\": " + s.getPercentage() + "}")
                .collect(Collectors.joining(", "));

        if (categories.isEmpty()) {
            rawCategories = "";
        }

        Message message = template.createMessage(Map.of("categories", rawCategories));
        return new Prompt(List.of(message));
    }



    private String callChatApi(Prompt prompt) {
        String res = chatClient.prompt(prompt).call().content();
        if (res != null) {
            res = res.replace("json", "");
            res = res.replace("```", "");
        }
        return res;
    }

    private Map<String, LLMStockDto> resToMap(String res) {
        try {
            return objectMapper.readValue(res, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, "!! LLM response parsing error");
        }
    }
}
