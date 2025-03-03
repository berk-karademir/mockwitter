package com.BekoInc.mockwitter.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "retweet", schema = "mockwitter")
public class Retweet{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @CreationTimestamp
    @Column(name = "retweet_date")
    private LocalDateTime retweetDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull( message = "User can not be null who retweets a tweet")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    @NotNull( message = "Retweeted tweet can not be null.")
    private Tweet tweet;



}
