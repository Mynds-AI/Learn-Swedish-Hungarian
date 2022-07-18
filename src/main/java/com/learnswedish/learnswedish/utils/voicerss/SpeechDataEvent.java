package com.learnswedish.learnswedish.utils.voicerss;

import java.util.EventObject;

public class SpeechDataEvent<T> extends EventObject {
    private T _data;

    public SpeechDataEvent(T arg) {
        super(arg);
        this._data = arg;
    }

    public T getData() {
        return this._data;
    }
}
