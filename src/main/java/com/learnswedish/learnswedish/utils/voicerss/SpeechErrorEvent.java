package com.learnswedish.learnswedish.utils.voicerss;

import java.util.EventObject;

public class SpeechErrorEvent extends EventObject {
    private Exception _exception;

    public SpeechErrorEvent(Exception arg) {
        super(arg);
        this._exception = arg;
    }

    public Exception getException() {
        return this._exception;
    }
}
