package com.thasheel.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A MajorAdmin.
 */
@Entity
@Table(name = "major_admin")
public class MajorAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    
    @Column(name = "idp_code", unique = true)
    private String idpCode;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MajorAdmin name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdpCode() {
        return idpCode;
    }

    public MajorAdmin idpCode(String idpCode) {
        this.idpCode = idpCode;
        return this;
    }

    public void setIdpCode(String idpCode) {
        this.idpCode = idpCode;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public MajorAdmin isEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MajorAdmin)) {
            return false;
        }
        return id != null && id.equals(((MajorAdmin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MajorAdmin{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", idpCode='" + getIdpCode() + "'" +
            ", isEnabled='" + isIsEnabled() + "'" +
            "}";
    }
}
