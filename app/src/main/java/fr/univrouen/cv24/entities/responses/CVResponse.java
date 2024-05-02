package fr.univrouen.cv24.entities.responses;

import jakarta.xml.bind.annotation.XmlElement;


public class CVResponse extends Response {

    @XmlElement
    private long id;

    public CVResponse(long id, CVResponseStatus status) {
        super(status);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(int cvId) {
        this.id = cvId;
    }
}
