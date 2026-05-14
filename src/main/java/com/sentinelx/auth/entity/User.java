package com.sentinelx.auth.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String name;
    private String email;
    private String password;
    private String role;

}
