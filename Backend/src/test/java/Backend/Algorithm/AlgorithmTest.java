package Backend.Algorithm;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {

    @Test
    void makeTrips() {
        Algorithm algorithm = new Algorithm();

        List<TripEntry> tripEntries = new ArrayList<>();
        tripEntries.add(new TripEntry("second", 1, 1, 1, ZonedDateTime.now(), 1, 1, 1, true));
        tripEntries.add(new TripEntry("second", 2, 1, 1, ZonedDateTime.now(), 1, 1, 1, true));
        tripEntries.add(new TripEntry("second", 3, 1, 1, ZonedDateTime.now(), 1, 1, 1, true));
        tripEntries.add(new TripEntry("second", 4, 1, 1, ZonedDateTime.now(), 1, 1, 1, true));

        List<Trip> created = algorithm.MakeTrips(tripEntries);
        Trip trip = created.get(0);
        TripEntry tripEntry = (TripEntry) trip.GetAllTripEntries().get(0);
        assertEquals(2, tripEntry.getLat());
    }
}