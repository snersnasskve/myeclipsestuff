package com.sners.snowforecast.view

import android.app.Activity
import android.graphics.*
import android.widget.ImageView
import android.widget.TextView
import com.sners.snowforecast.data.IntervalData
import com.sners.snowforecast.weather.*

/**
 * A class representing the Graph views
 */
open class WeatherPrecipChart : Activity() {
    /**
     * @property ivPcIntensity Intensity image graph view
     */
    var ivPcIntensity: ImageView? = null

    /**
     * @property ivPcProbability Probability image graph view
     */
    var ivPcProbability: ImageView? = null

    /**
     * @property tvPcIntensity Intensity description text view
     */
    var tvPcIntensity: TextView? = null

    /**
     * The values below are the cutoff points for different graph colours
     */
//    enum class precipIntensities(max: Float) {
//        None(0.001f),
//        VeryLight(0.9f),
//        Light(2.5f),
//        Moderate(6.25f),
//        Heavy(25.0f)
//    }
//    val precipNone = .001f
//    val precipVeryLight = 0.9f
//    val precipLight = 2.5f
//    val precipModerate = 6.25f
//    val precipHeavy = 25.0f
    /**
     * @property mGraphMargin The margin around the edge of the graph
     */
    var mGraphMargin = 5

    /**
     * @property yAxis The height of the y axis
     */
    var yAxis = 40

    /**
     * @property mAxesPaint Paint colour for axes
     */
    private var mAxesPaint: Paint? = null

    /**
     * @property mChartPaint Paint colour for chart bars
     */
    private var mChartPaint: Paint? = null

    /**
     * @property mDashPaint Paint colour for dashed line
     */
    private var mDashPaint: Paint? = null

    /**
     * @property precipPrefix Descriptor or precip intensity
     */
    protected var precipPrefix: String? = null

    /**
     * Draw Probability graph
     * @param intData Interval Data
     * @param numPointsToPlot Number of points to plot
     */
    protected fun drawProbabilityGraph(intData: ArrayList<IntervalData>, numPointsToPlot: Int) {
        var graphWidth = intData.size
        if (numPointsToPlot < graphWidth) {
            graphWidth = numPointsToPlot
        }
        val bmProb = Bitmap.createBitmap(
            graphWidth + 2 * mGraphMargin,
            yAxis + 2 * mGraphMargin, Bitmap.Config.ARGB_8888
        )
        val cProb = Canvas(bmProb)
        cProb.drawColor(Color.WHITE)
        //  Y axis
        cProb.drawLine(
            mGraphMargin.toFloat(),
            mGraphMargin.toFloat(),
            mGraphMargin.toFloat(),
            (yAxis + mGraphMargin).toFloat(),
            mAxesPaint!!
        )
        //  X axis
        cProb.drawLine(
            mGraphMargin.toFloat(), (yAxis + mGraphMargin).toFloat(), (graphWidth + mGraphMargin).toFloat(), (
                    yAxis + mGraphMargin).toFloat(), mAxesPaint!!
        )
        cProb.drawLine(
            mGraphMargin + graphWidth / 2.0f, mGraphMargin.toFloat(),
            mGraphMargin + graphWidth / 2.0f, (
                    yAxis + mGraphMargin).toFloat(), mDashPaint!!
        )
        for (i in 0 until graphWidth) {
            val probNum = intData[i].precipProbability * yAxis / 100
            cProb.drawLine(
                (mGraphMargin + i).toFloat(), mGraphMargin + yAxis - probNum, (
                        mGraphMargin + i).toFloat(), (yAxis + mGraphMargin).toFloat(), mChartPaint!!
            )
        }
        ivPcProbability!!.setImageBitmap(bmProb)
    }

    /**
     * Draw Precipitation graph
     * @param intData Interval Data
     * @param numPointsToPlot Number of points to plot
     */
    protected fun drawPrecipGraph(
        intData: ArrayList<IntervalData>,
        maxPrecip: Float, numPointsToPlot: Int
    ) {
        var graphWidth = intData.size
        if (numPointsToPlot < graphWidth) {
            graphWidth = numPointsToPlot
        }
        val bmIntens =
            Bitmap.createBitmap(graphWidth + 2 * mGraphMargin, yAxis + 2 * mGraphMargin, Bitmap.Config.ARGB_8888)
        val cIntens = Canvas(bmIntens)
        cIntens.drawColor(Color.WHITE)
        //  Y axis
        cIntens.drawLine(
            mGraphMargin.toFloat(),
            mGraphMargin.toFloat(),
            mGraphMargin.toFloat(),
            (yAxis + mGraphMargin).toFloat(),
            mAxesPaint!!
        )
        //  X axis
        cIntens.drawLine(
            mGraphMargin.toFloat(), (yAxis + mGraphMargin).toFloat(), (graphWidth + mGraphMargin).toFloat(), (
                    yAxis + mGraphMargin).toFloat(), mAxesPaint!!
        )

        //  50% line
        cIntens.drawLine(
            mGraphMargin + graphWidth / 2.0f, mGraphMargin.toFloat(),
            mGraphMargin + graphWidth / 2.0f, (yAxis + mGraphMargin).toFloat(), mDashPaint!!
        )
        for (i in 0 until graphWidth) {
            val precipNum = intData[i].precipIntensity * yAxis / maxPrecip
            cIntens.drawLine(
                (mGraphMargin + i).toFloat(), mGraphMargin + yAxis - precipNum, (
                        mGraphMargin + i).toFloat(), (yAxis + mGraphMargin).toFloat(), mChartPaint!!
            )
        }
        ivPcIntensity!!.setImageBitmap(bmIntens)
    }

    //	Format title and set colours
    /**
     * Set Title and Colours
     * @param maxPrecip Maximum precipitation
     * @return New maximimum precipitation
     */
    protected fun setTitleAndColours(maxPrecip: Float): Float {
        //precipIntensity: A numerical value representing the average expected intensity (in inches of liquid water per hour)
        //	of precipitation occurring at the given time conditional on probability (that is, assuming any precipitation occurs at all).
        //	A very rough guide is that a value of 0 in./hr. corresponds to no precipitation,
        //	0.002 in./hr. corresponds to very light precipitation,
        //	0.017 in./hr. corresponds to light precipitation,
        //	0.1 in./hr. corresponds to moderate precipitation,
        //	and 0.4 in./hr. corresponds to heavy precipitation.
        val precipLevel : PrecipLevels
//        var maxPrecip = maxPrecip
//        var descrip = ""
  //      var chartColour = Color.BLUE
        if (maxPrecip < PrecipNone.cutoff) {
            precipLevel = PrecipNone
//            descrip = precipPrefix + "None"
//            chartColour = -0x1000000 //	white
//            maxPrecip = 0.1f
        } else if (maxPrecip <= PrecipVeryLight.cutoff) {
            precipLevel = PrecipVeryLight
            //	.002 inches = .0508 mm
//            descrip = precipPrefix + "Very Light"
//            chartColour = -0x3f3f40 //	light grey
//            maxPrecip = maxPrecip * 5
        } else if (maxPrecip <= PrecipLight.cutoff) {
            precipLevel = PrecipLight

            //	0.017 inches = 0.4318 mm
//            descrip = precipPrefix + "Light"
//            chartColour = -0x663301 //	pale blue
//            maxPrecip = maxPrecip * 4
        } else if (maxPrecip <= PrecipModerate.cutoff) {
            precipLevel = PrecipModerate

            //	0.1 inches = 2.54 mm
//            descrip = precipPrefix + "Moderate"
//            chartColour = -0xff7f01 //	medium blue
//            maxPrecip = maxPrecip * 3
        } else if (maxPrecip <= PrecipHeavy.cutoff) {
            precipLevel = PrecipHeavy

            //	0.4 inches = 10.16 mm
//            descrip = precipPrefix + "Heavy"
//            chartColour = -0xffff9a //	dark blue
//            maxPrecip = maxPrecip * 2
        } else {
            precipLevel = PrecipFlood

//            descrip = "!!! EVACUATE !!!"
//            chartColour = -0x99ff9a //	dark purple
        }
        setGraphColours(precipLevel.chartColour)
        formatIntensityText(precipPrefix + precipLevel.name)
        return (maxPrecip * precipLevel.yAxisMultiplier)
    }

    /**
     * Set Graph colours
     * @param chartColour Colour to draw the bars
     */
    private fun setGraphColours(chartColour: Int) {
        mAxesPaint = Paint()
        mAxesPaint!!.color = Color.BLACK
        mChartPaint = Paint()
        mChartPaint!!.color = chartColour
        mDashPaint = Paint()
        mDashPaint!!.color = Color.LTGRAY
        mDashPaint!!.style = Paint.Style.STROKE
        mDashPaint!!.pathEffect = DashPathEffect(floatArrayOf(2f, 4f), 0f)
        mDashPaint!!.strokeWidth = 1f
    }

    /**
     * Format intensity
     * @param descrip Description text
     */
    private fun formatIntensityText(descrip: String) {

        //	Not much happening here right now,
        tvPcIntensity!!.text = descrip
    }
}