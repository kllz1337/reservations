package pl.sda.eventreservationmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.eventreservationmanager.model.AppUser;
import pl.sda.eventreservationmanager.repository.AppUserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {  // UserDetails - zwraca uprawnienia uzytkownika :) dodamy poki co, ze jezeli username jest admin, to ma grube uprawnienia
        Optional<AppUser> optionalExistingUser = appUserRepository.findByUsername(s);
        if(optionalExistingUser.isPresent()){
            AppUser appUser = optionalExistingUser.get();
            return new User(appUser.getUsername(), appUser.getPassword(), getRolesForUser(appUser.getUsername()));
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getRolesForUser(String username) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(username.equals("admin")){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return grantedAuthorities;
    }
}
