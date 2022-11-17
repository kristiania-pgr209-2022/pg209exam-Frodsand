package no.kristiania.chat;

public class Message {

    private int id;

    private String subject;

    private String messageBody;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString(){
        return "Message {\n" +
                "Subject: " + subject + "\n" +
                "message body: " + messageBody + "\n" +
                "}" + "\n";
    }
}
