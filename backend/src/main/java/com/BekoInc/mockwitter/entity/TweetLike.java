package com.BekoInc.mockwitter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tweet_likes", schema = "mockwitter")
public class TweetLike extends Like {

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    @NotNull(message = "Tweet to like can not be null!")
    private Tweet tweet;

}
