package pl.sda.eventreservationmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventreservationmanager.model.Reservation;
import pl.sda.eventreservationmanager.model.ReservationEvent;
import pl.sda.eventreservationmanager.model.ReservationStatus;
import pl.sda.eventreservationmanager.model.dto.CreateReservationDto;
import pl.sda.eventreservationmanager.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationEventService reservationEventService;

    public Reservation add(CreateReservationDto reservation) {
        Optional<ReservationEvent> optionalEvent = reservationEventService.find(reservation.getEventId());
        if (optionalEvent.isPresent()) {
            ReservationEvent reservationEvent = optionalEvent.get();
            Reservation res = new Reservation(null, reservation.getParticipantName(), reservationEvent, ReservationStatus.UNCONFIRMED);
            return saveReservation(res);
        }
        return null;
    }

    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public List<Reservation> listReservations(){
        return reservationRepository.findAll();
    }

    public void removeReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            reservationRepository.delete(optionalReservation.get());
        }
    }

    public Optional<Reservation> find(Long id) {
        return reservationRepository.findById(id);
    }
}
