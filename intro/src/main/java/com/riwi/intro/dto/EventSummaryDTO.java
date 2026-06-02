package com.riwi.intro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Projection used for event listings without loading full entities")
public record EventSummaryDTO(
        @Schema(description = "Event identifier", example = "1")
        Integer id,
        @Schema(description = "Event name", example = "Spring Summit")
        String eventName,
        @Schema(description = "Event date in ISO format", example = "2026-08-14")
        String date,
        @Schema(description = "Venue name", example = "Main Hall")
        String venueName,
        @Schema(description = "Venue city", example = "Bogotá")
        String city
) {
}
