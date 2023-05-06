package converters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class FileNamesConverter {
    String fileName;
    String[] splittedFileName;
    String name;
    String episode;
}
