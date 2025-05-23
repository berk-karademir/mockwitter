package com.BekoInc.mockwitter.entity;


import com.BekoInc.mockwitter.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @CreationTimestamp
    @Column(name = "like_date")
    private LocalDateTime likeDate = LocalDateTime.now();


    @ManyToOne
    @NotNull(message = "User can not be null who likes a tweet or comment.")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;






}
