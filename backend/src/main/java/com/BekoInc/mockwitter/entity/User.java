package com.BekoInc.mockwitter.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table( name = "user", schema = "mockwitter")
public class User {

    //https://chat.deepseek.com/a/chat/s/aa78a905-10e5-4842-9e8e-a9d89f9de5bc

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "User ID must be greater than 0!")
    @NotNull(message = "User ID can not be null!")
    @Column(name = "id" , nullable = false)
    private Long id;

    @Column(name = "first_name")
    @Size(max = 24, message = "First name should include 24 characters maximum!")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 24, message = "Last name should include 24 characters maximum!")
    private String lastName;


    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")
    @Email(message = "Email format is not valid!")
    @NotNull(message = "Email can not be null!")
    @NotBlank(message = "Email can not be empty!")
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    @NotNull(message = "Username can not be empty!")
    @NotBlank(message = "Username can not be blank!")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters!")
    private String userName;

    @Pattern(regexp="(^$|[0-9]{10})")
    @Column(name = "phone_number")
    private String phoneNumber;


    @CreationTimestamp
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) ||
                Objects.equals(email, user.email) ||
                Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, userName);
    }
}
