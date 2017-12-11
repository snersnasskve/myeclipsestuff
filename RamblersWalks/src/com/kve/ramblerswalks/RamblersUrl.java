package com.kve.ramblerswalks;

import java.util.ArrayList;

//	Need to figre out these groups.
//	Need to 1. have the ability to add area codes
//			2. calculate distance from postal code
//			3. store group codes with some kind of real geography info

public class RamblersUrl {
	/*
	 * http://www.ramblers.co.uk/walksfinder/walks.rss?showMap=1&zoomLevel=3&advanced=1&area=yo25+9rh&
	 * from_date_day=1&from_date_month=4&to_date_day=30&to_date_month=4&
	 * grade[]=EA&grade[]=E&grade[]=L&grade[]=M&grade[]=S&grade[]=T&weekday=Sunday&distance=6-10&
	 */

	private String area;
	private String fromDay;
	private String toDay;
	private String fromMonth;
	private String toMonth;
	private String weekday ;
	private String distance;
	private String url;
	ArrayList<String> chosenWeekdays;

	/*
	private String urlStart = "http://www.ramblers.co.uk/walksfinder/walks.rss?showMap=1&zoomLevel=3&advanced=1&";
	private String urlGrades = "grade[]=EA&grade[]=E&grade[]=L&grade[]=M&grade[]=S&grade[]=T&";
	*/
	private String urlStart = "http://www.ramblers.org.uk/LBSData.ashx?type=walks&";
	//private String urlGrades = "grade[]=EA&grade[]=E&grade[]=L&grade[]=M&grade[]=S&grade[]=T&";

	
	//	http://www.ramblers.co.uk/walksfinder/walksfeed.php?groups=DE02,DE01&days=Saturday,Sunday&distance=4-10&limit=5&dow=1
	// codes: 
	
	public RamblersUrl(String area, String fromDay, String toDay, String fromMonth, String toMonth, String weekday, String distance )
	{
		super();
		this.area 		=	area;
		this.fromDay	=	fromDay;
		this.toDay		=	toDay;
		this.fromMonth	=	fromMonth;
		this.toMonth	=	toMonth;
		this.weekday	=	weekday;
		this.distance	=	distance;
	}

	public RamblersUrl(String area, ArrayList<String> chosenWeekdays, String distance) {
		super();
		this.area			=	area;
		this.distance		=	distance;
		this.chosenWeekdays	=	chosenWeekdays;
	}

/*	public String getUrl() {
		url = urlStart + getArea() + getFromDay() + getFromMonth() + 
										getToDay() + getToMonth() +  
										getWeekday() + getDistance() + "&dow=0";
		return url;
	}
	
*/
	public String getUrl() {
		url = urlStart + getArea() + getWeekdays() + getDistance() + "limit=10&";
		return url;
	}

	//	New find : ER = Yeast Yorkshire and Derwent = experiment with it
	//	Beverley = ER01
	//	Ryedale = ER04
	//	East Yorkshire and Derwent = ER06
	//	Pocklington = ER06
	//	Hull and Holderness?
	//	York = ER07
	//	Howden and Goole  = ER09
	//	Scarborough and dist = ER05
	//	Scunthorpe = LI07
	//	Grimsby & Louth = LI07

	
	private String getArea()
	{
		//String encodedArea = area.replaceAll(" ", "%20");
		//return "area="+encodedArea+"&";
		String groupsArea = "";
		if (area.startsWith("YO") || area.startsWith("HU"))
		{
			 groupsArea =  "groups=ER01,ER02,ER03,ER04,ER05,ER06,ER07,ER08,ER09&,LI04,LI07&";
			// groupsArea =  "groups=NN01,NN02,NN03,NN04,NN05,NN06,NN07,NN08,NN09,NN10,NN11&";
		}
		return groupsArea;
	}
	
	private String getFromDay()
	{
		return "from_date_day="+fromDay+"&";

	}

	private String getFromMonth()
	{
		return "from_date_month="+fromMonth+"&";
	}

	private String getToDay()
	{
		return "to_date_day="+toDay+"&";
	}

	private String getToMonth()
	{
		return "to_date_month="+toMonth+"&";
	}

	private String getWeekday()
	{
		if (weekday.equals("All"))
		{
			return "";
		}
		else
		{
			return "days="+weekday+"&";
		}
	}
	
	private String getWeekdays()
	{
		String weekdaysText = "";
		if (chosenWeekdays.size() > 0 && chosenWeekdays.size() < 7 & !chosenWeekdays.get(0).equals("All"))
		{
			weekdaysText = "days=";
			for (String weekdayInst : chosenWeekdays)
			{
				weekdaysText = weekdaysText + weekdayInst + ",";
			}
			weekdaysText = weekdaysText.substring(0, (weekdaysText.length() - 1));
			weekdaysText += "&";
		}
		return weekdaysText;
	}

	
	private String getDistance()
	{
		return "distance="+distance+"&";
	}
}
