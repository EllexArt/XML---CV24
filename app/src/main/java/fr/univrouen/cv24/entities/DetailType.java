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
 * <p>Java class for detailType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="detailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="datedeb" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="datefin" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="titre" type="{http://univ.fr/cv24}string128"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailType", propOrder = {
    "datedeb",
    "datefin",
    "titre"
})
@Entity
public class DetailType {

    @Id
    @GeneratedValue
    @XmlTransient
    private Long id;

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    @Transient
    protected XMLGregorianCalendar datedeb;

    @XmlSchemaType(name = "date")
    @Transient
    protected XMLGregorianCalendar datefin;
    @XmlElement(required = true)
    protected String titre;

    @Column(name = "date-deb")
    public Date getDateSQLdeb() {
        return new Date(datedeb.toGregorianCalendar().getTimeInMillis());
    }

    @Column(name = "date-fin")
    public Date getDateSQLfin() {
        return new Date(datefin.toGregorianCalendar().getTimeInMillis());
    }

    /**
     * Gets the value of the datedeb property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDatedeb() {
        return datedeb;
    }

    /**
     * Sets the value of the datedeb property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setDatedeb(XMLGregorianCalendar value) {
        this.datedeb = value;
    }

    /**
     * Gets the value of the datefin property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDatefin() {
        return datefin;
    }

    /**
     * Sets the value of the datefin property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setDatefin(XMLGregorianCalendar value) {
        this.datefin = value;
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

    public Long getId() {
        return id;
    }
}
