package com.example.keepershomewordpopup

import java.util.*

class MonthlyInfo(private var numOfDay: Int, private var monthName: String) {
    private var weekList= Vector<WeeklyInfo>()
    //-----------------------------------------Gets------------------------------------------------------------------//
    private fun getNumOfDay():Int{
        return numOfDay
    }
    fun getWeekList():Vector<WeeklyInfo>
    {
        return weekList
    }
    fun getMonthName():String{
        return monthName
    }
    fun addDay(day:DailyInfo){
        if(weekList.isEmpty()||day.getDay()==1)
            weekList.addElement(WeeklyInfo())
        weekList.lastElement().add(day)
    }
    fun getmax():Int{
        var max=0
        for (week in weekList) {
            var this_avg = week.getAverageUseing()
            if (this_avg > max)
                max = this_avg
        }
        return max
    }
    fun getAverageBadWords():Int{
        var sum=0
        for(week in weekList){
            sum+=week.getAverageBadWords()
        }
        return sum/weekList.size
    }
    fun getHeightLessLowestUseing():Int
    {
        var max=0
        var min= Int.MAX_VALUE
        for (week in weekList){
            var this_avg=week.getAverageUseing()
            if (this_avg<min)
                min=this_avg
            if(this_avg>max)
                max=this_avg
        }
        return max-min
    }

}