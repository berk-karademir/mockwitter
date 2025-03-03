package com.BekoInc.mockwitter.entity;


import com.BekoInc.mockwitter.util.Regexes;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Pattern(regexp = Regexes.EMAIL_REGEX , message = "Email is not valid!")
    @Email(message = "Email is not valid!")
    @NotNull(message = "Email can not be null!")
    @NotBlank(message = "Email can not be empty!")
    private String email;

    @Pattern(regexp = Regexes.USERNAME_REGEX, message = "Username is not valid!")
    @Column(name = "username", unique = true, nullable = false)
    @NotNull(message = "Username can not be empty!")
    @NotBlank(message = "Username can not be blank!")
    @Size(min = 3, max = 20, message = "Username can not include special characters and must be between 3 and 20 characters!")
    private String userName;


//    At least one digit.
//    At least one lowercase letter.
//    At least one uppercase letter.
//    At least one special character.
//    No whitespace allowed.
//    Length between 8 and 20 characters.
    @Column(name = "password", nullable = false)
    @NotNull
    @NotBlank
    @Pattern(regexp = Regexes.PASSWORD_REGEX, message = "Password must be 8-20 characters long, include at least one uppercase letter, one lowercase letter, one digit, one special character and white space is not allowed!")
    private String password;

    @Pattern(regexp= Regexes.PHONE_NUMBER_REGEX, message = "Phone number is not valid!")
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
