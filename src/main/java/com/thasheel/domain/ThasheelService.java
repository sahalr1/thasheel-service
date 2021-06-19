package com.thasheel.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ThasheelService.
 */
@Entity
@Table(name = "thasheel_service")
public class ThasheelService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_major_admin_id")
    private Long createdMajorAdminId;

    @Column(name = "is_expired")
    private Boolean isExpired;

    @Column(name = "validity_date")
    private Instant validityDate;

    @Column(name = "amount")
    private Double amount;

    @OneToMany(mappedBy = "thasheelService")
    private Set<NeedfulServiceDocuments> needFulDocuments = new HashSet<>();

    @OneToMany(mappedBy = "thasheelService")
    private Set<Branch> assignedBranches = new HashSet<>();

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

    public ThasheelService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ThasheelService description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public ThasheelService createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Long getCreatedMajorAdminId() {
        return createdMajorAdminId;
    }

    public ThasheelService createdMajorAdminId(Long createdMajorAdminId) {
        this.createdMajorAdminId = createdMajorAdminId;
        return this;
    }

    public void setCreatedMajorAdminId(Long createdMajorAdminId) {
        this.createdMajorAdminId = createdMajorAdminId;
    }

    public Boolean isIsExpired() {
        return isExpired;
    }

    public ThasheelService isExpired(Boolean isExpired) {
        this.isExpired = isExpired;
        return this;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Instant getValidityDate() {
        return validityDate;
    }

    public ThasheelService validityDate(Instant validityDate) {
        this.validityDate = validityDate;
        return this;
    }

    public void setValidityDate(Instant validityDate) {
        this.validityDate = validityDate;
    }

    public Double getAmount() {
        return amount;
    }

    public ThasheelService amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Set<NeedfulServiceDocuments> getNeedFulDocuments() {
        return needFulDocuments;
    }

    public ThasheelService needFulDocuments(Set<NeedfulServiceDocuments> needfulServiceDocuments) {
        this.needFulDocuments = needfulServiceDocuments;
        return this;
    }

    public ThasheelService addNeedFulDocuments(NeedfulServiceDocuments needfulServiceDocuments) {
        this.needFulDocuments.add(needfulServiceDocuments);
        needfulServiceDocuments.setThasheelService(this);
        return this;
    }

    public ThasheelService removeNeedFulDocuments(NeedfulServiceDocuments needfulServiceDocuments) {
        this.needFulDocuments.remove(needfulServiceDocuments);
        needfulServiceDocuments.setThasheelService(null);
        return this;
    }

    public void setNeedFulDocuments(Set<NeedfulServiceDocuments> needfulServiceDocuments) {
        this.needFulDocuments = needfulServiceDocuments;
    }

    public Set<Branch> getAssignedBranches() {
        return assignedBranches;
    }

    public ThasheelService assignedBranches(Set<Branch> branches) {
        this.assignedBranches = branches;
        return this;
    }

    public ThasheelService addAssignedBranches(Branch branch) {
        this.assignedBranches.add(branch);
        branch.setThasheelService(this);
        return this;
    }

    public ThasheelService removeAssignedBranches(Branch branch) {
        this.assignedBranches.remove(branch);
        branch.setThasheelService(null);
        return this;
    }

    public void setAssignedBranches(Set<Branch> branches) {
        this.assignedBranches = branches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThasheelService)) {
            return false;
        }
        return id != null && id.equals(((ThasheelService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThasheelService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdMajorAdminId=" + getCreatedMajorAdminId() +
            ", isExpired='" + isIsExpired() + "'" +
            ", validityDate='" + getValidityDate() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
