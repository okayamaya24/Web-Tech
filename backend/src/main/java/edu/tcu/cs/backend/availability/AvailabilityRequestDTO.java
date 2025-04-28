package edu.tcu.cs.backend.availability;

public class AvailabilityRequestDTO {
    private Boolean available;
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
