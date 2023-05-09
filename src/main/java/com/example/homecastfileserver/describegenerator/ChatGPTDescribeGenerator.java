package com.example.homecastfileserver.describegenerator;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import java.time.Duration;
import java.util.List;


public class ChatGPTDescribeGenerator implements DescribeGenerator{
    OpenAiService service;

    public ChatGPTDescribeGenerator() {
        service = new OpenAiService("token", Duration.ofSeconds(30));
    }

    @Override
    public String getDescription(int season, int episode, String series) {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", "Krótki opis odcinka numer " + episode + " z sezonu numer " + season + " \"" + series + "\" nie zdradzający fabuły")))
                .model("gpt-3.5-turbo")
                .build();
        List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();

        StringBuilder stringBuilder = new StringBuilder();
        choices.stream()
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent)
                .forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}
