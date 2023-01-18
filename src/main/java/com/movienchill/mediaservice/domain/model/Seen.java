package com.movienchill.mediaservice.domain.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "seen")
@IdClass(Seen.class)
public class Seen {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private Media media;

    @Column(name = "tmdb_id", nullable = false)
    private Long tmdb_id;


}
