package pl.sda.eventreservationmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationDto {
    private String participantName;
    private Long eventId;
}