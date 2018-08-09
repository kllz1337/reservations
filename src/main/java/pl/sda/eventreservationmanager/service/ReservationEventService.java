package pl.sda.eventreservationmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventreservationmanager.model.Reservation;
import pl.sda.eventreservationmanager.model.ReservationEvent;
import pl.sda.eventreservationmanager.repository.ReservationEventRepository;
import pl.sda.eventreservationmanager.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationEventService {
    @Autowired
    private ReservationEventRepository reservationEventRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationEvent add(ReservationEvent event) {
        return reservationEventRepository.save(event);
    }

    public List<ReservationEvent> listAllEvents() {
        return reservationEventRepository.findAll();
    }

    public void removeEvent(Long id) {
        reservationEventRepository.deleteById(id);
    }

    public Optional<ReservationEvent> find(Long id) {
        return reservationEventRepository.findById(id);
    }

    public boolean checkAvailableTickets(Long eventId) {
        Optional<ReservationEvent> optionalEvent = reservationEventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            return isTicketAvailable(optionalEvent.get());
        }
        return false;
    }

    private boolean isTicketAvailable(ReservationEvent reservationEvent) {
        List<Reservation> reservations = reservationEvent.getReservations();
        int reservationsCounter = reservations.size();
        if (reservationsCounter < reservationEvent.getReservationsLimit()) {
            return true;
        }
        return false;
    }
}
