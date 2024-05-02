package fr.univrouen.cv24.entities.resume;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "CVList")
@XmlAccessorType(XmlAccessType.FIELD)
public class CVList {

    @XmlElement
    private List<CVid> cvidList = new ArrayList<>();


    public List<CVid> getCvidList() {
        return cvidList;
    }

    public void setCvidList(List<CVid> cvidList) {
        this.cvidList = cvidList;
    }

    public void addCVid(CVid cVid) {
        cvidList.add(cVid);
    }
}
