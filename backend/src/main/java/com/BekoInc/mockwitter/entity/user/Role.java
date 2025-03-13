package com.BekoInc.mockwitter.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "role", schema = "mockwitter")

public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "authority", nullable = false)
    @NotBlank(message = "Authority cannot be empty")
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
    @Column(name = "description")
    @Size(min=5, message = "Role description must be between 5 - 255 characters.")
    @NotNull(message = "Role description can not be null!")
    @NotBlank(message = "Role description can not be empty")
    private String description;

}
