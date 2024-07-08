package com.yarnyard.aibot_service.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/ai")
public class AIBotController {
    @GetMapping("/generate")
    public String generateText(@RequestParam String inputText) throws IOException, InterruptedException {
        //ProcessBuilder processBuilder = new ProcessBuilder(
        //        "python",
        //        "aibot-service/src/main/resources/ai.py",
        //        inputText
        //).redirectOutput(new File("aibot-service/src/main/resources/py_output.log"));
        //Process process = processBuilder.start();
//
        //BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //String generatedText = reader.readLine();
        //process.waitFor();

        String pythonScriptPath = "aibot-service/src/main/resources/ai.py";
        String[] cmd = new String[3];
        cmd[0] = "python";
        cmd[1] = pythonScriptPath;
        cmd[2] = inputText;
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(cmd);

        BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String generatedText = reader.readLine();
        pr.waitFor();

        return generatedText;
    }
}
