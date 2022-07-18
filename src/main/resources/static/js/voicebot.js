class Voicebot {

    recognition = new webkitSpeechRecognition();
    playbackHandler = new PlaybackHandler();
    $startButton = $('#start_recognition');
    $ttsButton = $('#text_to_speech')
    $text_to_speech = $('#text_to_hungarian')
    $resultContainer = $('#recognition_container');

    constructor(){
        var that = this;

        this.recognition.grammars = new webkitSpeechGrammarList();
        this.recognizing = false;
        this.recognition.continuous = true;
        this.recognition.lang = 'sv-SE';
        this.recognition.interimResults = true;
        this.recognition.maxAlternatives = 1;
    }

    startProgram(){
        var that = this;

         that.$startButton.click(function(){
            that.startRecognition();
         })
    }

    startTTSProgram(){
        var that = this;

        that.$ttsButton.click(function(){
            that.getTTS(that.$text_to_speech.val());
        })
    }


    startRecognition(){
        var that = this;

        that.recognition.start();

        that.recognition.onresult = function(event) {
            var last = event.results.length - 1;
            var result = event.results[last][0].transcript;
            that.$resultContainer.append(result + '<br>')
        }
    }

    /**
     * Makes a post request to the /get-tts endpoint to get the speech of the given text
     * @param {*} recognition
     * @param {*} textToSpeech
     */
    getTTS(textToSpeech){
    var that = this;
        var request = $.ajax({
            url: "/get-tts",
            type:"post",
            data:{
                tts: textToSpeech
            }
        })

        request.done(function(response){
            that.playbackHandler.play(response)
        })
    }
}