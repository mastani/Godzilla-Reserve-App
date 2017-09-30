package ir.mastani.godzilla.item;

import java.util.ArrayList;

public class Week {
    private String date;
    private ArrayList<Day> days;

    public Week() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public void addDay(Day day) {
        this.days.add(day);
    }
}
