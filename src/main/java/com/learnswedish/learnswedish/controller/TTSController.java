package com.learnswedish.learnswedish.controller;

import com.learnswedish.learnswedish.service.TTSServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TTSController {

    @Autowired
    private TTSServiceImpl ttsService;

    /**
     * Sends the given string to the VoiceRSS API
     * @param tts
     * @return the BASE64 encoded string of the resulted voice
     * @throws Exception
     */
    @PostMapping("/get-tts")
    public String getTTS(@RequestParam("tts") String tts) throws Exception {
        return ttsService.getTTSFromVoiceRSS(tts);
    }
}