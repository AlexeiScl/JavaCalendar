package calendarproject;

import java.util.List;
import java.util.Scanner;

public class CalendarProject {
public static void main(String[] args) {
Scanner input = new Scanner(System.in);
System.out.println("==== Calendar with Events ====");

while (true) {
System.out.println("\n1. View Calendar");
System.out.println("2. View Full Year");
System.out.println("3. Add Event");
System.out.println("4. Exit");
System.out.print("Choose an option: ");
int choice = input.nextInt();

if (choice == 1) {
System.out.print("Enter month (1-12): ");
int month = input.nextInt();
System.out.print("Enter year: ");
int year = input.nextInt();
printCalendar(month, year);
EventManager.printEventsForMonth(month, year);
}
else if (choice == 2) {
System.out.print("Enter year: ");
int year = input.nextInt();
for (int month = 1; month <= 12; month++) {
printCalendar(month, year);
EventManager.printEventsForMonth(month, year);
System.out.println("\n----------------------\n");
}
}
else if (choice == 3) {
System.out.print("Enter event day (1-31): ");
int day = input.nextInt();
System.out.print("Enter month (1-12): ");
int month = input.nextInt();
System.out.print("Enter year: ");
int year = input.nextInt();
input.nextLine(); // clear buffer
System.out.print("Enter event name: ");
String name = input.nextLine();
EventManager.addEvent(day, month, year, name);
System.out.println("✅ Event added!");
}
else if (choice == 4) {
System.out.println("Goodbye!");
break;
}
else {
System.out.println("Invalid choice, try again.");
}
}

input.close();
}

public static void printCalendar(int month, int year) {
String[] months = {
"", "January", "February", "March", "April", "May", "June",
"July", "August", "September", "October", "November", "December"
};

int daysInMonth = DateUtils.getDaysInMonth(month, year);

System.out.println("\n     " + months[month] + " " + year);
System.out.println("Su  Mo  Tu  We  Th  Fr  Sa");

int startDay = DateUtils.getStartDay(month, year);

for (int i = 1; i < startDay; i++) {
System.out.print("    "); // 4 spaces
}

for (int day = 1; day <= daysInMonth; day++) {
List<String> dayEvents = EventManager.getEvents(day, month, year);
boolean isToday = DateUtils.isToday(day, month, year);

String dayStr;
if (!dayEvents.isEmpty()) {
dayStr = String.format("%2d*%d", day, dayEvents.size());
} else if (isToday) {
dayStr = String.format("[%2d]", day);
} else {
dayStr = String.format("%2d  ", day);
}

while (dayStr.length() < 4) dayStr += " ";
System.out.print(dayStr);

if (((day + startDay - 1) % 7 == 0) || (day == daysInMonth)) {
System.out.println();
}
}
}
}
