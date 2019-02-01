package com.example.keepershomewordpopup

import java.util.*

class WeeklyInfo{
    private var dayList= Vector<DailyInfo>()
    fun add(day:DailyInfo){
        dayList.add(day)
    }
    //-----------------------------------------Gets------------------------------------------------------------------//
    fun get(i:Int):DailyInfo {
        return dayList.elementAt(i)
    }
    fun getAverageUseing():Int
    {
        var sum=0
        for(dayily in dayList)
            sum+=dayily.getUseingMinute()
        return sum/dayList.size
    }
    fun getAverageBadWords():Int
    {
        var sum=0
        for(dayily in dayList)
            sum+=dayily.getBadWords()
        return sum/dayList.size
    }
}