package converters;

public class MovieConverter extends FileNamesConverter {
    public MovieConverter(String name) {
        this.fileName = name;
        this.splittedFileName = name.split("\\.");
        this.name = splittedFileName[0];
        this.episode = "";
    }
}
