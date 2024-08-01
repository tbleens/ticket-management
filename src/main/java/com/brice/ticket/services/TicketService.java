package com.brice.ticket.services;

import com.brice.ticket.entities.Ticket;
import com.brice.ticket.entities.User;
import com.brice.ticket.repository.TicketRepository;
import com.brice.ticket.repository.UserRepository;
import com.brice.ticket.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public List<Ticket> getAll() {
        String username = SecurityUtils.getAuthenticatedUsername(); // Utilisation de la méthode pour obtenir l'utilisateur connecté
        if (username == null) {
            throw new RuntimeException("No authenticated user found");
        }
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Ticket> ticket = new ArrayList<>();
        ticketRepository.findByUserId(user.getId()).forEach(ticket::add);
//        ticketRepository.findAll().forEach(ticket::add);
        return ticket;
    }

    public Optional<Ticket> getById(Integer id) {
        String username = SecurityUtils.getAuthenticatedUsername();
        if (username == null) {
            throw new RuntimeException("No authenticated user found");
        }
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to get this ticket");
        }

        return ticketRepository.findById(id);
    }

    public Ticket createTicket(String title, String description) {
        String username = SecurityUtils.getAuthenticatedUsername(); // Utilisation de la méthode pour obtenir l'utilisateur connecté
        if (username == null) {
            throw new RuntimeException("No authenticated user found");
        }
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setUser(user);

        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(String title, String description, Integer idTicket, Ticket.Status status) {
        String username = SecurityUtils.getAuthenticatedUsername();
        if (username == null) {
            throw new RuntimeException("No authenticated user found");
        }
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to modify this ticket");
        }

        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setStatus(status);

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Integer id) {
        String username = SecurityUtils.getAuthenticatedUsername();
        if (username == null) {
            throw new RuntimeException("No authenticated user found");
        }
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to delete this ticket");
        }

        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Ticket not found");
        }
        ticketRepository.deleteById(id);
    }

    public Ticket assignTicketToUser(Integer ticketId, Integer userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ticket.setUser(user);
        return ticketRepository.save(ticket);
    }


}
