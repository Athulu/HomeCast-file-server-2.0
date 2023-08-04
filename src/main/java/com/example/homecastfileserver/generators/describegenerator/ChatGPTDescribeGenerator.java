package com.example.homecastfileserver.generators.describegenerator;

import com.example.homecastfileserver.configs.ChatGPTConfig;
import com.example.homecastfileserver.converters.FileNamesConverter;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class ChatGPTDescribeGenerator implements DescribeGenerator {
    OpenAiService openAiService;
    ChatGPTConfig chatGPTConfig;

    public ChatGPTDescribeGenerator(ChatGPTConfig myConfig) {
        this.openAiService = new OpenAiService(myConfig.getToken(), Duration.ofSeconds(30));
        this.chatGPTConfig = myConfig;
    }

    @Override
    public String getDescription(FileNamesConverter converter) {
        String chatMessage = converter.generateChatMessageForDescription();

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", chatMessage)))
                .model("gpt-3.5-turbo")
                .build();
        List<ChatCompletionChoice> choices = openAiService.createChatCompletion(chatCompletionRequest).getChoices();

        StringBuilder stringBuilder = new StringBuilder();
        choices.stream()
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent)
                .forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}
