package no.kristiania.chats;

import java.util.Random;

public class SampleChat {

    private static final Random random = new Random();

    static Message sampleMessage(){
        var message = new Message();
        message.setSubject(generateExample(
                "Emergency ",
                "Hello There ",
                "Question "
        ));
        message.setMessageBody(generateExample(
                "We have en emergency at the office, can you come in",
                "I have wanted to ask you a question for a while now",
                "WUBBALUBBADUBDUB"
        ));
        return message;
    }

    private static String generateExample( String... options) {
        return options[random.nextInt(options.length)];
    }
}
