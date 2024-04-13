//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.04.13 at 04:18:45 PM UTC 
//


package fr.univrouen.cv24.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;


/**
 * <p>Java class for diplomeType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="diplomeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="institut" type="{http://univ.fr/cv24}string32" minOccurs="0"/&gt;
 *         &lt;element name="titre" type="{http://univ.fr/cv24}string32"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="niveau" use="required" type="{http://univ.fr/cv24}niveauType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "diplomeType", propOrder = {
    "date",
    "institut",
    "titre"
})
@Entity
public class DiplomeType {

    @Id
    @GeneratedValue
    @XmlTransient
    private Long id;

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    @Transient
    protected XMLGregorianCalendar date;

    protected String institut;

    @XmlElement(required = true)
    protected String titre;

    @XmlAttribute(name = "niveau", required = true)
    protected int niveau;

    @Column(name = "date")
    public Date getDateSQL() {
        return new Date(date.toGregorianCalendar().getTimeInMillis());
    }

    /**
     * Gets the value of the date property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the institut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitut() {
        return institut;
    }

    /**
     * Sets the value of the institut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitut(String value) {
        this.institut = value;
    }

    /**
     * Gets the value of the titre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Sets the value of the titre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitre(String value) {
        this.titre = value;
    }

    /**
     * Gets the value of the niveau property.
     * 
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * Sets the value of the niveau property.
     * 
     */
    public void setNiveau(int value) {
        this.niveau = value;
    }

    public Long getId() {
        return id;
    }
}