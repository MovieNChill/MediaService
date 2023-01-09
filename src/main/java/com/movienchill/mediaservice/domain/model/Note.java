package com.movienchill.mediaservice.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_user")
    private Long idUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_media", referencedColumnName = "id")
    private Media media;

    @Column(name = "note")
    private Float note;
}
