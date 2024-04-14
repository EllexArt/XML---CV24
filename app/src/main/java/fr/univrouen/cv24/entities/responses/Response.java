package fr.univrouen.cv24.entities.responses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Response {
    @XmlElement
    private CVResponseStatus status;

    protected Response(CVResponseStatus status) {
        this.status = status;
    }

    public CVResponseStatus getStatus() {
        return status;
    }

    public void setStatus(CVResponseStatus status) {
        this.status = status;
    }
}
