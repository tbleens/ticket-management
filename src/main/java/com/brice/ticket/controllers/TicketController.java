package com.brice.ticket.controllers;

import com.brice.ticket.dtos.CreateTicketDto;
import com.brice.ticket.dtos.RegisterUserDto;
import com.brice.ticket.dtos.UpdateTicketDto;
import com.brice.ticket.entities.Ticket;
import com.brice.ticket.entities.User;
import com.brice.ticket.services.TicketService;
import com.brice.ticket.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
    }

    @Tag(name = "get", description = "Method Post pour afficher les tickets d'un utilisateur connecte")
    @GetMapping("")
    public ResponseEntity<List<Ticket>> getAll() {
        List<Ticket> tickets = ticketService.getAll();
        System.out.println(tickets.get(0));
        return ResponseEntity.ok(tickets);
    }

    @Tag(name = "get", description = "Method Get pour afficher un ticket")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Ticket>> getById(@PathVariable Integer id) {
        Optional<Ticket> ticket = ticketService.getById(id);
        return ResponseEntity.ok(ticket);
    }

    @Tag(name = "post", description = "Method Post pour creer un ticket")
    @PostMapping("")
    public ResponseEntity<Ticket> createTicket(@RequestBody CreateTicketDto createTicketDto) {
        Ticket createdTicket = ticketService.createTicket(createTicketDto.getTitle(), createTicketDto.getDescription());
        return ResponseEntity.status(201).body(createdTicket);
    }

    @Tag(name = "put", description = "Method Put pour modifier un ticket")
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> UpdateUser(@PathVariable Integer id, @RequestBody UpdateTicketDto updateTicketDto) {
        Ticket ticket = ticketService.updateTicket(updateTicketDto.getTitle(), updateTicketDto.getDescription(), id,
                updateTicketDto.getStatus());
        return ResponseEntity.ok(ticket);
    }

    @Tag(name = "put", description = "Method Put pour assigner un ticket")
    @PutMapping("/{ticketId}/assign/{userId}")
    public ResponseEntity<Ticket> assignTicketToUser(@PathVariable Integer ticketId, @PathVariable Integer userId) {
        Ticket assignedTicket = ticketService.assignTicketToUser(ticketId, userId);
        return ResponseEntity.ok(assignedTicket);
    }

    @Tag(name = "delete", description = "Method Delete pour supprimer un ticket")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

}
