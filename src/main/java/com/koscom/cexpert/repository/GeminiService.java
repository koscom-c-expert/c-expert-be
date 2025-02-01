package com.koscom.cexpert.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koscom.cexpert.model.Stock;
import com.koscom.cexpert.service.LLMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: 참고용 코드이므로 나중에 지울 것
@Component
@Slf4j
@RequiredArgsConstructor
public class GeminiService implements LLMService {

    public static final int MAX_CONTEXT_COUNT = 10; // 최대 x개 대화만 기억합니다.

    private static final String USER_ROLE = "user";
    private static final String MODEL_ROLE = "model";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${GEMINI_API_KEY}")
    private String API_URL;

    private static final String role = """
You're Stock-Classification-GPT.
            """;

    /**
     * Gemini API로 요청을 보낸 뒤 응답을 반환합니다. 예외가 발생한 경우 null을 반환합니다.
     * 예외 사유:
     *  - 메시지가 검열되어 컨텐츠가 오지 않은 경우
     */
    @Override
    public Map<String, List<Stock>> classifyStocksByKeyword(List<Stock> stocks, String keyword) {
        /*
        ResponseEntity<String> response = null;
        try {
            // Body
            GeminiRequest geminiRequest = createGeminiRequest(requestMessage, chady, recentMessages);
            String requestBody = objectMapper.writeValueAsString(geminiRequest);

            // Header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Post
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            // Parse response
            JsonNode responseBody = objectMapper.readTree(response.getBody());
            return responseBody.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        } catch (Exception e) {
            if (response != null) {
                log.info("raw response body: {}", response.getBody());
            }
            log.error("Error occurs generating response using Gemini", e);
            return null;
        }
        */
        return new HashMap<>();
    }


    /*
    private GeminiRequest createGeminiRequest(Message requestMessage, Chady chady, List<Message> recentMessages) {
        GeminiRequest geminiRequest = new GeminiRequest();

        // Set contents
        List<MessagePart> contents = new ArrayList<>();
        // 최근 메시지 컨텍스트 턴
        for (Message message : recentMessages) {
            if (message.getRole().equals(Message.MODEL_ROLE)) {
                contents.add(new MessagePart(MODEL_ROLE, message.getContent()));
            } else if (message.getRole().equals(Message.USER_ROLE)) {
                contents.add(new MessagePart(USER_ROLE, message.getContent()));
            }
        }
        // requestMessage 턴
        contents.add(new MessagePart(USER_ROLE, requestMessage.getContent()));
        geminiRequest.setContents(contents);

        // Set systemInstruction
        geminiRequest.setSystemInstruction(new MessagePart(USER_ROLE, createPrompt(chady)));

        return geminiRequest;
    }
    */
}
