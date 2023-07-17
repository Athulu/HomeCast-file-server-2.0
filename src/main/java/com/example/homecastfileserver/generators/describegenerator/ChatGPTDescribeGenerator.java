package com.example.homecastfileserver.generators.describegenerator;

import com.example.homecastfileserver.configs.MyConfig;
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
    OpenAiService service;
    MyConfig myConfig;

    public ChatGPTDescribeGenerator(MyConfig myConfig) {
        service = new OpenAiService(myConfig.getToken(), Duration.ofSeconds(30));
        this.myConfig = myConfig;
    }

    @Override
    public String getDescription(FileNamesConverter converter) {
        String season = converter.getEpisode().substring(1, 3);
        String episode = converter.getEpisode().substring(4, 6);
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
