package com.thasheel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import com.thasheel.domain.enumeration.FILETYPE;

/**
 * A NeedfulServiceDocuments.
 */
@Entity
@Table(name = "needful_service_documents")
public class NeedfulServiceDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FILETYPE fileType;

    @Column(name = "is_required")
    private Boolean isRequired;

    @ManyToOne
    @JsonIgnoreProperties(value = "needFulDocuments", allowSetters = true)
    private ThasheelService thasheelService;

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

    public NeedfulServiceDocuments name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FILETYPE getFileType() {
        return fileType;
    }

    public NeedfulServiceDocuments fileType(FILETYPE fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(FILETYPE fileType) {
        this.fileType = fileType;
    }

    public Boolean isIsRequired() {
        return isRequired;
    }

    public NeedfulServiceDocuments isRequired(Boolean isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public ThasheelService getThasheelService() {
        return thasheelService;
    }

    public NeedfulServiceDocuments thasheelService(ThasheelService thasheelService) {
        this.thasheelService = thasheelService;
        return this;
    }

    public void setThasheelService(ThasheelService thasheelService) {
        this.thasheelService = thasheelService;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NeedfulServiceDocuments)) {
            return false;
        }
        return id != null && id.equals(((NeedfulServiceDocuments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NeedfulServiceDocuments{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", isRequired='" + isIsRequired() + "'" +
            "}";
    }
}
