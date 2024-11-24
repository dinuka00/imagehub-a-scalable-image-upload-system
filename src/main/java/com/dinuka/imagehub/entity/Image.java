package com.dinuka.imagehub.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String location;

    private String size;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploaded_at;



}
