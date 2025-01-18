package com.hahn.erms.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.io.Serializable;

@Entity
@RevisionEntity(CustomRevisionListener.class)
@Table(name = "revinfo")
@Getter
@Setter
public class CustomRevisionEntity implements Serializable {
    @Id
    @GeneratedValue
    @RevisionNumber
    private Long rev;

    @RevisionTimestamp
    private Long timestamp;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modification_type")
    private String modificationType;
}