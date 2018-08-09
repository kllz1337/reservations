package pl.sda.eventreservationmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.eventreservationmanager.model.ReservationEvent;

@Repository
public interface ReservationEventRepository extends JpaRepository<ReservationEvent, Long> {
}
