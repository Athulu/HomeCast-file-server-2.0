package converters;

public class DefaultConverter extends FileNamesConverter {
    public DefaultConverter(String name) {
        this.fileName = name;
        this.splittedFileName = name.split("\\.");
        this.name = name;
        this.episode = "";
    }
}
