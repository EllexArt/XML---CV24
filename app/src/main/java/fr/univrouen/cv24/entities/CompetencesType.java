//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.04.13 at 04:18:45 PM UTC 
//


package fr.univrouen.cv24.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.xml.bind.annotation.*;


/**
 * <p>Java class for competencesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="competencesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="diplôme" type="{http://univ.fr/cv24}diplomeType" maxOccurs="5"/&gt;
 *         &lt;element name="certif" type="{http://univ.fr/cv24}certifType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "competencesType", propOrder = {
    "diplome",
    "certif"
})
@Entity
public class CompetencesType {

    @Id
    @GeneratedValue
    @XmlTransient
    private Long id;

    @XmlElement(required = true)
    @OneToMany
    protected List<DiplomeType> diplome;

    @OneToMany
    protected List<CertifType> certif;

    /**
     * Gets the value of the diplôme property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diplôme property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiplôme().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiplomeType }
     * 
     * 
     */
    public List<DiplomeType> getDiplome() {
        if (diplome == null) {
            diplome = new ArrayList<DiplomeType>();
        }
        return this.diplome;
    }

    /**
     * Gets the value of the certif property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the certif property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCertif().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CertifType }
     * 
     * 
     */
    public List<CertifType> getCertif() {
        if (certif == null) {
            certif = new ArrayList<CertifType>();
        }
        return this.certif;
    }

    public Long getId() {
        return id;
    }
}
