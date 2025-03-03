package com.BekoInc.mockwitter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name = "comment_likes", schema = "mockwitter")
public class CommentLike extends Like {

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    @NotNull(message = "Comment to like can not be null!")
    private Comment comment;

}
