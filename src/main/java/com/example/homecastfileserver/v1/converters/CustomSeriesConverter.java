package converters;

public class CustomSeriesConverter extends FileNamesConverter{
    public CustomSeriesConverter(String name) {
        this.fileName = name;
        this.splittedFileName = name.split("\\.");
        this.name = splittedFileName[1];
        this.episode = splittedFileName[0];
    }
}
