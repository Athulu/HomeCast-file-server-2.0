package com.example.homecastfileserver.converters;

public class CustomSeriesConverter extends FileNamesConverter {
    private CustomSeriesConverter(String title, String episode) {
        super(title, episode);
    }

    public static CustomSeriesConverter create(String name){
        String[] splittedFileName = name.split("\\.");
        String title = splittedFileName[1];
        String episode = splittedFileName[0];
        return new CustomSeriesConverter(title, episode);
    }

    @Override
    public String toString() {
        return "title: " + this.title + "\nepisode: " + this.episode;
    }

    @Override
    public String generateChatMessageForDescription() {
        String seasonNumber = episode.substring(1, 3);
        String episodeNumber = episode.substring(4, 6);
        return "Krótki opis odcinka numer " + episodeNumber + " z sezonu numer " + seasonNumber + " \"" + title + "\" nie zdradzający fabuły";
    }
}
