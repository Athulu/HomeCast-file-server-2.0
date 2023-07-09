package com.example.homecastfileserver;

import com.example.homecastfileserver.describegenerator.DescribeGenerator;
import com.example.homecastfileserver.converters.FileNamesConverter;
import com.example.homecastfileserver.describegenerator.EmptyDescribeGenerator;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFileGenerator {
    public static final String HOST_ADRESS = "http://192.168.1.109:8080";
    public static final String JSON_DB_FILE = "C:\\HomeCast\\db.json";
    public static final String MOVIES_DIRECTORY = "C:\\HomeCast\\mp4";
    private static DescribeGenerator describeGenerator;

    public static void main(String[] args) {
        JsonFileGenerator aaa = new JsonFileGenerator(new EmptyDescribeGenerator());
        aaa.createJsonFile();
    }

    public JsonFileGenerator(DescribeGenerator describeGenerator) {
        JsonFileGenerator.describeGenerator = describeGenerator;
    }

    public void createJsonFile(){
        createJsonFileFromString(generateJsonString());
    }

    public void initializeCheckOfChanges(){
        int hashcode = getAllDirFiles().hashCode();
        if(isDirectoryDifferent(hashcode)){
            try{
                PrintWriter zapis = new PrintWriter("hashcode.txt");
                zapis.println(hashcode);
                zapis.close();
            }catch(FileNotFoundException e){
                System.out.println(e);
            }
            createJsonFile();
        }
    }
    private boolean isDirectoryDifferent(int hashcode) {
        String text;

        try{
            BufferedReader brTest = new BufferedReader(new FileReader("hashcode.txt"));
            text = brTest.readLine();
        }catch(Exception e){
            System.err.println("BufferedReader error");
            text = "1";
        }

        if(Integer.parseInt(text) != hashcode)
            return true;

        return false;
    }

    private static List<String> getAllDirFiles() {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream
                     = Files.newDirectoryStream(Paths.get(MOVIES_DIRECTORY))) {
            for (Path path : directoryStream) {
                fileNames.add(path.getFileName().toString());
            }
        } catch (IOException ex) {}
        return fileNames;
    }

    private String generateJsonString() {

        JSONObject jsonString = new JSONObject();
        jsonString.put("name", "Movies");
        jsonString.put("hls", "");
        jsonString.put("dash", "");
        jsonString.put("mp4", HOST_ADRESS+"/mp4/");
        jsonString.put("images", HOST_ADRESS+"/images/");

        JSONObject jsonSourcesString;
        JSONArray jsonVideosArray = new JSONArray();
        String description;

        for (String name: getAllDirFiles()) {
            FileNamesConverter converter = FileNamesConverterFactory.getFileNameConverter(name);

            description = describeGenerator.getDescription(converter);

            JSONObject jsonVideosString = new JSONObject();
            jsonVideosString.put("subtitle", description);
            JSONArray jsonSourcesArray = new JSONArray();
            jsonSourcesString = new JSONObject();
            jsonSourcesString.put("type", "mp4");
            jsonSourcesString.put("mime", "videos/mp4");
            jsonSourcesString.put("url", name);
            jsonSourcesArray.put(jsonSourcesString);

            jsonVideosString.put("sources", jsonSourcesArray);
            jsonVideosString.put("thumb", name.replace(".mp4", "") +"480x270.png");
            jsonVideosString.put("image-480x270", name.replace(".mp4", "") +"480x270.png");
            jsonVideosString.put("image-780x1200", "bbb.png");
            jsonVideosString.put("title", converter.getName());
            jsonVideosString.put("studio", converter.getEpisode());
            jsonVideosString.put("duration", 100);
            jsonVideosArray.put(jsonVideosString);
        }

        jsonString.put("videos", jsonVideosArray);

        JSONArray jsonCategoriesArray = new JSONArray();
        jsonCategoriesArray.put(jsonString);

        JSONObject object = new JSONObject();
        object.put("categories", jsonCategoriesArray);

        return object.toString(4);
    }


    private void createJsonFileFromString(String formattedJsonString) {
        try {
            FileWriter myWriter = new FileWriter(JSON_DB_FILE);
            myWriter.write(formattedJsonString);
            myWriter.close();
            System.out.println("db.json został utworzony");
        } catch (IOException e) {
            System.out.println("Błąd w tworzeniu pliku db.json");
            e.printStackTrace();
        }
    }
}

