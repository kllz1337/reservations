package pl.sda.eventreservationmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.eventreservationmanager.model.AppUser;
import pl.sda.eventreservationmanager.model.dto.RegisterAppUserDto;
import pl.sda.eventreservationmanager.repository.AppUserRepository;

import java.util.Optional;

@Service
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public boolean registerUser(RegisterAppUserDto dto) {
        Optional<AppUser> optionalUser = appUserRepository.findByUsername(dto.getUsername());
        if(optionalUser.isPresent()){
            return false;
        }
        AppUser appUser = new AppUser(null, dto.getUsername(), bCryptPasswordEncoder.encode(dto.getPassword()), null, null);
        appUserRepository.save(appUser);
        return true;
    }
}
