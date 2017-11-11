package de.dhbw.softwareengineering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 */

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index (Model m){
        m.addAttribute("someAttributes", "someValue");
        return "index";

    /*
    public void index(HttpServletRequest request, HttpServletResponse response) {


        String filename = request.getRequestURL().toString().replace("http://localhost:8080/", "");

        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource("html2/" + filename).getFile());

            try {
                // get your file as InputStream
                InputStream is = new FileInputStream(file);
                // copy it to response's OutputStream
                org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            } catch (IOException ex) {
                ex.printStackTrace();
            } */

    }
}
