//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.12.15 at 09:22:26 AM VET 
//


package com.ula.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.statemachine.org/state_machine.xsd}eventSM" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.statemachine.org/state_machine.xsd}state" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.statemachine.org/state_machine.xsd}initial"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="package" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="pathJava" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="alternativeTemplateDirectory" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="alternativeTemplateName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "eventSM",
    "state",
    "initial"
})
@XmlRootElement(name = "state_machine")
public class StateMachine {

    @XmlElement(required = true)
    protected List<EventSM> eventSM;
    @XmlElement(required = true)
    protected List<State> state;
    @XmlElement(required = true)
    protected Initial initial;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(name = "package", required = true)
    protected String _package;
    @XmlAttribute(required = true)
    protected String pathJava;
    @XmlAttribute
    protected String alternativeTemplateDirectory;
    @XmlAttribute
    protected String alternativeTemplateName;

    /**
     * Gets the value of the eventSM property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eventSM property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEventSM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventSM }
     * 
     * 
     */
    public List<EventSM> getEventSM() {
        if (eventSM == null) {
            eventSM = new ArrayList<EventSM>();
        }
        return this.eventSM;
    }

    /**
     * Gets the value of the state property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the state property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getState().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link State }
     * 
     * 
     */
    public List<State> getState() {
        if (state == null) {
            state = new ArrayList<State>();
        }
        return this.state;
    }

    /**
     * Gets the value of the initial property.
     * 
     * @return
     *     possible object is
     *     {@link Initial }
     *     
     */
    public Initial getInitial() {
        return initial;
    }

    /**
     * Sets the value of the initial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Initial }
     *     
     */
    public void setInitial(Initial value) {
        this.initial = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the package property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackage() {
        return _package;
    }

    /**
     * Sets the value of the package property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackage(String value) {
        this._package = value;
    }

    /**
     * Gets the value of the pathJava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathJava() {
        return pathJava;
    }

    /**
     * Sets the value of the pathJava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathJava(String value) {
        this.pathJava = value;
    }

    /**
     * Gets the value of the alternativeTemplateDirectory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternativeTemplateDirectory() {
        return alternativeTemplateDirectory;
    }

    /**
     * Sets the value of the alternativeTemplateDirectory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternativeTemplateDirectory(String value) {
        this.alternativeTemplateDirectory = value;
    }

    /**
     * Gets the value of the alternativeTemplateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternativeTemplateName() {
        return alternativeTemplateName;
    }

    /**
     * Sets the value of the alternativeTemplateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternativeTemplateName(String value) {
        this.alternativeTemplateName = value;
    }

}
