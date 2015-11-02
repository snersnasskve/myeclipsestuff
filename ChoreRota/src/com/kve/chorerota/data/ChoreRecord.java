package com.kve.chorerota.data;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChoreRecord {

	private int				choreId;
	private String 			choreName;
	private Date			baseDate;
	private Date			baseTime;
	private Float			freqNo;
	private FrequencyUnit	freqUnits;
	private boolean			toNotify;
	private boolean			reqDismissal;
	
	private boolean useTimeValue = false;
	
	static String[] timeBasedFreqs = {"Hour", "Minute", "Second"};
	
	static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
	static SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm", Locale.ENGLISH);

	public ChoreRecord()
{
		this.choreId 	= -1;
		this.choreName 	= "";
		this.baseDate 	= new Date();
		this.baseTime 	= new Date();
		this.freqNo 	= 1.0f;
		this.freqUnits 	= new FrequencyUnit("day");
		this.toNotify 	= true;
		this.reqDismissal = true;
	
}
	
	public ChoreRecord(int choreId, String choreName, Date baseDate,
			Date baseTime, Float freqNo, String freqUnits, boolean toNotify,
			boolean reqDismissal) {
		super();
		this.choreId 	= choreId;
		this.choreName 	= choreName;
		this.baseDate 	= baseDate;
		this.baseTime 	= baseTime;
		this.freqNo 	= freqNo;
		this.freqUnits 	= new FrequencyUnit(freqUnits);
		this.toNotify 	= toNotify;
		this.reqDismissal = reqDismissal;
	}

	
	public ChoreRecord(int choreId, String choreName, String baseDateString,
			String baseTimeString, Float freqNo, String freqUnits, boolean toNotify,
			boolean reqDismissal) {
		super();
		this.choreId 	= choreId;
		this.choreName 	= choreName;
		this.baseDate 	= getDateFromString(baseDateString);
		this.baseTime 	= getTimeFromString(baseTimeString);;
		this.freqNo 	= freqNo;
		this.freqUnits 	= new FrequencyUnit(freqUnits);
		this.toNotify 	= toNotify;
		this.reqDismissal = reqDismissal;
	}

	
	public Calendar getDateNextDue()
	{
		Date today = new Date();
		
		Calendar cal = Calendar.getInstance();
		if (this.getFreqUnits().getIsTimeUnit())
		{
		cal.setTime(this.getBaseTime());
		}
		else
		{
		cal.setTime(this.getBaseDate());
		}
		
		//	It totally makes sense to cast to long, as we're not dealing in fractions of seconds
		int delaySeconds = (int) (getFreqUnits().getNoOfSeconds() * getFreqNo());
		
		cal.add(Calendar.SECOND, delaySeconds);
		
		return cal;
	}

	public int getChoreId() {
		return choreId;
	}

	public String getChoreName() {
		return choreName;
	}

	public Date getBaseDate() {
		return baseDate;
	}

	public String getBaseDateString() {
		return getDateString(baseDate);
	}

	public Date getBaseTime() {
		return baseTime;
	}

	public String getBaseTimeString() {
		return getTimeString(baseTime);
	}

	public Float getFreqNo() {
		return freqNo;
	}

	public FrequencyUnit getFreqUnits() {
		return freqUnits;
	}

	public boolean isToNotify() {
		return toNotify;
	}

	public boolean isReqDismissal() {
		return reqDismissal;
	}

	public void setChoreId(int choreId) {
		this.choreId = choreId;
	}

	public void setChoreName(String choreName) {
		this.choreName = choreName;
	}

	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;
	}

	public void setBaseTime(Date baseTime) {
		this.baseTime = baseTime;
	}

	public void setFreqNo(Float freqNo) {
		this.freqNo = freqNo;
	}

	public void setFreqUnits(String freqUnits) {
		this.freqUnits = new FrequencyUnit(freqUnits);
	}

	public void setToNotify(boolean toNotify) {
		this.toNotify = toNotify;
	}

	public void setReqDismissal(boolean reqDismissal) {
		this.reqDismissal = reqDismissal;
	}
	

	////////////////////////////////////////////////
	//		Date and Time stuff
	////////////////////////////////////////////////


	public static String getDateString(Date date)
	{
		return dateFormatter.format(date);

	}

	public static String getTimeString(Date date)
	{
		return timeFormatter.format(date);	  
	}

	private Date getDateFromString(String dateString)
	{
		Date date;
		try {
			date = dateFormatter.parse(dateString);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}

	private Date getTimeFromString(String timeString)
	{
		Date time;
		try {
			time = timeFormatter.parse(timeString);
		} catch (ParseException e) {
			time = new Date();
		}
		return time;
	}
	
	

}
