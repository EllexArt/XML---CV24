package fr.univrouen.cv24.entities.responses;

import fr.univrouen.cv24.entities.Cv24Type;
import jakarta.xml.bind.annotation.XmlElement;

public class XMLResponse extends Response {

    @XmlElement
    Cv24Type cv;

    public XMLResponse(Cv24Type cv, ResponseStatus status) {
        super(status);
        this.cv = cv;
    }

    public Cv24Type getCv() {
        return cv;
    }

    public void setCv(Cv24Type cv) {
        this.cv = cv;
    }

}
