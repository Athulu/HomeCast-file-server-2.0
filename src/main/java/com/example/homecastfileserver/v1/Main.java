public class Main {
    public static void main(String[] args) {
        ChatGPTDescribeGenerator chatGPT = new ChatGPTDescribeGenerator();
        JsonFileGenerator jsonFileGenerator = new JsonFileGenerator(chatGPT);
        jsonFileGenerator.createJsonFile();
        ThumbnailGenerator.generateThumbnails();
    }
}