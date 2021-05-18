package com.sners.snowforecast.data

/**
 * A Helper class with useful conversions and other functions in general use for weather
 */
class WeatherHelper {

    /**
     * Format probability as a percentage
     * @param prob Probability as float
     * @return formatted percentage
     */
    fun probabilityToPercent(prob: Float): String {
        val perc = if (prob <= 1) prob * 100.0 else prob.toDouble()
        return String.format("%.1f %s", perc, WeatherConstants.PERCENT)
    }

    /**
     * Format Time
     * @param totalMinutes Time in minutes
     * @return Time in a readable format, HH:MM but prefixed by num days if applicable
     */
    fun formatTime(totalMinutes: Long): String {
        var hours = totalMinutes / 60
        val mins = totalMinutes % 60
        return if (hours > 24) {
            val days = hours / 24
            hours = hours % 24
            String.format("%d days, %d:%02d", days, hours, mins)
        } else {
            String.format("%d:%02d", hours, mins)
        }
    }


}