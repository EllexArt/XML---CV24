package fr.univrouen.cv24.entities.responses;

import fr.univrouen.cv24.entities.CV;
import jakarta.xml.bind.annotation.XmlElement;

public class HTMLResponse extends Response {

    @XmlElement
    String content;


    public HTMLResponse(ResponseStatus status, String content) {
        super(status);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
