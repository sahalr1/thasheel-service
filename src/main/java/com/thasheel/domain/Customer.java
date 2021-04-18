package com.thasheel.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    
    @Column(name = "idp_code", unique = true)
    private String idpCode;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_complete_pofile")
    private Boolean isCompletePofile;

    @OneToMany(mappedBy = "customer")
    private Set<Comment> comments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdpCode() {
        return idpCode;
    }

    public Customer idpCode(String idpCode) {
        this.idpCode = idpCode;
        return this;
    }

    public void setIdpCode(String idpCode) {
        this.idpCode = idpCode;
    }

    public Long getCountryId() {
        return countryId;
    }

    public Customer countryId(Long countryId) {
        this.countryId = countryId;
        return this;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public Customer isEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean isIsCompletePofile() {
        return isCompletePofile;
    }

    public Customer isCompletePofile(Boolean isCompletePofile) {
        this.isCompletePofile = isCompletePofile;
        return this;
    }

    public void setIsCompletePofile(Boolean isCompletePofile) {
        this.isCompletePofile = isCompletePofile;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Customer comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Customer addComments(Comment comment) {
        this.comments.add(comment);
        comment.setCustomer(this);
        return this;
    }

    public Customer removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setCustomer(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", idpCode='" + getIdpCode() + "'" +
            ", countryId=" + getCountryId() +
            ", isEnabled='" + isIsEnabled() + "'" +
            ", isCompletePofile='" + isIsCompletePofile() + "'" +
            "}";
    }
}
