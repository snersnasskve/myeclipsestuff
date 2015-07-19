package com.kve.xmascardlist;

public class FilterPrefs {

	private String region;
	private String lastSent;
	private String xmasReceived;
	private String xmasSent;
	private String favourites;
	
	public FilterPrefs()
	{
		this.region = "All";
		this.lastSent = "All";
		this.xmasReceived = "";
		this.xmasSent = "All";
		this.favourites = "All";
		
	}
	public FilterPrefs(String region, String lastSent, String xmasReceived, String xmasSent, String favourites)
	{
		this.region = region;
		this.lastSent = lastSent;
		this.xmasReceived = xmasReceived;
		this.xmasSent = xmasSent;
		this.favourites = favourites;
		
	}
	
	public void UpdateFilterPrefs(String region, String lastSent, String xmasReceived, String xmasSent, String favourites)
	{
		this.region = region;
		this.lastSent = lastSent;
		this.xmasReceived = xmasReceived;
		this.xmasSent = xmasSent;
		this.favourites = favourites;
		
	}

	public String getRegion() {
		return region;
	}

	public String getLastSent() {
		return lastSent;
	}

	public String getXmasReceived() {
		return xmasReceived;
	}

	public String getXmasSent() {
		return xmasSent;
	}

	public String getFavourites() {
		return favourites;
	}
	
	public int getFavouritesForDb()
	{
		int favouriteValue = -1;
		if (favourites.contentEquals("Faves only"))
		{
			favouriteValue = 1;
		}
		return favouriteValue;
	}

	/*
	public void setRegion(String region) {
		this.region = region;
	}

	public void setLastSent(String lastSent) {
		this.lastSent = lastSent;
	}

	public void setXmasReceived(String xmasReceived) {
		this.xmasReceived = xmasReceived;
	}

	public void setXmasSent(String xmasSent) {
		this.xmasSent = xmasSent;
	}

	public void setFavourites(String favourites) {
		this.favourites = favourites;
	}
	*/
	

}
