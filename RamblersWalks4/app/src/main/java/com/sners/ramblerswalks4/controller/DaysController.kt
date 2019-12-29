package com.sners.ramblerswalks4.controller

class DaysController {

    //  The purpose of this companion is just to make it static. It is not related to the days fragment at all
    companion object {
        //  Apparently cannot use constant in this
        enum class DayName(val day: String) {
            MONDAY("Monday"),
            TUESDAY("Tuesday"),
            WEDNESDAY("Wednesday"),
            THURSDAY("Thursday"),
            FRIDAY("Friday"),
            SATURDAY("Saturday"),
            SUNDAY("Sunday")
        }

        fun daysDescription(daysArray : Array<Boolean>): String
        {
            val weekdays = daysArray[7]
            val weekend = daysArray[8]
            val everyday = daysArray[9]
            val weekdayNames = DayName.values()
            var desc = "None"
            if (everyday || ((weekdays && weekend) ))
            {
                desc = "Every Day"
            }
            else if (weekdays)
            {
                desc = "Weekdays"
            }
            else if (weekend)
            {
                desc = "Weekends"
            }
            else {
                val selectedDays = ArrayList<String>()
                for (dayNo in 0..6)
                {
                    if (daysArray[dayNo])
                    {
                        selectedDays.add(weekdayNames[dayNo].day)
                    }
                }

                if (selectedDays.count() > 0) {
                    desc = selectedDays.joinToString(separator = ", ")
                }
            }
            return desc
        }

        fun daysFromDescription(desc : String) : Array<Boolean> {
            var daysArray = Array<Boolean>(10){i -> false}
            val weekdayNames = DayName.values()

            for (num in 0..6)
            {
                if (weekdayNames[num].day in desc) {
                    daysArray[num] = true
                }
            }
            if ("Weekdays" in desc)
            {
                for (num in 0..4)
                {
                    daysArray[num] = true
                }
                daysArray[7] = true
            }
            if ("Weekend" in desc)
            {
                daysArray[5] = true
                daysArray[6] = true
                daysArray[8] = true
            }
            return daysArray
        }

    }
}