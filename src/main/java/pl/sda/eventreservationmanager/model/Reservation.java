package pl.sda.eventreservationmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

// @Data - nie moze byc Data poniewaz toString rowniez sie zapetli i stad StackOverflow
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne  // @JsonIgnore - nie zostalby zwrocony, a @JsonBackReference mowi, zeby nie zaglebiac sie rekurencyjnie
    @JsonBackReference  // Reservation pobierze ReservationEvent, nastepnie ReservationEvent ma liste Reservation wiec pobierze Reservation, i tak w kolko... zapetli sie i krzak
    private ReservationEvent reservationEvent;
    private ReservationStatus status;

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
