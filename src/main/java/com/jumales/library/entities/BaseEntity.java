package com.jumales.library.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {

    @Column(name = "deleted", columnDefinition = "Bit(1) default false")
    private boolean deleted = false;

    @Column(name = "data_change_created_by", nullable = false)
    private String dataChangeCreatedBy;

    @Column(name = "data_change_created_time", nullable = false)
    private Date dataChangeCreatedTime;

    @Column(name = "data_change_last_modified_by")
    private String dataChangeLastModifiedBy;

    @Column(name = "data_change_Last_time")
    private Date dataChangeLastModifiedTime;

    @PrePersist
    protected void prePersist() {
        if (this.dataChangeCreatedTime == null) dataChangeCreatedTime = new Date();
        if (this.dataChangeLastModifiedTime == null) dataChangeLastModifiedTime = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.dataChangeLastModifiedTime = new Date();
    }

    @PreRemove
    protected void preRemove() {
        this.dataChangeLastModifiedTime = new Date();
    }


}
