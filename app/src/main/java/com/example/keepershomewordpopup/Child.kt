package com.example.keepershomewordpopup



class Child(private var name: String, private var age: Int, private var numOfDays: Int, monthName:String) {
    private var numOfWeeks=0
    private var monthlyUsage=MonthlyInfo(numOfDays,monthName)


    //-----------------------------------Add Drandom data of the child------------------------------------------------//
    fun addRandomData(){
        var day=DailyInfo(0,0,0)
        for( i in 1..numOfDays){
            if(monthlyUsage.getWeekList().isEmpty())//if is the first day
                day= DailyInfo(getRandom(1,7),getRandom(200,500),getRandom(20,90))
            else{
                var temp=day.getDay()
                if(temp==7)
                    temp=1
                else
                    temp += 1
                day= DailyInfo(temp,getRandom(200,500),getRandom(20,90))
            }

            monthlyUsage.addDay(day)//add to the correct week
        }
        numOfWeeks=monthlyUsage.getWeekList().size
    }
    //-----------------------------------------get random between to numbers------------------------------------------------------------------//
    private fun getRandom(s:Int, e:Int):Int {
        return (s..e).random()
    }
    //-----------------------------------------Gets------------------------------------------------------------------//
    fun getAverageBadWords():Int {
        return monthlyUsage.getAverageBadWords()
    }
    fun getNumOfWeeks():Int{
        return numOfWeeks
    }
    fun getName():String{
        return name
    }
    fun getAge():String{
        return ""+age
    }
    fun getMonthlyUsage():MonthlyInfo{
        return monthlyUsage
    }
    fun getAveFormatHM(i:Int):IntArray{
        val ave = monthlyUsage.getWeekList()[i].getAverageUseing()
        val a=IntArray(2)
        a[0]=ave%60//Minuts
        a[1]=ave/60//Hours
        return a
    }

}