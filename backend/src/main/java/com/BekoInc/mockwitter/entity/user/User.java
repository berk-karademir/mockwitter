package com.BekoInc.mockwitter.entity.user;

import com.BekoInc.mockwitter.entity.*;
import com.BekoInc.mockwitter.util.Regexes;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Data
@Entity
@Table(name = "user", schema = "mockwitter")
public class User implements UserDetails {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "User ID must be greater than 0!")
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
    private String username;


    @Column(name = "password", nullable = false)
    @NotNull(message = "Password can not be null!")
    @NotBlank(message = "Password can not be empty!")
//   @Pattern(regexp = Regexes.PASSWORD_REGEX, message = "Password must be 8-20 characters long, include at least one uppercase letter, one lowercase letter, one digit, one special character and white space is not allowed!")
    private String password;

    @Column(name = "phone_number", unique = true)
    @Pattern(regexp= Regexes.PHONE_NUMBER_REGEX, message = "Phone number is not valid!")
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "registration_date", updatable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tweet> tweets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Retweet> retweets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TweetLike> tweetLikes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            schema = "mockwitter",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> authorities = new HashSet<>();



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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
