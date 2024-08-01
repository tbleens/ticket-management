package com.brice.ticket.dtos;

import com.brice.ticket.entities.Ticket;

public class UpdateTicketDto {
    private String title;

    private String description;

    private Ticket.Status status;

    // getters and setters here...\

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Ticket.Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Ticket.Status getStatus() {
        return status;
    }
}
