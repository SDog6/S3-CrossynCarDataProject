package Backend.Classes;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TripTest {

    @Test
    void sortbyTime() {
        Trip p = new Trip("first", "car", ZonedDateTime.now().minusDays(1),ZonedDateTime.now(), true);
        p.AddTripEntry(new TripEntry("second", 1, 1, 1, ZonedDateTime.now(), 1, 1, 1, true));
        p.AddTripEntry(new TripEntry("first", 1, 1, 1, ZonedDateTime.now().plusDays(1), 1, 1, 1, true));
        p.setEntries(p.SortbyTime(p.getEntries()));
        TripEntry entry = (TripEntry)p.GetAllTripEntries().get(0);
        assertEquals("first", entry.getVehicleID());
    }

    @Test
    void getEntriesWithoutFake() {
        Trip p = new Trip("first", "car", ZonedDateTime.now().minusDays(1),ZonedDateTime.now(), true);
        TripEntry fake = new TripEntry("second", 1, 1, 1, ZonedDateTime.now(), 1, 1, 1, true);
        fake.setFake(true);
        TripEntry real = new TripEntry("first", 1, 1, 1, ZonedDateTime.now().plusDays(1), 1, 1, 1, true);
        p.AddTripEntry(fake);
        p.AddTripEntry(real);
        TripEntry entry = p.getEntriesWithoutFake().get(0);
        assertEquals("first", entry.getVehicleID());
    }
}