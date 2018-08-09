package pl.sda.eventreservationmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.eventreservationmanager.model.Reservation;
import pl.sda.eventreservationmanager.model.ReservationEvent;
import pl.sda.eventreservationmanager.model.dto.CreateReservationDto;
import pl.sda.eventreservationmanager.model.dto.ReservationDetailsDto;
import pl.sda.eventreservationmanager.service.ReservationEventService;
import pl.sda.eventreservationmanager.service.ReservationService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/reservation/")
public class ReservationController {
    @Autowired
    private ReservationEventService reservationEventService;
    @Autowired
    private ReservationService reservationService;

    @GetMapping(path = "/add")
    public String addReservation(Model model) {
        CreateReservationDto reservation = new CreateReservationDto();
        List<ReservationEvent> events = reservationEventService.listAllEvents();
        model.addAttribute("reservation", reservation);
        model.addAttribute("events", events);
        return "addReservationForm";
    }

    @PostMapping(path = "/add")
    public String addReservationToDB(CreateReservationDto reservation, RedirectAttributes ra) {
        boolean areTicketsAvailable = reservationEventService.checkAvailableTickets(reservation.getEventId());
        if(areTicketsAvailable){
            System.out.println(reservationService.add(reservation));
            return "redirect:/reservation/list";
        } else {
            ra.addFlashAttribute("warningAttr", "No more tickets available");
            return "redirect:/reservation/list";
        }
    }

    @GetMapping(path = "/list")
    public String listReservations(Model model) {
        List<Reservation> reservationsList = reservationService.listReservations();
        model.addAttribute("reservationsList", reservationsList);
        return "reservationsList";
    }

    @GetMapping(path = "/remove/{id}")
    public String removeReservation(@PathVariable(name = "id") Long id) {
        reservationService.removeReservation(id);
        return "redirect:/reservation/list";
    }

    @GetMapping(path = "/details/{id}")
    public String getReservationDetails(@PathVariable(name = "id") Long id, Model model) {
        Optional<Reservation> optionalReservation = reservationService.find(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            ReservationDetailsDto reservationDetails = new ReservationDetailsDto(
                    reservation.getId(),
                    reservation.getReservationEvent().getEventId(),
                    reservation.getName(),
                    reservation.getStatus()
            );
            model.addAttribute("reservation", reservationDetails);
            return "reservationDetails";
        } else {
            return "error";
        }
    }

    @PostMapping(path = "/details")
    public String setReservationDetails(ReservationDetailsDto reservation) {
        System.out.println("DTO: " + reservation);
        Optional<ReservationEvent> optionalReservationEvent = reservationEventService.find(reservation.getEventId());
        if (optionalReservationEvent.isPresent()) {
            Reservation modifiedReservation = new Reservation(
                    reservation.getId(),
                    reservation.getParticipantName(),
                    optionalReservationEvent.get(),
                    reservation.getStatus()
            );
            System.out.println("PRZED DB: " + modifiedReservation);
            reservationService.saveReservation(modifiedReservation);
        }
        return "redirect:/reservation/list";
    }
}
