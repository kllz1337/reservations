package pl.sda.eventreservationmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventreservationmanager.model.AppUser;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
//    @Query(nativeQuery = "select * from users");
//    String countThis();

    Optional<AppUser> findByUsername(String username);
}
