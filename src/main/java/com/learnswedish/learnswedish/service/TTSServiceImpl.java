package com.learnswedish.learnswedish.service;

import com.learnswedish.learnswedish.utils.voicerss.*;
import org.springframework.stereotype.Service;

@Service
public class TTSServiceImpl implements TTSService{

    private static final VoiceProvider tts = new VoiceProvider("4fe3db9cda364051999f3903a58f3afc");

    @Override
    public String getTTSFromVoiceRSS(String text) throws Exception {
        VoiceParameters params = new VoiceParameters(text, Languages.Hungarian);
        params.setVoice("Mate");
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(true);
        params.setSSML(false);
        params.setRate(0);

        String voice = tts.speech(params);
        return voice;
    }
}
