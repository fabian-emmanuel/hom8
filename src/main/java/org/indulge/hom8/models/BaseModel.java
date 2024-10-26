package org.indulge.hom8.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;


@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreatedDate
    @Column(value = "created_at")
    LocalDateTime createdAt;

    @LastModifiedDate
    @Column(value = "updated_at")
    LocalDateTime updatedAt;

    @CreatedBy
    @Column(value = "created_by")
    private Long createdBy;

    @LastModifiedBy
    @Column(value = "updated_by")
    private Long updatedBy;

    @Column(value = "deleted")
    private boolean deleted = Boolean.FALSE;
}
