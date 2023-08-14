package com.example.homecastfileserver.converters;

public class ShindenConverter extends FileNamesConverter {
    private ShindenConverter(String title, String episode) {
        super(title, episode);
    }

    public static ShindenConverter create(String fileName) {
        fileName = fileName.replace("Shinden.mp4", "");
        String separator = " \\(anime\\) - "; // Uwaga na escape znaku '(' i ')'

        String[] splitArray = fileName.split(separator);

//        String title = splitArray[0] + " -";
        String title = splitArray[0];
        String episode = splitArray[1];

        //splittedEpisode[] = [0] - "Odcinek" | [1] - {numer odcinka} | [2+] - {kolejne słowa tytułu odcinka}
        String[] splittedEpisode = episode.split(" ");
        episode = splittedEpisode[0].toLowerCase() + " " + splittedEpisode[1];

//        if(splittedEpisode[1].length()<2) episode += "0" + splittedEpisode[1];
//        else episode += splittedEpisode[1];

//        for(int i = 2; i < splittedEpisode.length; i++)
//            title += " " + splittedEpisode[i];

        return new ShindenConverter(title, episode);
    }

    @Override
    public String generateChatMessageForDescription() {
        return "Napisz mi krótki opis anime" + title + " nie zdradzający fabuły z " + episode.replace("k", "a");
    }
}
