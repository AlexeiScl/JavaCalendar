package calendarproject;

import java.util.*;

public class EventManager {
private static Map<String, List<String>> events = FileHandler.loadEvents();

public static void addEvent(int day, int month, int year, String eventName) {
String key = year + "-" + month + "-" + day;
events.putIfAbsent(key, new ArrayList<>());
events.get(key).add(eventName);
FileHandler.saveEvents(events);
}

public static List<String> getEvents(int day, int month, int year) {
String key = year + "-" + month + "-" + day;
return events.getOrDefault(key, new ArrayList<>());
}

public static void printEventsForMonth(int month, int year) {
boolean found = false;
System.out.println("\n📅 Events This Month:");
for (Map.Entry<String, List<String>> e : events.entrySet()) {
String[] parts = e.getKey().split("-");
int eYear = Integer.parseInt(parts[0]);
int eMonth = Integer.parseInt(parts[1]);
int eDay = Integer.parseInt(parts[2]);
if (eYear == year && eMonth == month) {
for (String event : e.getValue()) {
System.out.println("  " + eDay + ": " + event);
}
found = true;
}
}
if (!found) System.out.println("  (none)");
}

public static Map<String, List<String>> getAllEvents() {
return events;
}
}
