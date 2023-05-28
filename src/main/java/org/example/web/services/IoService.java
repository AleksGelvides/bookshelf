package org.example.web.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class IoService {

    Logger logger = Logger.getLogger(IoService.class);

    public void saveFile(MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(Files.newOutputStream(serverFile.toPath()));
        stream.write(bytes);
        stream.close();

        logger.info("new file saved at: " + serverFile.getAbsolutePath());
    }
}
