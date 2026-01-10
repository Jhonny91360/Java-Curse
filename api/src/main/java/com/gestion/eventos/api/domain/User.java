package com.gestion.eventos.api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // eager = traer relaciones en las lecturas del user
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name = "user_id" , referencedColumnName = "id"), // referencia a la tabla actual( users ) con su id
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // referencia a la otra tabla ( roles ) con su id
    )
    private Set<Role> roles= new HashSet<>();
}
