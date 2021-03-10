package fr.soro.utilities;

import fr.soro.entities.Reservation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Timer;

@Component
public class ReservationTimers extends HashMap<Reservation, Timer> {
}
