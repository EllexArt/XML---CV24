package fr.univrouen.cv24.entities.responses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InsertedCVResponse implements Response {

    @XmlElement
    private int cvId;

    @XmlElement
    private ResponseStatus status;

    public InsertedCVResponse(int cvId) {
        this.cvId = cvId;
        this.status = ResponseStatus.INSERTED;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public int getCvId() {
        return cvId;
    }

    public void setCvId(int cvId) {
        this.cvId = cvId;
    }
}
