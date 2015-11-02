package com.kve.chorerota.data;

import java.util.Calendar;
import java.util.Locale;

public class FrequencyUnit {

	private String unitName;
	private int noOfSeconds;
	private Boolean isTimeUnit;
	
	public FrequencyUnit(String unitName) {
		super();
		this.unitName = unitName;
		this.isTimeUnit = false;
		this.setNoOfSeconds(60 * 60 * 24);		//	Day if nothing else
		
		this.setTimeValues(unitName);
	}

	private void setTimeValues(String unitName)
	{
		if (unitName.toLowerCase(Locale.UK).contains("second"))
		{
			this.setIsTimeUnit(true);
			this.setNoOfSeconds(1);
		}
		else if (unitName.toLowerCase(Locale.UK).contains("minute"))
		{
			this.setIsTimeUnit(true);
			this.setNoOfSeconds(60);
		}		
		else if (unitName.toLowerCase(Locale.UK).contains("hour"))
		{
			this.setIsTimeUnit(true);
			this.setNoOfSeconds(60 * 60);
		}	
		else if (unitName.toLowerCase(Locale.UK).contains("day"))
		{
			this.setNoOfSeconds(60 * 60 * 24);
		}	
		else if (unitName.toLowerCase(Locale.UK).contains("week"))
		{
			this.setNoOfSeconds(60 * 60 * 24 * 7);
		}	
		else if (unitName.toLowerCase(Locale.UK).contains("month"))
		{
			Calendar cal = Calendar.getInstance();
			this.setNoOfSeconds(60 * 60 * 24 * 7 * cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}	
		else if (unitName.toLowerCase(Locale.UK).contains("year"))
		{
			Calendar cal = Calendar.getInstance();
			this.setNoOfSeconds(60 * 60 * 24 * 7 * cal.getActualMaximum(Calendar.DAY_OF_YEAR));
		}	
	
	
	}
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public int getNoOfSeconds() {
		return noOfSeconds;
	}
		public Boolean getIsTimeUnit() {
		return isTimeUnit;
	}
	
	//	These are not for public use
	private void setNoOfSeconds(int noOfSeconds) {
		this.noOfSeconds = noOfSeconds;
	}
	private void setIsTimeUnit(Boolean isTimeUnit) {
		this.isTimeUnit = isTimeUnit;
	}

	
}
