package com.thasheel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ServiceAppliedDocuments.
 */
@Entity
@Table(name = "service_applied_documents")
public class ServiceAppliedDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "needful_service_docment_id")
    private Long needfulServiceDocmentId;

    @Lob
    @Column(name = "upload_document")
    private byte[] uploadDocument;

    @Column(name = "upload_document_content_type")
    private String uploadDocumentContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "uploadedDocuments", allowSetters = true)
    private ServiceApplied serviceApplied;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNeedfulServiceDocmentId() {
        return needfulServiceDocmentId;
    }

    public ServiceAppliedDocuments needfulServiceDocmentId(Long needfulServiceDocmentId) {
        this.needfulServiceDocmentId = needfulServiceDocmentId;
        return this;
    }

    public void setNeedfulServiceDocmentId(Long needfulServiceDocmentId) {
        this.needfulServiceDocmentId = needfulServiceDocmentId;
    }

    public byte[] getUploadDocument() {
        return uploadDocument;
    }

    public ServiceAppliedDocuments uploadDocument(byte[] uploadDocument) {
        this.uploadDocument = uploadDocument;
        return this;
    }

    public void setUploadDocument(byte[] uploadDocument) {
        this.uploadDocument = uploadDocument;
    }

    public String getUploadDocumentContentType() {
        return uploadDocumentContentType;
    }

    public ServiceAppliedDocuments uploadDocumentContentType(String uploadDocumentContentType) {
        this.uploadDocumentContentType = uploadDocumentContentType;
        return this;
    }

    public void setUploadDocumentContentType(String uploadDocumentContentType) {
        this.uploadDocumentContentType = uploadDocumentContentType;
    }

    public ServiceApplied getServiceApplied() {
        return serviceApplied;
    }

    public ServiceAppliedDocuments serviceApplied(ServiceApplied serviceApplied) {
        this.serviceApplied = serviceApplied;
        return this;
    }

    public void setServiceApplied(ServiceApplied serviceApplied) {
        this.serviceApplied = serviceApplied;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceAppliedDocuments)) {
            return false;
        }
        return id != null && id.equals(((ServiceAppliedDocuments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceAppliedDocuments{" +
            "id=" + getId() +
            ", needfulServiceDocmentId=" + getNeedfulServiceDocmentId() +
            ", uploadDocument='" + getUploadDocument() + "'" +
            ", uploadDocumentContentType='" + getUploadDocumentContentType() + "'" +
            "}";
    }
}
