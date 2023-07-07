package com.example.homecastfileserver.describegenerator;

import com.example.homecastfileserver.converters.FileNamesConverter;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import java.time.Duration;
import java.util.List;


public class ChatGPTDescribeGenerator implements DescribeGenerator{
    OpenAiService service;

    public ChatGPTDescribeGenerator() {
        service = new OpenAiService("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", Duration.ofSeconds(30));
    }

    @Override
    public String getDescription(FileNamesConverter converter) {
        int season = Integer.parseInt(converter.getEpisode().substring(1,3));
        int episode = Integer.parseInt(converter.getEpisode().substring(4,6));
        String series = converter.getName();
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
