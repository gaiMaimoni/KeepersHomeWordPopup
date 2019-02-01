package com.example.keepershomewordpopup

class DailyInfo(private var day: Int, private var useingMinute: Int, private var badWords: Int) {
    //-----------------------------------------Gets------------------------------------------------------------------//
    fun getDay():Int{
        return day
    }
    fun getUseingMinute():Int{
        return useingMinute
    }
    fun getBadWords():Int{
        return badWords
    }
}