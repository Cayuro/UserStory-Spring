package com.riwi.intro.models;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name="Venue")
public class Venue {
    @Schema(description="This is the id of the venue", example="123")
    private int id;

    @Schema(description="The name of the venue",example="Nombre Generico")
    private String name;

    @Schema(description = "the location, adress", example="cll 60 # 75 - 100")
    private String direction;

    @Schema(description="capacity of the place", example="18")
    private int Capacity;
}
