package com.example.keepershomewordpopup


import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import java.util.*
import android.graphics.*
import android.widget.TextView
import androidx.view.doOnPreDraw



class MainActivity : AppCompatActivity(), View.OnClickListener {
    //-----------------------------------Fixed variables--------------------------------------------------------------//
    val NAME:String="Gai"
    val AGE:Int=30
    val NUMOFDAYS:Int=33
    val MONTH:String="April"
    val MAXIMAGE=6
    //----------------------------------------------------------------------------------------------------------------//
    private lateinit var popup_button: Button
    private lateinit var main_title: TextView
    private lateinit var sub_title: TextView
    private lateinit var month_title: TextView
    private lateinit var summaryTv: TextView
    private lateinit var graphsView: LinearLayout
    private lateinit var bottom_line: View
    private lateinit var positions: Vector<FloatArray>
    private lateinit var imageXY:Vector<FloatArray>
    private var imageSize = IntArray(2)
    private lateinit var child: Child



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        popup_button = findViewById(R.id.pop_up_btn)
        popup_button.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        setContentView(R.layout.weekly_average)
        main_title = findViewById(R.id.main_title)
        sub_title = findViewById(R.id.sub_title)
        month_title = findViewById(R.id.month_tv)
        summaryTv = findViewById(R.id.bottom_text)
        graphsView = findViewById(R.id.graphs_view)
        bottom_line = findViewById(R.id.bottom_line)
        child = Child(NAME, AGE, NUMOFDAYS, MONTH)
        child.addRandomData()
        columnHeight()
        addLabelsOfWeeks(this)
        addAllClocksImages()
        addLines(this)

        addTitle(child.getName())
        addMonth(child.getMonthlyUsage().getMonthName())
        addSummary(" " + child.getAverageBadWords(), child.getAge())

    }
    //-----------------------------------Add the current month to screen----------------------------------------------//
    private fun addMonth(month: String) {
        month_title.text=month
    }
    //-----------------------------------Add the title with a name of the kid-----------------------------------------//
    private fun addTitle(name: String) {
        main_title.text =getString(R.string.main_title,name)
        sub_title.text = getString(R.string.sub_title,name)
    }
    //-----------------------------------Add the summary of the last month--------------------------------------------//
    private fun addSummary(average: String, age: String) {
        summaryTv.text = getString(R.string.footer_statistic,age,average)
    }
    //-----------------------------------Add weeks W1....Wn-----------------------------------------------------------//
    private fun addLabelsOfWeeks(context: Context) {
        val weeksBar: LinearLayout = findViewById(R.id.weeksBar)
        for (i in 1..child.getNumOfWeeks()) {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.weekly_label_layout, null)

            val weekTV: TextView = view.findViewById(R.id.week_tv)
            weekTV.id=i-1
            weekTV.text = getString(R.string.week_number,i)
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x/4
            view.x +=(i-1)*(width/child.getNumOfWeeks())+(MAXIMAGE-child.getNumOfWeeks())*width/4
            println("wfsdgdsgsd"+view.x)
            weeksBar.addView(view)
        }
    }
    //-----------------------------------Decide on the height of the columns------------------------------------------//
    private fun columnHeight() {
        positions = Vector()
        val highest = child.getMonthlyUsage().getmax()
        var i = 0
        for (week in child.getMonthlyUsage().getWeekList()) {
            val tempArray = FloatArray(2)
            tempArray[1] = 1F * highest - week.getAverageUseing()
            positions.add(tempArray)
            i += 1
        }
    }
    //----------------------------------------------------------------------------------------------------------------//
    private fun addAllClocksImages() {
        val graphsViewXY = IntArray(2)
        graphsView.getLocationInWindow(graphsViewXY)
        val bottom_line_xy = IntArray(2)
        imageXY= Vector()
        bottom_line.getLocationInWindow(bottom_line_xy)
        var i = 0
        for (week in child.getMonthlyUsage().getWeekList()) {
            addClockImage(i)
            i += 1
        }
    }
    //--------------------------------Add the clock image and the average time----------------------------------------//
    private fun addClockImage( i: Int) {

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.weekly_screen_time_layout, null)
        val lblScreenTime: TextView = view.findViewById(R.id.lblScreenTime)
        val hm = child.getAveFormatHM(i)
        lblScreenTime.text = getString(R.string.hours_minutes,hm[1],hm[0])
        val l1: LinearLayout = findViewById(R.id.images)
        view.doOnPreDraw {
            if (i == 0) {
                imageSize[0] = view.width
                imageSize[1] = view.height
            }
            val temp=FloatArray(2)
            temp[0]=view.x
            temp[1]=view.y
            imageXY.addElement(temp)

        }
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x/4
        view.x += i*(width/child.getNumOfWeeks())+(MAXIMAGE-child.getNumOfWeeks())*width/4
        println(view.x)
        view.y = positions[i][1]
        l1.addView(view)
    }
    //--------------------------------Add the lines between all clock image-------------------------------------------//
    private fun addLines(context: Context) {
        val l1: LinearLayout = findViewById(R.id.graphs_view)

        val canvas = MyCanvas(context, positions,imageXY, imageSize)
        l1.addView(canvas)
    }
    //-------------------------------Useing canvs to draw diagonal lines----------------------------------------------//
    class MyCanvas(context: Context, private var positions: Vector<FloatArray>,
                   private var imageXY: Vector<FloatArray>, private var imageSize: IntArray
    ) : View(context) {
        private var paint = Paint()
        private fun init() {
            paint.color = Color.BLACK
            paint.strokeWidth = 5F
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            init()
            for (i: Int in 0..positions.size - 2) {
                val extrax: Float = imageSize[0].toFloat()/2
                val extray: Float = imageSize[1].toFloat()*0.7F
                val startX = imageXY[i][0]+extrax
                val startY = imageXY[i][1]+extray
                val stopX = imageXY[i+1][0]+extrax
                val stopY = imageXY[i+1][1]+extray
                canvas.drawLine(startX, startY, stopX, stopY, paint)
            }
        }
    }
}



