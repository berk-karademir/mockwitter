package com.BekoInc.mockwitter.dto;

import com.BekoInc.mockwitter.entity.user.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;

import java.util.Collection;


// İster manuel null filter yap istersen;
// @JsonInclude(JsonInclude.Include.NON_NULL) kullan.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
    private Collection<Role> userRoles;

    // Constructor
    public UserRegistrationResponse(Long id, String firstName, String lastName, String email, String username, String phoneNumber, Collection<Role> userRoles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.userRoles = userRoles;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Collection<Role> getUserRoles() {
        return userRoles;
    }
}
