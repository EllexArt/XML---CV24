package fr.univrouen.cv24.entities.responses;

import fr.univrouen.cv24.entities.CV;
import jakarta.xml.bind.annotation.XmlElement;

public class XMLResponse extends Response {

    @XmlElement
    CV cv;

    public XMLResponse(CV cv, ResponseStatus status) {
        super(status);
        this.cv = cv;
    }

    public CV getCv() {
        return cv;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }

}
