package com.sners.snowforecast.view;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.sners.snowforecast.data.IntervalData;

public class WeatherPrecipChart extends Activity {

    ImageView ivPcIntensity;
    ImageView ivPcProbability;
    TextView tvPcIntensity;

    final float precipNone = .001f;
    final float precipVeryLight = 0.7f;
    final float precipLight = 2.3f;
    final float precipModerate = 6.25f;
    final float precipHeavy = 25.0f;

    int mGraphMargin = 5;
    int yAxis = 40;
    private Paint mAxesPaint;
    private Paint mChartPaint;
    private Paint mDashPaint;

    protected String precipPrefix;


    protected void drawProbabilityGraph(ArrayList<IntervalData> intData, Integer numPointsToPlot) {
        int graphWidth = intData.size();
        if (numPointsToPlot < graphWidth) {
            graphWidth = numPointsToPlot;
        }
        Bitmap bmProb = Bitmap.createBitmap(graphWidth + (2 * mGraphMargin),
				yAxis + (2 * mGraphMargin), Bitmap.Config.ARGB_8888);
        Canvas cProb = new Canvas(bmProb);
        cProb.drawColor(Color.WHITE);
        //  Y axis
        cProb.drawLine(mGraphMargin, mGraphMargin, mGraphMargin, yAxis + mGraphMargin, mAxesPaint);
        //  X axis
        cProb.drawLine(mGraphMargin, yAxis + mGraphMargin, graphWidth + mGraphMargin,
				yAxis + mGraphMargin, mAxesPaint);
        cProb.drawLine(mGraphMargin + (graphWidth / 2.0f), mGraphMargin ,
                mGraphMargin + (graphWidth / 2.0f),
                yAxis + mGraphMargin, mDashPaint);

		for (int i = 0; i < graphWidth; i++) {
            float probNum = (intData.get(i).getPrecipProbability() * yAxis) / 100;
            cProb.drawLine(mGraphMargin + i, (mGraphMargin + yAxis - probNum),
                    mGraphMargin + i, yAxis + mGraphMargin, mChartPaint);
        }

        ivPcProbability.setImageBitmap(bmProb);
    }


    protected void drawPrecipGraph(ArrayList<IntervalData> intData,
                                   Float maxPrecip, Integer numPointsToPlot) {
        int graphWidth = intData.size();
        if (numPointsToPlot < graphWidth) {
            graphWidth = numPointsToPlot;
        }
        Bitmap bmIntens = Bitmap.createBitmap(graphWidth + (2 * mGraphMargin), yAxis + (2 * mGraphMargin), Bitmap.Config.ARGB_8888);
        Canvas cIntens = new Canvas(bmIntens);
        cIntens.drawColor(Color.WHITE);
        //  Y axis
        cIntens.drawLine(mGraphMargin, mGraphMargin, mGraphMargin, yAxis + mGraphMargin, mAxesPaint);
        //  X axis
        cIntens.drawLine(mGraphMargin, yAxis + mGraphMargin, graphWidth + mGraphMargin,
				yAxis + mGraphMargin, mAxesPaint);

        //  50% line
        cIntens.drawLine(mGraphMargin + (graphWidth / 2.0f), (mGraphMargin ),
                mGraphMargin + (graphWidth / 2.0f), yAxis + mGraphMargin, mDashPaint);
		for (int i = 0; i < graphWidth; i++) {
            float precipNum = (intData.get(i).getPrecipIntensity() * yAxis / maxPrecip);
            cIntens.drawLine(mGraphMargin + i, (mGraphMargin + yAxis - precipNum),
                    mGraphMargin + i, yAxis + mGraphMargin, mChartPaint);
        }
        ivPcIntensity.setImageBitmap(bmIntens);

    }


    //	Format title and set colours
    protected Float setTitleAndColours(Float maxPrecip) {
        //precipIntensity: A numerical value representing the average expected intensity (in inches of liquid water per hour)
        //	of precipitation occurring at the given time conditional on probability (that is, assuming any precipitation occurs at all).
        //	A very rough guide is that a value of 0 in./hr. corresponds to no precipitation,
        //	0.002 in./hr. corresponds to very light precipitation,
        //	0.017 in./hr. corresponds to light precipitation,
        //	0.1 in./hr. corresponds to moderate precipitation,
        //	and 0.4 in./hr. corresponds to heavy precipitation.

        String descrip = "";
        int chartColour = Color.BLUE;

        if (maxPrecip < precipNone) {
            descrip = precipPrefix + "None";
            chartColour = 0xFF000000;        //	white
            maxPrecip = 0.1f;
        } else if (maxPrecip <= precipVeryLight) {
            //	.002 inches = .0508 mm
            descrip = precipPrefix + "Very Light";
            chartColour = 0xFFC0C0C0;        //	light grey
            maxPrecip = maxPrecip * 5;
        } else if (maxPrecip <= precipLight) {
            //	0.017 inches = 0.4318 mm
            descrip = precipPrefix + "Light";
            chartColour = 0xFF99CCFF;        //	pale blue
            maxPrecip = maxPrecip * 4;
        } else if (maxPrecip <= precipModerate) {
            //	0.1 inches = 2.54 mm
            descrip = precipPrefix + "Moderate";
            chartColour = 0xFF0080FF;        //	medium blue
            maxPrecip = maxPrecip * 3;
        } else if (maxPrecip <= precipHeavy) {
            //	0.4 inches = 10.16 mm
            descrip = precipPrefix + "Heavy";
            chartColour = 0xFF000066;        //	dark blue
            maxPrecip = maxPrecip * 2;
        } else {
            descrip = "!!! EVACUATE !!!";
            chartColour = 0xFF660066;        //	dark purple
        }

        setGraphColours(chartColour);
        formatIntensityText(descrip, chartColour);

        return maxPrecip;
    }

    private void setGraphColours(int chartColour) {
        mAxesPaint = new Paint();
        mAxesPaint.setColor(Color.BLACK);
        mChartPaint = new Paint();
        mChartPaint.setColor(chartColour);
        mDashPaint = new Paint();
        mDashPaint.setColor(Color.LTGRAY);
        mDashPaint.setStyle(Paint.Style.STROKE);
        mDashPaint.setPathEffect(new DashPathEffect(new float[]{2, 4}, 0));
        mDashPaint.setStrokeWidth(1);
    }


    private void formatIntensityText(String descrip, int chartColour) {

        //	Not much happening here right now,
        tvPcIntensity.setText(descrip);
    }


}
