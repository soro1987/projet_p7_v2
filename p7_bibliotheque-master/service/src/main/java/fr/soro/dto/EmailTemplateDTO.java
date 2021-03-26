package fr.soro.dto;

public class EmailTemplateDTO {
    private String sendTo;
    private String subject;
    private String body;

    public EmailTemplateDTO() {

    }

    public EmailTemplateDTO(String sendTo, String subject, String body) {
        this.sendTo = sendTo;
        this.subject = subject;
        this.body = body;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
