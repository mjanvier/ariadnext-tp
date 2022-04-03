package com.mjanvier.ariadnexttp.controller;

import com.mjanvier.ariadnexttp.service.IdCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
@Controller
@RequestMapping("/idcheck")
public class IdCheckController {

    private final IdCheckService idCheckService;

    @PostMapping
    public String checkId(@RequestParam("file") MultipartFile file) throws IOException {
        final String imageAsString = Base64.getEncoder().encodeToString(file.getBytes());
        if (idCheckService.analyseImage(imageAsString)) {
            return "congratulation";
        } else {
            return "badperson";
        }
    }
}
