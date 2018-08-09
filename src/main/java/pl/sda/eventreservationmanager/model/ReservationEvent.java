package pl.sda.eventreservationmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @NotNull
    private String eventName;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate eventDate;
    @NotNull
    private int reservationsLimit;
    private String eventDescription;
    @OneToMany(mappedBy = "reservationEvent")  // powiazanie z klasa Reservation, mappedBy wskazuje
    private List<Reservation> reservations;
}