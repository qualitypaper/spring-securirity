package com.qualitypaper.co_tourism.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating;
    private String content;
    @OneToOne
    @JoinColumn(name = "review_from")
    private User reviewFrom;
    @OneToOne
    @JoinColumn(name = "review_to")
    private User reviewTo;
}
