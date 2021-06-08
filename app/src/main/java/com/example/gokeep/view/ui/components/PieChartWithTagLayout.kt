package com.example.gokeep.view.ui.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.gokeep.R
import com.example.gokeep.databinding.ComponentsPiechartWithTagLayoutBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class PieChartWithTagLayout: LinearLayout {
    private lateinit var binding: ComponentsPiechartWithTagLayoutBinding
    constructor(context: Context) : super(context) {
        init(context, null)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        binding = ComponentsPiechartWithTagLayoutBinding.inflate(
            LayoutInflater.from(context), this, true
        )
        iniView()
    }

    private fun iniView() {
        initPieChart()
    }

    private fun initPieChart() = with(binding) {
        /**
         *  Styling PieChart
         **/
        pieChart.holeRadius = 80F
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.centerText = "-$12,589"
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart.setCenterTextSize(22f);
        pieChart.setCenterTextColor(ContextCompat.getColor(context, R.color.bluePrimary))
    }

    /**
     * @param entries: the data for pieChart
     * @param listColors the corresponding colors for pieChart
     */
    fun setData(entries: ArrayList<PieEntry>, listColors: ArrayList<Int>) = with(binding){
        // Create DataSet and data for PieChart
        val set = PieDataSet(entries, "")
        set.colors =listColors
        val data = PieData(set)
        pieChart.data = data
        // Remove labels on PieChart
        pieChart.setDrawEntryLabels(false)
        pieChart.data?.setDrawValues(false)
        pieChart.invalidate()
    }

}