package com.thasheel.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A SavedNews.
 */
@Entity
@Table(name = "saved_news")
public class SavedNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "news_id")
    private Long newsId;

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

    public SavedNews customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public SavedNews newsId(Long newsId) {
        this.newsId = newsId;
        return this;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SavedNews)) {
            return false;
        }
        return id != null && id.equals(((SavedNews) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SavedNews{" +
            "id=" + getId() +
            ", customerId=" + getCustomerId() +
            ", newsId=" + getNewsId() +
            "}";
    }
}
