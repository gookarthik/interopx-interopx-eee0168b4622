package com.interopx.platform.transformation.utill;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {

	public static Date convertStringToDate(String dateInString) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = formatter.parse(dateInString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
}
