package fr.univrouen.cv24.entities.responses;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class NotFoundResponse extends ErrorResponse {

    @XmlElement
    private long id;

    public NotFoundResponse(String message, long id) {
        super(message, CVResponseStatus.NOT_FOUND);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
