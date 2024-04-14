package fr.univrouen.cv24.entities.responses;

import fr.univrouen.cv24.entities.Cv24Type;
import jakarta.xml.bind.annotation.XmlElement;

public class XMLResponse extends Response {

    @XmlElement
    String cv;

    public XMLResponse(String cv, ResponseStatus status) {
        super(status);
        this.cv = cv;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

}
