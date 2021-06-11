package com.thasheel.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.thasheel.domain.enumeration.SERVICEAPPLIEDSTATUS;

/**
 * A ServiceApplied.
 */
@Entity
@Table(name = "service_applied")
public class ServiceApplied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "branch_manager_id")
    private Long branchManagerId;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "updated_time")
    private Instant updatedTime;

    @Column(name = "reprocess_description")
    private String reprocessDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status")
    private SERVICEAPPLIEDSTATUS currentStatus;

    @OneToMany(mappedBy = "serviceApplied")
    private Set<ServiceAppliedStatusHistory> histories = new HashSet<>();

    @OneToMany(mappedBy = "serviceApplied")
    private Set<ServiceAppliedDocuments> uploadedDocuments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public ServiceApplied customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public ServiceApplied serviceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getBranchManagerId() {
        return branchManagerId;
    }

    public ServiceApplied branchManagerId(Long branchManagerId) {
        this.branchManagerId = branchManagerId;
        return this;
    }

    public void setBranchManagerId(Long branchManagerId) {
        this.branchManagerId = branchManagerId;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public ServiceApplied createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public ServiceApplied updatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getReprocessDescription() {
        return reprocessDescription;
    }

    public ServiceApplied reprocessDescription(String reprocessDescription) {
        this.reprocessDescription = reprocessDescription;
        return this;
    }

    public void setReprocessDescription(String reprocessDescription) {
        this.reprocessDescription = reprocessDescription;
    }

    public SERVICEAPPLIEDSTATUS getCurrentStatus() {
        return currentStatus;
    }

    public ServiceApplied currentStatus(SERVICEAPPLIEDSTATUS currentStatus) {
        this.currentStatus = currentStatus;
        return this;
    }

    public void setCurrentStatus(SERVICEAPPLIEDSTATUS currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Set<ServiceAppliedStatusHistory> getHistories() {
        return histories;
    }

    public ServiceApplied histories(Set<ServiceAppliedStatusHistory> serviceAppliedStatusHistories) {
        this.histories = serviceAppliedStatusHistories;
        return this;
    }

    public ServiceApplied addHistory(ServiceAppliedStatusHistory serviceAppliedStatusHistory) {
        this.histories.add(serviceAppliedStatusHistory);
        serviceAppliedStatusHistory.setServiceApplied(this);
        return this;
    }

    public ServiceApplied removeHistory(ServiceAppliedStatusHistory serviceAppliedStatusHistory) {
        this.histories.remove(serviceAppliedStatusHistory);
        serviceAppliedStatusHistory.setServiceApplied(null);
        return this;
    }

    public void setHistories(Set<ServiceAppliedStatusHistory> serviceAppliedStatusHistories) {
        this.histories = serviceAppliedStatusHistories;
    }

    public Set<ServiceAppliedDocuments> getUploadedDocuments() {
        return uploadedDocuments;
    }

    public ServiceApplied uploadedDocuments(Set<ServiceAppliedDocuments> serviceAppliedDocuments) {
        this.uploadedDocuments = serviceAppliedDocuments;
        return this;
    }

    public ServiceApplied addUploadedDocuments(ServiceAppliedDocuments serviceAppliedDocuments) {
        this.uploadedDocuments.add(serviceAppliedDocuments);
        serviceAppliedDocuments.setServiceApplied(this);
        return this;
    }

    public ServiceApplied removeUploadedDocuments(ServiceAppliedDocuments serviceAppliedDocuments) {
        this.uploadedDocuments.remove(serviceAppliedDocuments);
        serviceAppliedDocuments.setServiceApplied(null);
        return this;
    }

    public void setUploadedDocuments(Set<ServiceAppliedDocuments> serviceAppliedDocuments) {
        this.uploadedDocuments = serviceAppliedDocuments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceApplied)) {
            return false;
        }
        return id != null && id.equals(((ServiceApplied) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceApplied{" +
            "id=" + getId() +
            ", customerId=" + getCustomerId() +
            ", serviceId=" + getServiceId() +
            ", branchManagerId=" + getBranchManagerId() +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", reprocessDescription='" + getReprocessDescription() + "'" +
            ", currentStatus='" + getCurrentStatus() + "'" +
            "}";
    }
}
