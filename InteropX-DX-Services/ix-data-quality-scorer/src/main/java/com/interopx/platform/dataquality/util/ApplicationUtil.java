package com.interopx.platform.dataquality.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


import com.interopx.platform.repository.model.DataQualityIssues;

public class ApplicationUtil {

	public static boolean validateBirthDate(String birthDate) {
		boolean isValid = false;
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date parsedDate = dateFormat.parse(birthDate);
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		    isValid = timestamp.before(new Timestamp(new Date().getTime()));
		} catch(Exception e) { 
		    // look the origin of exception 
		}
		return isValid;
	}
	
	public static boolean validateName(String name) {
		boolean isValid = false;
		Pattern pattern  = Pattern.compile("^[A-Za-z]+$");
		if(pattern.matcher(name).matches()) {
			isValid = true;
		}
		return isValid;
	}
	
	public static float calculateRubricScore(int maxPoints, int actualPoints)
	{
		if(maxPoints!=0)
		{
			return (float)actualPoints/(float)maxPoints;
		}else 
			return 0;
	}
	
	public static int calculateSectionGradeAndIssues(List<DataQualityIssues> dataQualityIssues)
	{
		float actualPoints =0;
		float maxPoints = 0;
		int percentage ;
		
		for(DataQualityIssues issues : dataQualityIssues)
		{
			actualPoints = actualPoints + issues.getRubricScore();
			maxPoints++;
		}
		percentage = Math.round((actualPoints * 100)/maxPoints);
		return percentage;
		
	}

}
