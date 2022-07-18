/**
 * Handles the playback of voices
 */
class PlaybackHandler{

    /**
     * Javascript Audio API which handles the playback of the given audio file
     */
    audio = new Audio();

    constructor(){}

    /**
     * play a given base64 string audio
     * @param {*} url
     * @returns Promise
     */
    play(sound) {
        var that = this;
        return new Promise(function(resolve, reject) {
            that.audio.preload = "auto";
            that.audio.autoplay = true;
            that.audio.onerror = reject;
            that.audio.onended = resolve;
            that.audio.src = sound
        });
    }

    /**
     * Stops the current playback of the entity
     */
    stop(){
        var that = this;
        that.audio.pause();
        that.audio.currentTime = 0;
    }
}