package com.brice.ticket;

import com.brice.ticket.controllers.TicketController;
import com.brice.ticket.dtos.CreateTicketDto;
import com.brice.ticket.dtos.UpdateTicketDto;
import com.brice.ticket.entities.Ticket;
import com.brice.ticket.entities.User;
import com.brice.ticket.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Ticket ticket = new Ticket();
//        ticket.setId(1);
        ticket.setTitle("Test Ticket");

        when(ticketService.getAll()).thenReturn(Arrays.asList(ticket));

        ResponseEntity<List<Ticket>> response = ticketController.getAll();
        assertEquals(1, response.getBody().size());
        assertEquals("Test Ticket", response.getBody().get(0).getTitle());
    }

    @Test
    void testGetById() {
        Ticket ticket = new Ticket();
//        ticket.setId(1);
        ticket.setTitle("Test Ticket");

        when(ticketService.getById(1)).thenReturn(Optional.of(ticket));

        ResponseEntity<Optional<Ticket>> response = ticketController.getById(1);
        assertEquals("Test Ticket", response.getBody().get().getTitle());
    }

    @Test
    void testCreateTicket() {
        CreateTicketDto dto = new CreateTicketDto();
        dto.setTitle("New Ticket");
        dto.setDescription("This is a new ticket.");
        Ticket ticket = new Ticket();
//        ticket.setId(1);
        ticket.setTitle("New Ticket");

        when(ticketService.createTicket("New Ticket", "This is a new ticket.")).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.createTicket(dto);
        assertEquals("New Ticket", response.getBody().getTitle());
    }

    @Test
    void testUpdateTicket() {
        UpdateTicketDto dto = new UpdateTicketDto();
        dto.setTitle("Updated Ticket");
        dto.setDescription("This is an updated ticket.");
        dto.setStatus(Ticket.Status.CLOSE);
        Ticket ticket = new Ticket();
//        ticket.setId(1);
        ticket.setTitle("Updated Ticket");

        when(ticketService.updateTicket("Updated Ticket", "This is an updated ticket.", 1, Ticket.Status.CLOSE)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.UpdateUser(1, dto);
        assertEquals("Updated Ticket", response.getBody().getTitle());
    }

    @Test
    void testAssignTicketToUser() {
        Ticket ticket = new Ticket();
//        ticket.setId(1);
        ticket.setTitle("Test Ticket");

        when(ticketService.assignTicketToUser(1, 1)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.assignTicketToUser(1, 1);
        assertEquals("Test Ticket", response.getBody().getTitle());
    }

    @Test
    void testDeleteTicket() {
        doNothing().when(ticketService).deleteTicket(1);

        ResponseEntity<Void> response = ticketController.deleteTicket(1);
        assertEquals(204, response.getStatusCodeValue());

        verify(ticketService, times(1)).deleteTicket(1);
    }
}
