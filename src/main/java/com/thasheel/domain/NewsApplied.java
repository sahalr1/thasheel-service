package com.thasheel.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.thasheel.domain.enumeration.NEWSAPPLIEDSTATUS;

/**
 * A NewsApplied.
 */
@Entity
@Table(name = "news_applied")
public class NewsApplied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "news_id")
    private Long newsId;

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
    private NEWSAPPLIEDSTATUS currentStatus;

    @OneToMany(mappedBy = "newsApplied")
    private Set<NewsAppliedStatusHistory> histories = new HashSet<>();

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

    public NewsApplied customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public NewsApplied newsId(Long newsId) {
        this.newsId = newsId;
        return this;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Long getBranchManagerId() {
        return branchManagerId;
    }

    public NewsApplied branchManagerId(Long branchManagerId) {
        this.branchManagerId = branchManagerId;
        return this;
    }

    public void setBranchManagerId(Long branchManagerId) {
        this.branchManagerId = branchManagerId;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public NewsApplied createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public NewsApplied updatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getReprocessDescription() {
        return reprocessDescription;
    }

    public NewsApplied reprocessDescription(String reprocessDescription) {
        this.reprocessDescription = reprocessDescription;
        return this;
    }

    public void setReprocessDescription(String reprocessDescription) {
        this.reprocessDescription = reprocessDescription;
    }

    public NEWSAPPLIEDSTATUS getCurrentStatus() {
        return currentStatus;
    }

    public NewsApplied currentStatus(NEWSAPPLIEDSTATUS currentStatus) {
        this.currentStatus = currentStatus;
        return this;
    }

    public void setCurrentStatus(NEWSAPPLIEDSTATUS currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Set<NewsAppliedStatusHistory> getHistories() {
        return histories;
    }

    public NewsApplied histories(Set<NewsAppliedStatusHistory> newsAppliedStatusHistories) {
        this.histories = newsAppliedStatusHistories;
        return this;
    }

    public NewsApplied addHistory(NewsAppliedStatusHistory newsAppliedStatusHistory) {
        this.histories.add(newsAppliedStatusHistory);
        newsAppliedStatusHistory.setNewsApplied(this);
        return this;
    }

    public NewsApplied removeHistory(NewsAppliedStatusHistory newsAppliedStatusHistory) {
        this.histories.remove(newsAppliedStatusHistory);
        newsAppliedStatusHistory.setNewsApplied(null);
        return this;
    }

    public void setHistories(Set<NewsAppliedStatusHistory> newsAppliedStatusHistories) {
        this.histories = newsAppliedStatusHistories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewsApplied)) {
            return false;
        }
        return id != null && id.equals(((NewsApplied) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewsApplied{" +
            "id=" + getId() +
            ", customerId=" + getCustomerId() +
            ", newsId=" + getNewsId() +
            ", branchManagerId=" + getBranchManagerId() +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", reprocessDescription='" + getReprocessDescription() + "'" +
            ", currentStatus='" + getCurrentStatus() + "'" +
            "}";
    }
}
