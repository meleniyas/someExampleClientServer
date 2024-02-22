package main;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CabeceraDate {
	
	public String currentDate() {

		DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		return "Date: " +df.format(new Date());
	}

}
