package com.BekoInc.mockwitter.entity.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Junction Table's entity class (user + role)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_roles", schema = "mockwitter")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


}