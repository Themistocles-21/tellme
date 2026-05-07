package com.cscd488.html.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vin;
    private String make;
    private String model;
    private String year;

    private String issueLocation;
    private String issueSymptom;
    private String issueSpecific;
    private String severity;

    @Column(name = "free_form_text", length = 1000)
    private String freeFormText;

    @ManyToOne
    @JoinColumn(name = "customer_email", referencedColumnName = "email")
    private Customer customer;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getIssueLocation() { return issueLocation; }
    public void setIssueLocation(String issueLocation) { this.issueLocation = issueLocation; }

    public String getIssueSymptom() { return issueSymptom; }
    public void setIssueSymptom(String issueSymptom) { this.issueSymptom = issueSymptom; }

    public String getIssueSpecific() { return issueSpecific; }
    public void setIssueSpecific(String issueSpecific) { this.issueSpecific = issueSpecific; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getFreeFormText() { return freeFormText; }
    public void setFreeFormText(String freeFormText) { this.freeFormText = freeFormText; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}