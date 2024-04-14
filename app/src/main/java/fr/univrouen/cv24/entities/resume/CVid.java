package fr.univrouen.cv24.entities.resume;

import fr.univrouen.cv24.entities.Cv24Type;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class CVid {

    private long id;

    private String resume;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCv() {
        return resume;
    }

    public void setCv(String resume) {
        this.resume = resume;
    }
}
