package com.hieuduy.core.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image_model")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_model_id")
    private Long id;
    private String name;
    private String type;
    @Column(length = 50000000)
    private byte[] picByte;

}
