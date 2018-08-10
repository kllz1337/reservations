package pl.sda.eventreservationmanager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.sda.eventreservationmanager.service.AppUserDetailsService;

@Configuration
public class BasicSecurity extends WebSecurityConfigurerAdapter {
    // Stworzenie Beana BCrypta do szyfrowania hasła
    // Bean - może być stworzony przez metodę jak poniżej, albo pole może być Beanem
    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Ograniczenie dostępu do fragmentów aplikacji dla niezalogowanych i sparowanie logowania SPringa z naszym formularzem :) liczą się pola name dla springa
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()    // 1: zasady autoryzacji wszystkich zapytan
                .antMatchers("/","/about","/register","/webjars/**","/css/**").permitAll() // REGULA 1: adresy do których nie potrzeba logowania (webjars to bootstrap i jquery), ** to cssy : zezwól wszystkim
                .anyRequest()   // 2: wszystko pozostałe...
                        .authenticated()    // musi być zalogowane
                .and().formLogin()  // 3 : dodajemy do login...
                        .loginPage("/login")    // ... mapowanie na nszą aplikację
                        .defaultSuccessUrl("/")
                        .permitAll()            // ...zezwalamy wszystkim
                .and().logout()     // 4 : dla logouta...
                        .clearAuthentication(true)
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true);
    }

    // TWORZYMY DAO AUTHENTICATE PROVIDERA poniewaz chcemy sie logowac w oparciu o baze danych
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());  // dostawca szyfrowania
        daoAuthenticationProvider.setUserDetailsService(appUserDetailsService); // dostawca logowania - appUserDetailsService
        return daoAuthenticationProvider;
    }

    // AUTHENTICATIONMANAGERBUILDER - ustawiamy dostarczyliśmy DOSTAWCY LOGOWANIA dostawcę tego naszego dao providera
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
}
