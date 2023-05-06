import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

public class ThumbnailGenerator {
    public static final String MAIN_DIRECTORY = "C:\\HomeCast\\";
    public static void generateThumbnails() {
        int frameNumber = 100;
        DirectoryStream.Filter<Path> filter = file -> {
            return file.toString().endsWith(".mp4") || file.toString().endsWith(".MP4")
                    || file.toString().endsWith(".mov") || file.toString().endsWith(".MOV");
        };
        Path dirName = Paths.get(MAIN_DIRECTORY + "mp4");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirName, filter)) {
            stream.forEach(path -> {
                try {
                    Picture picture = FrameGrab.getFrameFromFile(
                            new File(path.toString()), frameNumber);
                    BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
                    bufferedImage = Scalr.resize(bufferedImage, 480);
                    ImageIO.write(bufferedImage, "png", new File(
                            MAIN_DIRECTORY + "images\\" + path.getFileName().toString().replace(".mp4", "") + "480x270.png"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}