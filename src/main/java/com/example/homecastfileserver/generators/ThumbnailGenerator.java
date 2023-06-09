package com.example.homecastfileserver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

import lombok.NoArgsConstructor;
import org.imgscalr.Scalr;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ThumbnailGenerator {
    public static final String MAIN_DIRECTORY = "C:\\HomeCast\\";
    private static final int frameNumber = 100;

    public void generateThumbnails() {

        DirectoryStream.Filter<Path> filter = file -> {
            return file.toString().endsWith(".mp4") || file.toString().endsWith(".MP4")
                    || file.toString().endsWith(".mov") || file.toString().endsWith(".MOV");
        };
        Path dirName = Paths.get(MAIN_DIRECTORY + "mp4");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirName, filter)) {
            stream.forEach(this::generateThumbnail);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void generateThumbnail(Path path) {
        try {
            waitForFileAvailability(path);
            Picture picture = FrameGrab.getFrameFromFile(new File(path.toString()), frameNumber);
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
            bufferedImage = Scalr.resize(bufferedImage, 480);
            ImageIO.write(bufferedImage, "png", new File(
                    MAIN_DIRECTORY + "images\\" + path.getFileName().toString().replace(".mp4", "") + "480x270.png"));
        } catch (Exception e1) {
//            e1.printStackTrace();
            System.out.println(e1);
        }
    }

    public void waitForFileAvailability(Path path) {
        try {
            File file = new File(path.toString());
            while (!file.canRead()) {
                System.out.println("Plik jest już używany przez inny proces");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}