package com.BekoInc.mockwitter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "retweet_likes", schema = "mockwitter")
public class RetweetLike extends Like {

    @ManyToOne
    @JoinColumn(name = "retweet_id", nullable = false)
    @NotNull(message = "Retweet to like can not be null!")
    private Retweet retweet;
}
