package ui;

import exceptions.LogException;
import model.EventLog;

/**
 * Defines behaviours that event log printers must support.
 */
public interface LogPrinter {
    /**
     * Prints the log
     * @param el  the event log to be printed
     * @throws LogException when printing fails for any reason
     */
    String printLog(EventLog el) throws LogException;
}
