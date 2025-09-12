package org.menuservice.db.user;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.menuservice.db.BaseEntity;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity(name = "user")
@Table(name = "user", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(length = 80, nullable = false)
    private String password;
}
