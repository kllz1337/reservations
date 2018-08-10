package pl.sda.eventreservationmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAppUserDto {
    private String username;
    private String password;
    private String confirm_password;
}
