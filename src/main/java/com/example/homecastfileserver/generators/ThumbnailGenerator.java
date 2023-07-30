package com.example.homecastfileserver.generators;

import com.example.homecastfileserver.configs.HomeCastConfig;
import lombok.AllArgsConstructor;
import org.imgscalr.Scalr;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@AllArgsConstructor
public class ThumbnailGenerator {
    private static final int frameNumber = 100;
    private final HomeCastConfig homeCastConfig;

    public void generateThumbnails() {

        DirectoryStream.Filter<Path> filter = file -> {
            return file.toString().endsWith(".mp4") || file.toString().endsWith(".MP4");
        };
        Path dirName = Paths.get(homeCastConfig.getMp4dir());
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
                    homeCastConfig.getImagesdir() + path.getFileName().toString().replace(".mp4", "") + "480x270.png"));
        } catch (Exception e1) {
            e1.printStackTrace();
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