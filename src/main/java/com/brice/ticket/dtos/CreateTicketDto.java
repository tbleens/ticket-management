package com.brice.ticket.dtos;


public class CreateTicketDto {
    private String title;

    private String description;

    // getters and setters here...\

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
