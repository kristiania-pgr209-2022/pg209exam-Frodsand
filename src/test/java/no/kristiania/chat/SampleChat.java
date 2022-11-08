package no.kristiania.chat;

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
    public static User sampleUser() {
        var user = new User();
        user.setUsername(generateExample(
                "Michael Scott",
                "Rick Sanchez",
                "Rhaenyra Targaryen"
        ));
        user.setEmailAddress(generateExample(
                "michael.scott@bestbossever.com",
                "rick.sanchez@getschwifty.com",
                "rhaenyra@therealqueen.com"
        ));
        user.setTlfNumber(generateExample(
                "+43 38194628",
                "+45 847294721",
                "+47 12346251"
        ));
        return user;
    }

    private static String generateExample( String... options) {
        return options[random.nextInt(options.length)];
    }

}
