package com.kve.xmascardlist_v3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by PC on 27/11/2017.
 */

public class FilterPrefs {

    private String region;
    private String lastSent;
    private String xmasReceived;
    private String xmasSent;
    private String favourites;

    public enum XmasSentType {
        SENT, NOT_SENT, ALL
    }

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

    public List<String> getCountriesForDb()
    {
        List<String> countryList = new ArrayList<String>();
        countryList.add(this.region);

        //	If the region has an array list then add all those countries
        //	If the region is all then add all countries
        if ( this.region.equals("Africa"))
        {
            countryList.addAll(Arrays.asList(XmasCardMain.africa));
        }

        if (this.region.equals("Down Under"))
        {
            countryList.addAll(Arrays.asList(XmasCardMain.downUnder));
        }

        return countryList;
    }

    public XmasSentType getXmasSentForDb()
    {
        XmasSentType sent = XmasSentType.ALL;
        if (this.xmasSent.equals("Sent"))
        {
            sent = XmasSentType.SENT;
        }
        else if (this.xmasSent.equals("Not Sent"))
        {
            sent = XmasSentType.NOT_SENT;
        }
        return sent;
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
