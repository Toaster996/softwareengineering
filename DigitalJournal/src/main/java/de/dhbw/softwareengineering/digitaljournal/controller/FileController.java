package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.ImageService;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    private final ImageService imageService;

    public FileController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/image")
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpSession session){
        List<byte[]> images = new ArrayList<>();

        Object sessionAttribute = session.getAttribute(Constants.SESSION_IMAGES);

        if(sessionAttribute != null){
            images = (List<byte[]>) sessionAttribute;
        }

        String mimeType = file.getContentType();
        String type = mimeType.split("/")[0];
        if (type.equalsIgnoreCase("image")) {
            try {
                images.add(file.getBytes());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Image expected, actual: [" + type + "]");
        }

        if(images.size() > 0){
            session.setAttribute(Constants.SESSION_IMAGES, images);
        }

        return "_emptyresponse";
    }

    @GetMapping("/image/{journalId}/{image}")
    @ResponseBody
    public byte[] helloWorld(@PathVariable String journalId, @PathVariable int image) {
        return imageService.getImageByJournalId(journalId,image);
    }

}
