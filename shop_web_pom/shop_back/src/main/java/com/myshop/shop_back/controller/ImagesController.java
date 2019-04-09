package com.myshop.shop_back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/image")
public class ImagesController {

    private final String UPLOADER_PATH="F:\\shop_images";

    @RequestMapping("/uploader")
    @ResponseBody
    public String uploader(MultipartFile file) throws IOException {
        String fileName= UUID.randomUUID()+file.getOriginalFilename();
        File f=new File(UPLOADER_PATH);
        if(!f.exists()){
            f.mkdirs();
        }
        file.transferTo(new File(f, fileName));
        return null;
    }
}
