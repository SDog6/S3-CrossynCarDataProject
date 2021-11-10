package Backend.DatabaseAccess;

import Backend.Classes.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITripRepo extends JpaRepository<Trip,Long> {

    Trip GetTripByName(String name);
}
