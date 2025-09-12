package org.menuservice.api.domain.aimenurecommend.controller;

import lombok.RequiredArgsConstructor;
import org.menuservice.api.common.api.Api;
import org.menuservice.api.domain.aimenurecommend.converter.AiConverter;
import org.menuservice.api.domain.aimenurecommend.model.MenuRecommendation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/ai-menu-recommend")
public class AiMenuRecommendController {

    private final OpenAiChatModel openAiChatModel;
    private final AiConverter aiConverter;

    @ResponseBody
    @PostMapping("/")
    public Api<MenuRecommendation> RecommendMenuWithAi(@RequestBody Api<String> message) {

        SystemMessage systemMessage = new SystemMessage(
                "당신은 외식산업과 식재료에 대한 이해가 높은 카페 전문가입니다." +
                        " 사용자가 제공한 기획서에 부합하는 카페의 새로운 메뉴를 개발하세요." +
                        " 한국어로 답해주세요." +
                        " 가격 단위는 '원'입니다." +
                        " 제작법은 순서대로 앞에 번호를 붙이고, 절차별로 줄을 바꿔야 하고 '\n'으로 표시해주세요.");
        UserMessage userMessage = new UserMessage(message.getBody());
        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        messages.add(userMessage);

        String jsonSchema = aiConverter.getJsonSchema();
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .responseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                .build();

        Prompt prompt = new Prompt(messages, openAiChatOptions);

        String response = openAiChatModel.call(prompt).getResult().getOutput().getText();

        MenuRecommendation recommendation = aiConverter.getResponseFormat(response);

        return Api.OK(recommendation);
    }
}
