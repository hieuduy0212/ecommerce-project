package com.hieuduy.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "admin_roles"
            , joinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "admin_id", foreignKey = @ForeignKey(name = "fk_admin_role_id"))
            , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id", foreignKey = @ForeignKey(name = "fk_role_admin_id")))
    private List<Role> roles;

}
