package edu.tcu.cs.backend.availability;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object for creating or updating availability")
public class AvailabilityRequestDTO {
    
    @Schema(description = "Whether the crew member is available for the game", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean available;
    
    @Schema(description = "Optional comment or note about the availability", example = "Available in the afternoon only")
    private String comment;

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
