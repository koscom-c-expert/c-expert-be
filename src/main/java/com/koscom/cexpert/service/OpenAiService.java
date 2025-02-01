package com.koscom.cexpert.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koscom.cexpert.dto.Stock;
import com.koscom.cexpert.dto.StockCategory;
import com.koscom.cexpert.exception.CommonException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor
public class OpenAiService implements LLMService {
    private final ChatClient chatClient;
    @Data
    private class ClassificationResponse {
        private final String stockName;
        private final String stockCategoryName;
    }
    @Override
    public List<Stock> classifyStocks(List<StockCategory> stockCategories, List<String> stocks) {
        Map<String, Integer> categoryMap = new HashMap();
        stockCategories.forEach(s -> categoryMap.put(s.getName(), s.getId()));
        String command = """
                1. [{Stocks}]
                2. [{StockCategories}]
                
                1번은 분류해야 하는 주식들의 배열이고, 2번은 각 주식이 속할 수 있는 카테고리들의 배열이야.
                1번의 각 주식에 대해서, 2번의 카테고리 중 속할 가능성이 높은 하나의 카테고리로 응답해줘.
                1번의 각 주식이 훨씬 더 다양하게 분류될 수록 좋아.
                
                아래는 응답 예시야.
                1. ["Apple", "Microsoft", "Pfizer"]
                2. ["AI 관련 주식", "기타"]
                
                1번와 2번이 위와 같이 주어진 경우
                [\\{"Apple": "AI 관련 주식"\\}, \\{"Microsoft": "AI 관련 주식"\\}, \\{"Pfizer": "기타"\\}]
                와 같이 응답해줘.
                """;
        PromptTemplate template = new PromptTemplate(command);

        String rawStocks = stocks.stream()
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(", "));
        String rawStockCategories = stockCategories.stream()
                .map(s -> "\"" + s.getName() + "\"")
                .collect(Collectors.joining(", "));

        Message message = template.createMessage(Map.of("Stocks", rawStocks, "StockCategories", rawStockCategories));
        Prompt prompt = new Prompt(List.of(message));
        System.out.println(message);

        String res = chatClient.prompt(prompt).call().content();
        System.out.println(res);
        ObjectMapper mapper = new ObjectMapper();
        List<Stock> indexedStocks = new ArrayList();
        try {
            List<Map<String, String>> parsedResult = mapper.readValue(res, new TypeReference<List<Map<String, String>>>(){});
            for (Map<String, String> map : parsedResult) {
                for (Map.Entry<String, String> entry: map.entrySet()) {
                    indexedStocks.add(new Stock(entry.getKey(), categoryMap.get(entry.getValue())));
                }
            }
        } catch (IOException e) {
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR, "LLM error");
        }

        return indexedStocks;

    }
}
