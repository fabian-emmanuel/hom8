package org.indulge.hom8.models;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.indulge.hom8.enums.Availability;
import org.indulge.hom8.enums.Preference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@SuperBuilder
@Table(name = "helpers")
@Entity
public class Helper extends User {
    @Column(value = "date_of_birth")
    LocalDateTime dateOfBirth;
    @Column(value = "state_of_origin")
    String stateOfOrigin;
    @Column(value = "availability")
    Availability availability;
    @Column(value = "preference")
    Preference preference;
}
