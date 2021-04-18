package com.thasheel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A News.
 */
@Entity
@Table(name = "news")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @Column(name = "file_content_type")
    private String fileContentType;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by_major_admin_id")
    private Long createdByMajorAdminId;

    @OneToMany(mappedBy = "news")
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "news", allowSetters = true)
    private Branch branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public News description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFile() {
        return file;
    }

    public News file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public News fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public News fileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public News createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Long getCreatedByMajorAdminId() {
        return createdByMajorAdminId;
    }

    public News createdByMajorAdminId(Long createdByMajorAdminId) {
        this.createdByMajorAdminId = createdByMajorAdminId;
        return this;
    }

    public void setCreatedByMajorAdminId(Long createdByMajorAdminId) {
        this.createdByMajorAdminId = createdByMajorAdminId;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public News comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public News addComments(Comment comment) {
        this.comments.add(comment);
        comment.setNews(this);
        return this;
    }

    public News removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setNews(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Branch getBranch() {
        return branch;
    }

    public News branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof News)) {
            return false;
        }
        return id != null && id.equals(((News) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "News{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdByMajorAdminId=" + getCreatedByMajorAdminId() +
            "}";
    }
}
