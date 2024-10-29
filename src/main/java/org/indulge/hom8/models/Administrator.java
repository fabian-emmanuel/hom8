package org.indulge.hom8.models;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "administrators")
public class Administrator extends User {

}
