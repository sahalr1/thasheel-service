package com.thasheel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.thasheel.domain.enumeration.NEWSAPPLIEDSTATUS;

/**
 * A NewsAppliedStatusHistory.
 */
@Entity
@Table(name = "news_applied_status_history")
public class NewsAppliedStatusHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NEWSAPPLIEDSTATUS status;

    @Column(name = "description")
    private String description;

    @Column(name = "updated_time")
    private Instant updatedTime;

    @Column(name = "action_done_branch_manager_id")
    private String actionDoneBranchManagerId;

    @ManyToOne
    @JsonIgnoreProperties(value = "histories", allowSetters = true)
    private NewsApplied newsApplied;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NEWSAPPLIEDSTATUS getStatus() {
        return status;
    }

    public NewsAppliedStatusHistory status(NEWSAPPLIEDSTATUS status) {
        this.status = status;
        return this;
    }

    public void setStatus(NEWSAPPLIEDSTATUS status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public NewsAppliedStatusHistory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public NewsAppliedStatusHistory updatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getActionDoneBranchManagerId() {
        return actionDoneBranchManagerId;
    }

    public NewsAppliedStatusHistory actionDoneBranchManagerId(String actionDoneBranchManagerId) {
        this.actionDoneBranchManagerId = actionDoneBranchManagerId;
        return this;
    }

    public void setActionDoneBranchManagerId(String actionDoneBranchManagerId) {
        this.actionDoneBranchManagerId = actionDoneBranchManagerId;
    }

    public NewsApplied getNewsApplied() {
        return newsApplied;
    }

    public NewsAppliedStatusHistory newsApplied(NewsApplied newsApplied) {
        this.newsApplied = newsApplied;
        return this;
    }

    public void setNewsApplied(NewsApplied newsApplied) {
        this.newsApplied = newsApplied;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewsAppliedStatusHistory)) {
            return false;
        }
        return id != null && id.equals(((NewsAppliedStatusHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewsAppliedStatusHistory{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", actionDoneBranchManagerId='" + getActionDoneBranchManagerId() + "'" +
            "}";
    }
}
