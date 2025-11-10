package calendarproject;

import java.io.*;
import java.util.*;

public class FileHandler {
private static final String FILE_NAME = "events.txt";

public static void saveEvents(Map<String, List<String>> events) {
try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
for (Map.Entry<String, List<String>> entry : events.entrySet()) {
String key = entry.getKey();
for (String event : entry.getValue()) {
writer.println(key + "|" + event);
}
}
} catch (IOException e) {
System.out.println("Error saving events: " + e.getMessage());
}
}

public static Map<String, List<String>> loadEvents() {
Map<String, List<String>> events = new HashMap<>();
File file = new File(FILE_NAME);
if (!file.exists()) return events;

try (Scanner scanner = new Scanner(file)) {
while (scanner.hasNextLine()) {
String line = scanner.nextLine();
String[] parts = line.split("\\|");
if (parts.length == 2) {
String key = parts[0];
String event = parts[1];
events.putIfAbsent(key, new ArrayList<>());
events.get(key).add(event);
}
}
} catch (IOException e) {
System.out.println("Error loading events: " + e.getMessage());
}
return events;
}
}
