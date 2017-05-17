package ru.sbt.ds;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

import static java.nio.file.Files.readAllBytes;

@RestController
public class PhotoController {
    @RequestMapping(value = "/img", produces = "image/jpeg")
    public byte[] getById(long id) throws IOException {
        return readAllBytes(new File("photo-service-green/src/main/resources/Koala.jpg").toPath());
    }
}
