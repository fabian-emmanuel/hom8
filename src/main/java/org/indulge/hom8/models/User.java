package org.indulge.hom8.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.indulge.hom8.enums.UserType;
import org.springframework.data.relational.core.mapping.Column;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@SuperBuilder
@MappedSuperclass
public abstract class User extends BaseModel {
    @Column(value = "first_name")
    String firstName;
    @Column(value = "last_name")
    String lastName;
    @Column(value = "phone_number")
    String phoneNumber;
    @Column(value = "address")
    String address;
    @Column(value = "pin")
    String pin;
    @Column(value = "is_active")
    boolean active;
    @Enumerated(EnumType.STRING)
    @Column(value = "user_type")
    UserType userType;
}
