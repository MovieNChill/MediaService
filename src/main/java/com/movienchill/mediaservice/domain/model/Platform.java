package com.movienchill.mediaservice.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "platform")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_media", referencedColumnName = "id")
    private Media media;

    @Column(name = "platform_name")
    private String platformName;
}
