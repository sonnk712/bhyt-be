package com.sqa.bhyt.common.ultis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
	public static Date convertStringToDate(String dateInString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formatter.parse(dateInString);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	public static String dateToStringStartWithDDMMYYY(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = dateFormat.format(date);
		return strDate;
	}
	
}	
