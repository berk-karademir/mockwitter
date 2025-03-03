package com.BekoInc.mockwitter.entity;



import com.BekoInc.mockwitter.util.Regexes;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


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
    @Pattern(regexp = Regexes.NAME_or_SURNAME)
    @Size(max = 24, message = "First name should include 24 characters maximum!")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = Regexes.NAME_or_SURNAME)
    @Size(max = 24, message = "Last name should include 24 characters maximum!")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @Pattern(regexp = Regexes.EMAIL_REGEX , message = "Email is not valid!")
    @Email(message = "Email is not valid!")
    @NotNull(message = "Email can not be null!")
    @NotBlank(message = "Email can not be empty!")
    private String email;


    @Column(name = "username", unique = true, nullable = false)
    @Pattern(regexp = Regexes.USERNAME_REGEX, message = "Username is not valid!")
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

    @Column(name = "phone_number", unique = true)
    @Pattern(regexp= Regexes.PHONE_NUMBER_REGEX, message = "Phone number is not valid!")
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "registration_date", updatable = false)
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tweet> tweets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Retweet> retweets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes;

    @ManyToMany
    @JoinTable(
            name = "user_followers",
            schema = "mockwitter",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers;

    @ManyToMany
    @JoinTable(
            name = "user_followings",
            schema = "mockwitter",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> followings;


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
