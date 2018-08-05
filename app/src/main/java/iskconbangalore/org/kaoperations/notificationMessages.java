package iskconbangalore.org.kaoperations;

public class notificationMessages {

    private String title;
    private String body;
    private String tmstmp;

    public notificationMessages() {
    }

    public notificationMessages(String title, String body, String tmstmp) {
        this.title = title;
        this.body = body;
        this.tmstmp = tmstmp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTmstmp() {
        return tmstmp;
    }

    public void setTmstmp(String tmstmp) {
        this.tmstmp = tmstmp;
    }
}