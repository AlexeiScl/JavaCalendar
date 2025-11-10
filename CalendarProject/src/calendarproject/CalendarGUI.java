package calendarproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CalendarGUI {
private JFrame frame;
private JPanel calendarPanel;
private JTextArea eventListArea;
private JComboBox<String> monthCombo;
private JComboBox<Integer> yearCombo;

public CalendarGUI() {
frame = new JFrame("Calendar with Events");
frame.setSize(900, 600);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(new BorderLayout());

// Top panel
JPanel topPanel = new JPanel();
String[] months = {
"January","February","March","April","May","June",
"July","August","September","October","November","December"
};
monthCombo = new JComboBox<>(months);

Integer[] years = new Integer[50];
int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
for (int i=0;i<50;i++) years[i]=currentYear-25+i;
yearCombo = new JComboBox<>(years);
yearCombo.setSelectedItem(currentYear);

JButton viewButton = new JButton("View Month");
JButton fullYearButton = new JButton("Full Year");
JButton addEventButton = new JButton("Add Event");

topPanel.add(monthCombo);
topPanel.add(yearCombo);
topPanel.add(viewButton);
topPanel.add(fullYearButton);
topPanel.add(addEventButton);

frame.add(topPanel, BorderLayout.NORTH);

// Calendar grid
calendarPanel = new JPanel();
calendarPanel.setLayout(new GridLayout(0,7,5,5));
frame.add(calendarPanel, BorderLayout.CENTER);

// Event list
eventListArea = new JTextArea(15,20);
eventListArea.setEditable(false);
eventListArea.setFont(new Font("Monospaced",Font.PLAIN,12));
frame.add(new JScrollPane(eventListArea), BorderLayout.EAST);

// Button actions
viewButton.addActionListener(e -> drawMonth());
fullYearButton.addActionListener(e -> drawFullYear());
addEventButton.addActionListener(e -> addEventDialog());

frame.setVisible(true);
drawMonth();
}

private void drawMonth() {
calendarPanel.removeAll();
int month = monthCombo.getSelectedIndex() + 1;
int year = (Integer) yearCombo.getSelectedItem();

String[] days = {"Su","Mo","Tu","We","Th","Fr","Sa"};
for (int i=0;i<days.length;i++) {
JLabel label = new JLabel(days[i],SwingConstants.CENTER);
label.setFont(new Font("Arial",Font.BOLD,12));
if(i==0 || i==6) label.setForeground(Color.GRAY);
calendarPanel.add(label);
}

int startDay = DateUtils.getStartDay(month,year);
int daysInMonth = DateUtils.getDaysInMonth(month,year);

for(int i=1;i<startDay;i++) calendarPanel.add(new JLabel(""));

for(int day=1;day<=daysInMonth;day++){
List<String> events = EventManager.getEvents(day,month,year);
boolean isToday = DateUtils.isToday(day,month,year);
String text = String.valueOf(day);
if(!events.isEmpty()) text+="*"+events.size();

JButton dayButton = new JButton(text);
dayButton.setMargin(new Insets(2,2,2,2));
if(isToday) {
dayButton.setBackground(Color.CYAN);
dayButton.setFont(dayButton.getFont().deriveFont(Font.BOLD));
}
if(!events.isEmpty()) dayButton.setForeground(Color.RED);

int dayIndex = (day + startDay - 2) % 7;
if(dayIndex==0 || dayIndex==6) dayButton.setBackground(new Color(230,230,230));

int d = day;
dayButton.addActionListener(ev -> showEventsForDay(d,month,year));
calendarPanel.add(dayButton);
}

calendarPanel.revalidate();
calendarPanel.repaint();
updateEventList(month,year);
}

private void drawFullYear(){
JFrame yearFrame = new JFrame("Full Year View");
yearFrame.setSize(1000,800);
yearFrame.setLayout(new GridLayout(4,3));

int year = (Integer) yearCombo.getSelectedItem();
for(int month=1;month<=12;month++){
JTextArea monthArea = new JTextArea();
monthArea.setEditable(false);
monthArea.setFont(new Font("Monospaced",Font.PLAIN,12));
monthArea.setText(getMonthString(month,year));
yearFrame.add(new JScrollPane(monthArea));
}
yearFrame.setVisible(true);
}

private String getMonthString(int month,int year){
StringBuilder sb = new StringBuilder();
String[] dayNames = {"Su","Mo","Tu","We","Th","Fr","Sa"};
sb.append("     ").append(monthCombo.getItemAt(month-1)).append(" ").append(year).append("\n");
for(String d : dayNames) sb.append(d).append(" ");
sb.append("\n");

int startDay = DateUtils.getStartDay(month,year);
int daysInMonth = DateUtils.getDaysInMonth(month,year);
for(int i=1;i<startDay;i++) sb.append("   ");

for(int day=1;day<=daysInMonth;day++){
List<String> events = EventManager.getEvents(day,month,year);
boolean isToday = DateUtils.isToday(day,month,year);
String text = String.format("%2d", day);
if(!events.isEmpty()) text+="*";
else text+=" ";
sb.append(text).append(" ");
if((day+startDay-1)%7==0) sb.append("\n");
}
sb.append("\n\nEvents:\n");
return sb.toString();
}

private void addEventDialog(){
JTextField dayField = new JTextField();
JTextField monthField = new JTextField();
JTextField yearField = new JTextField();
JTextField nameField = new JTextField();

Object[] message = {
"Day:", dayField,
"Month:", monthField,
"Year:", yearField,
"Event Name:", nameField
};
int option = JOptionPane.showConfirmDialog(frame,message,"Add Event",JOptionPane.OK_CANCEL_OPTION);
if(option==JOptionPane.OK_OPTION){
try{
int day = Integer.parseInt(dayField.getText());
int month = Integer.parseInt(monthField.getText());
int year = Integer.parseInt(yearField.getText());
String name = nameField.getText();
EventManager.addEvent(day,month,year,name);
JOptionPane.showMessageDialog(frame,"Event added!");
drawMonth();
}catch(Exception e){
JOptionPane.showMessageDialog(frame,"Invalid input!");
}
}
}

private void showEventsForDay(int day,int month,int year){
List<String> events = EventManager.getEvents(day,month,year);
if(events.isEmpty()){
JOptionPane.showMessageDialog(frame,"No events for "+day+"/"+month+"/"+year);
} else {
StringBuilder sb = new StringBuilder();
sb.append("Events for ").append(day).append("/").append(month).append("/").append(year).append(":\n");
for(String e: events) sb.append("- ").append(e).append("\n");
JOptionPane.showMessageDialog(frame,sb.toString());
}
}

private void updateEventList(int month,int year){
StringBuilder sb = new StringBuilder();
sb.append("Events for ").append(month).append("/").append(year).append(":\n");
boolean found=false;
for(int day=1;day<=DateUtils.getDaysInMonth(month,year);day++){
List<String> events = EventManager.getEvents(day,month,year);
if(!events.isEmpty()){
for(String e: events){
sb.append(day).append(": ").append(e).append("\n");
found=true;
}
}
}
if(!found) sb.append("(none)");
eventListArea.setText(sb.toString());
}

public static void main(String[] args){
SwingUtilities.invokeLater(CalendarGUI::new);
}
}
