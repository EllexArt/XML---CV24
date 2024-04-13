package fr.univrouen.cv24.entities.responses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


public class InsertedCVResponse extends Response {

    @XmlElement
    private int cvId;

    public InsertedCVResponse(int cvId, ResponseStatus status) {
        super(ResponseStatus.INSERTED);
        this.cvId = cvId;
    }

    public int getCvId() {
        return cvId;
    }

    public void setCvId(int cvId) {
        this.cvId = cvId;
    }
}
