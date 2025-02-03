package com.koscom.cexpert.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koscom.cexpert.dto.ClassificationRequest;
import com.koscom.cexpert.dto.ClassificationResponse;
import com.koscom.cexpert.dto.LLMStockDto;
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
