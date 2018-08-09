package pl.sda.eventreservationmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.eventreservationmanager.model.ReservationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetailsDto {
    private Long id;
    private Long eventId;
    private String participantName;
    private ReservationStatus status;
}