package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.business.ImageService;
import de.dhbw.softwareengineering.digitaljournal.business.JournalService;
import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

    private final ImageService imageService;
    private final JournalService journalService;

    public FileController(ImageService imageService, JournalService journalService) {
        this.imageService = imageService;
        this.journalService = journalService;
    }

    @PostMapping("/image")
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpSession session) {
        List<byte[]> images = new ArrayList<>();

        Object sessionAttribute = session.getAttribute(Constants.SESSION_IMAGES);

        if (sessionAttribute != null) {
            images = (List<byte[]>) sessionAttribute;
        }

        String mimeType = file.getContentType();
        String type = mimeType.split("/")[0];
        if (type.equalsIgnoreCase("image")) {
            try {
                images.add(file.getBytes());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Image expected, actual: [\" + type + \"]");
        }

        if (!images.isEmpty()) {
            session.setAttribute(Constants.SESSION_IMAGES, images);
        }

        return "_emptyresponse";
    }

    @GetMapping("/image/{journalId}/{image}")
    @ResponseBody
    public byte[] getImage(@PathVariable String journalId, @PathVariable int image) {
        return imageService.getImageByJournalId((journalId), image);
    }

    @PostMapping("/image/delete/{journalId}/{image}")
    public String deleteImage(@PathVariable String journalId, @PathVariable int image) {
        imageService.deleteImageById((journalId), image);

        return Constants.REDIRECT_JOURNAL + "/edit/" + journalId;
    }

}
