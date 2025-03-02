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


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "mockweet", schema = "mockwitter")
public class Mockweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Mockweet ID must be greater than 0!")
    @NotNull(message = "Mockweet ID can not be null!")
    @Column(name = "id" , nullable = false)
    private Long id;

    //tweet body
    @Column(name = "content", nullable = false)
    @Size(min = 1, max = 280, message = "Mockweet must be between 1 and 280 characters!")
    @NotNull(message = "Mockweet can not be null!")
    @NotBlank(message = "Mockweet can not be empty!")
    private String content;

    @CreationTimestamp
    @Column(name = "post_date")
    private LocalDateTime postDate;
}
