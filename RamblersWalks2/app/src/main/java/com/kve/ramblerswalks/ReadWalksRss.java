package com.kve.ramblerswalks;

//	This no longer works, the RSS feed has died
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import android.util.Log;

public class ReadWalksRss  {

	String TAG = "ReadWalksRss";

public void readRssXml(ArrayList<String> urlList)
{
		XmlPullParserFactory factory;
		for (String url : urlList)
		{
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			Log.d(TAG, "url = " + url);
			parser.setInput(new InputStreamReader(getUrlData(url)));


			int eventType = parser.getEventType();
			eventType = getNextElement(parser, "item");
			do {
				eventType = readWalkInfo(parser, "item");
			} while (eventType != XmlPullParser.END_DOCUMENT);

		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}

	public InputStream getUrlData(String url) throws URISyntaxException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet method = new HttpGet(new URI(url));
		HttpResponse res = client.execute(method);
		return res.getEntity().getContent();
	}
	
	public final int getNextElement(XmlPullParser parser, String firstElementName) throws XmlPullParserException, IOException
	{
		int type;
		while ((type=parser.next()) != XmlPullParser.END_DOCUMENT )
		{
			if(type == XmlPullParser.START_TAG)
			{
				String elementName = parser.getName();
				if (null != elementName && elementName.equals(firstElementName))
				{
					//throw new XmlPullParserException("Unexpected Start Tag Found" + parser.getName() + ", expected "+ firstElementName);
					//	ready to roll
					break;
				}
			}
		}
		return type;
	}
	
	public final int readWalkInfo(XmlPullParser parser, String parentTag) throws XmlPullParserException, IOException
	{
		// cycle through till the end of walks - add walk as you go
		int type;
		WalkInfo walkInfo = new WalkInfo();
		String parsingField = "";
		boolean parsing = true;
		while ((type=parser.next()) != XmlPullParser.END_DOCUMENT && parsing)
		{
			String elementName = parser.getName();
			switch (type) {
			case XmlPullParser.START_TAG:
				if (null != elementName && elementName.equals(parentTag))
				{
					// take no action
				}
				else if (null != elementName)
				{
					parsingField = elementName;
				}
				break;
			case XmlPullParser.END_TAG:
				if (null != elementName && elementName.equals(parentTag))
				{
					if (null != walkInfo.getTitle())
					{
						RamblersMain.walks.add(walkInfo);
					}
					parsing = false;
				}
				else if (null != elementName)
				{
					parsingField = "";
				}
				break;
			case XmlPullParser.TEXT:
				//walkInfo.setValueForField(parser.getText(), parsingField);
				break;

			default:
				break;
			}
	
		}
		return type;
	}
		

}
