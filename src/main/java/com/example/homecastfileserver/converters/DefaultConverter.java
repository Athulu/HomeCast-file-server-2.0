package com.example.homecastfileserver.converters;

public class DefaultConverter extends FileNamesConverter {
    private DefaultConverter(String title, String episode) {
        super(title, episode);
    }

    public static DefaultConverter create(String fileName){
        return new DefaultConverter(fileName, "");
    }


    @Override
    public String generateChatMessageForDescription() {
        return "Najpierw odgadnij co to za film po nazwie pliku, a potem napisz krótki opis tego filmu nie zdradzający fabuły\n nazwa pliku: " + title;
    }
}
