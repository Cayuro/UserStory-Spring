package com.riwi.intro.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@Schema(name = "Category", description = "Event category used to classify events")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Category identifier", example = "1")
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "Category name", example = "Rock")
    private String name;

    @Column(nullable = false, length = 255)
    @Schema(description = "Category description", example = "Live music and concerts")
    private String description;
}
