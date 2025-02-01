package com.koscom.cexpert.service;

import com.koscom.cexpert.dto.Stock;
import com.koscom.cexpert.dto.StockCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenAiService implements LLMService {
    private final ChatClient chatClient;
    @Override
    public List<Stock> classifyStocks(List<StockCategory> stockCategories, List<String> stocks) {
        String command = """
                1. [{Stocks}]
                2. [{StockCategories}]
                
                1번은 분류해야 하는 주식들의 배열이고, 2번은 각 주식이 속할 수 있는 카테고리들의 배열이야.
                1번의 각 주식에 대해서, 2번의 카테고리 중 속할 가능성이 높은 하나의 카테고리로 응답해줘.
                
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
                .map(s -> "\"" + s.name + "\"")
                .collect(Collectors.joining(", "));

        Message message = template.createMessage(Map.of("Stocks", rawStocks, "StockCategories", rawStockCategories));
        Prompt prompt = new Prompt(List.of(message));
        System.out.println(message);

        String res = chatClient.prompt(prompt).call().content();

        System.out.println(res);

        return new ArrayList<Stock>();
    }
}
