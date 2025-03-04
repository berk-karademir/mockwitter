package com.BekoInc.mockwitter.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tweet", schema = "mockwitter")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Tweet ID must be greater than 0!")
    @NotNull(message = "Tweet ID can not be null!")
    @Column(name = "id" , nullable = false)
    private Long id;

    //tweet body
    @Column(name = "content", nullable = false)
    @Size(min = 1, max = 280, message = "Tweet must be between 1 and 280 characters!")
    @NotNull(message = "Tweet can not be null!")
    @NotBlank(message = "Tweet can not be empty!")
    private String content;

    @CreationTimestamp
    @Column(name = "post_date")
    private LocalDateTime postDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TweetLike> likes;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Retweet> retweets;

}
