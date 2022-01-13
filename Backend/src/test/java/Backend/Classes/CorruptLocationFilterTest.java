package Backend.Classes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CorruptLocationFilterTest {

    @Test
    void calculateDistance() {
        CorruptLocationFilter test = new CorruptLocationFilter();
        double distance = test.calculateDistance(47.0783991, 21.1969872, 47.1029893, 21.2110634);
        assertEquals(2.937915271299951, distance);
    }


    @Test
    void createFalseTripEntry() {
        CorruptLocationFilter test = new CorruptLocationFilter();
        TripEntry real = new TripEntry("bruh", 1, 1, 1, ZonedDateTime.now(), 1, 1, 1, true);
        TripEntry fakeTrip = test.createFalseTripEntry(real, 47.0783991, 21.1969872, 1);
        assertEquals(47.0783991, fakeTrip.getLat());
        assertEquals(21.1969872, fakeTrip.getLon());
        assertEquals(1, fakeTrip.getAlt());
    }
}