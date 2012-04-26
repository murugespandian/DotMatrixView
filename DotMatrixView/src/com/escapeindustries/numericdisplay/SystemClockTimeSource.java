package com.escapeindustries.numericdisplay;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SystemClockTimeSource implements TimeSource {

	@Override
	public Calendar getNow() {
		return GregorianCalendar.getInstance();
	}

}