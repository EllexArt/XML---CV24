package fr.univrouen.cv24.entities.responses;

import jakarta.xml.bind.annotation.XmlElement;


public class CVResponse extends Response {

    @XmlElement
    private long cvId;

    public CVResponse(long cvId, CVResponseStatus status) {
        super(status);
        this.cvId = cvId;
    }

    public long getCvId() {
        return cvId;
    }

    public void setCvId(int cvId) {
        this.cvId = cvId;
    }
}
