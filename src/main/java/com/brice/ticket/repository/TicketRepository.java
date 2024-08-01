package com.brice.ticket.repository;

import com.brice.ticket.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByUserId(Integer userId);

    List<Ticket> findByUserIdAndId(Integer userId, Integer id);
}