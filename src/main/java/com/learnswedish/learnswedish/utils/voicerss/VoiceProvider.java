package com.learnswedish.learnswedish.utils.voicerss;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class VoiceProvider {
    private ArrayList<com.learnswedish.learnswedish.utils.voicerss.SpeechErrorEventListener> _speechErrorListeners;
    private ArrayList<SpeechDataEventListener> _speechDataListeners;
    private String _apiKey;
    private Boolean _ssl;

    public VoiceProvider(String apiKey) {
        this._speechErrorListeners = new ArrayList();
        this._speechDataListeners = new ArrayList();
        this._apiKey = apiKey;
        this._ssl = false;
    }

    public VoiceProvider(String apiKey, Boolean ssl) {
        this(apiKey);
        this._ssl = ssl;
    }

    public String getApiKey() {
        return this._apiKey;
    }

    public void setApiKey(String value) {
        this._apiKey = value;
    }

    public Boolean getSSL() {
        return this._ssl;
    }

    public void setSSL(Boolean value) {
        this._ssl = value;
    }

    public synchronized void addSpeechErrorEventListener(com.learnswedish.learnswedish.utils.voicerss.SpeechErrorEventListener listener) {
        this._speechErrorListeners.add(listener);
    }

    public synchronized void removeSpeechErrorEventListener(com.learnswedish.learnswedish.utils.voicerss.SpeechErrorEventListener listener) {
        this._speechErrorListeners.remove(listener);
    }

    public synchronized void addSpeechDataEventListener(SpeechDataEventListener listener) {
        this._speechDataListeners.add(listener);
    }

    public synchronized void removeSpeechDataEventListener(SpeechDataEventListener listener) {
        this._speechDataListeners.remove(listener);
    }

    private void handleSpeechError(Exception exception) {
        if (exception != null && this._speechErrorListeners != null) {
            Iterator var3 = this._speechErrorListeners.iterator();

            while(var3.hasNext()) {
                com.learnswedish.learnswedish.utils.voicerss.SpeechErrorEventListener _listener = (com.learnswedish.learnswedish.utils.voicerss.SpeechErrorEventListener)var3.next();
                _listener.handleSpeechErrorEvent(new com.learnswedish.learnswedish.utils.voicerss.SpeechErrorEvent(exception));
            }
        }

    }

    private <T> void handleSpeechData(T data) {
        if (data != null && !data.equals("") && this._speechDataListeners != null) {
            Iterator var3 = this._speechDataListeners.iterator();

            while(var3.hasNext()) {
                SpeechDataEventListener _listener = (SpeechDataEventListener)var3.next();
                _listener.handleSpeechDataEvent(new SpeechDataEvent(data));
            }
        }

    }

    public <T> T speech(VoiceParameters params) throws Exception {
        this.validate(params);
        URL url = new URL((this._ssl ? "https" : "http") + "://api.voicerss.org/");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        conn.setConnectTimeout(60000);
        conn.setDoOutput(true);
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream, "UTF-8"));
        writer.write(this.buildParameters(params));
        writer.close();
        outStream.close();
        if (conn.getResponseCode() != 200) {
            throw new Exception(conn.getResponseMessage());
        } else {
            ByteArrayOutputStream outArray = new ByteArrayOutputStream();
            InputStream inStream = conn.getInputStream();
            byte[] buffer = new byte[4096];
            boolean var9 = true;

            int n;
            while((n = inStream.read(buffer)) > 0) {
                outArray.write(buffer, 0, n);
            }

            byte[] response = outArray.toByteArray();
            inStream.close();
            String responseString = new String(response, "UTF-8");
            if (responseString.indexOf("ERROR") == 0) {
                throw new Exception(responseString);
            } else {
                return params.getBase64() ? (T) responseString : (T) response;
            }
        }
    }

    public void speechAsync(final VoiceParameters params) {
        try {
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        Object response = VoiceProvider.this.speech(params);
                        if (params.getBase64()) {
                            handleSpeechData((String)response);
                        } else {
                            handleSpeechData((byte[])response);
                        }
                    } catch (Exception var2) {
                        handleSpeechError(var2);
                    }

                }
            })).start();
        } catch (Exception var3) {
            this.handleSpeechError(var3);
        }

    }

    private void validate(VoiceParameters params) throws Exception {
        if (this._apiKey != null && !this._apiKey.trim().equals("")) {
            if (params.getText() != null && !params.getText().trim().equals("")) {
                if (params.getLanguage() == null || params.getLanguage().trim().equals("")) {
                    throw new Exception("The language is undefined");
                }
            } else {
                throw new Exception("The text is undefined");
            }
        } else {
            throw new Exception("The API key is undefined");
        }
    }

    private String buildParameters(VoiceParameters params) {
        StringBuilder sb = new StringBuilder();
        sb.append("key=" + (this._apiKey != null ? this._apiKey : ""));
        sb.append("&src=" + (params.getText() != null ? params.getText() : ""));
        sb.append("&hl=" + (params.getLanguage() != null ? params.getLanguage() : ""));
        sb.append("&v=" + (params.getVoice() != null ? params.getVoice() : ""));
        sb.append("&r=" + (params.getRate() != null ? params.getRate() : ""));
        sb.append("&c=" + (params.getCodec() != null ? params.getCodec() : ""));
        sb.append("&f=" + (params.getFormat() != null ? params.getFormat() : ""));
        sb.append("&ssml=" + (params.getSSML() != null ? params.getSSML() : ""));
        sb.append("&b64=" + (params.getBase64() != null ? params.getBase64() : ""));
        return sb.toString();
    }
}
