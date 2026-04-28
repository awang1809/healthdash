package ui;

import model.Event;
import model.EventLog;

// Represents a LogPrinter 
public class LogPrinter {
    EventLog el;

    // EFFECTS: construts a LogPrinter with the EventLog
    public LogPrinter(EventLog el) {
        this.el = el;
    }

    // EFFECTS: prints the log to console
    public void printLog() {
        for (Event e: el) {
            System.out.println(e.toString());
        }
    }
}
