package com.riwi.intro.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="venues")

@Schema(name = "Venue", description = "Entity representing an event venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description="This is the  o the venue", example="123")
    private Integer id;

    @Column(nullable = false,length = 100)
    @Schema(description="The name of the venue",example="Nombre Generico")
    private String name;

    @Column(nullable = false,length = 150)
    @Schema(description = "the location, adress", example="cll 60 # 75 - 100")
    private String address;

    @Column(nullable = false, length = 120)
    @Schema(description = "City where the venue is located", example = "Bogotá")
    private String city;

    @Column(nullable = false)
    @Schema(description="capacity of the place", example="100")
    private int capacity;

    public Venue(Integer id, String name, String address, int capacity) {
        this(id, name, address, "Bogotá", capacity);
    }
}
