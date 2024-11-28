package de.gaustadtasv.turnierorganisator.configuration;

import java.time.LocalTime;
import java.util.List;

public record TurnierConfiguration(Modus modus, List<String> teams, long gametime, long pause, String name,
                                   LocalTime starttime) {
}
