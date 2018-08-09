package pl.sda.eventreservationmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.eventreservationmanager.model.ReservationEvent;
import pl.sda.eventreservationmanager.service.ReservationEventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/event/")
public class ReservationEventController {
    @Autowired
    ReservationEventService reservationEventService;

    @GetMapping(path = "/add")
    public String openAddEventForm(Model model){
        ReservationEvent event = new ReservationEvent();
        model.addAttribute("event", event);
        LocalDate defaultDate = LocalDate.now();
        model.addAttribute("defaultDate", defaultDate);
        return "addReservationEventForm";
    }

    @PostMapping(path = "/add")
    public String performAddEventToDB(ReservationEvent event){
        System.out.println(reservationEventService.add(event));
        return "redirect:/event/list";
    }

    @GetMapping(path = "/list")
    public String list(Model model){
        List<ReservationEvent> eventsList = reservationEventService.listAllEvents();
        model.addAttribute("eventsList", eventsList);
        return "eventsList";
    }

    @GetMapping(path = "/remove/{id}")
    public String remove(@PathVariable(name = "id") Long id){
        reservationEventService.removeEvent(id);
        return "redirect:/event/list";
    }

    @GetMapping(path = "/details/{id}")
    public String details(Model model, @PathVariable(name = "id") Long id){
        Optional<ReservationEvent> optionalEvent = reservationEventService.find(id);
        if(optionalEvent.isPresent()){
            model.addAttribute("event", optionalEvent.get());
            return "eventDetails";
        } else {
            return "error";
        }
    }
}
