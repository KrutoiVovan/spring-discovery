package ru.sbt.ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhotoClientController {
    @Autowired
    private PhotoServiceClient photoServiceClient;

    @RequestMapping(value = "/getKoala", produces = "image/jpeg")
    public byte[] getKoala() {
        return photoServiceClient.getPhoto(100);
    }
}
