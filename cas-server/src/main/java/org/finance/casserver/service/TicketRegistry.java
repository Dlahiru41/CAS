package org.finance.casserver.service;

import org.finance.casserver.model.ServiceTicket;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;

@Component
public class TicketRegistry {

    private final Map<String, ServiceTicket> ticketStore = new ConcurrentHashMap<>();

    public void addTicket(ServiceTicket ticket) {
        ticketStore.put(ticket.getId(), ticket);
    }

    public Optional<ServiceTicket> getTicket(String ticketId) {
        return Optional.ofNullable(ticketStore.get(ticketId));
    }

    public void removeTicket(String ticketId) {
        ticketStore.remove(ticketId);
    }

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void cleanExpiredTickets() {
        ticketStore.entrySet()
                .removeIf(entry -> entry.getValue().isExpired());
    }
}