package com.example.gokeep.view.ui.components

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gokeep.R
import com.example.gokeep.data.model.StaticMonthlyTagData
import com.example.gokeep.databinding.ComponentsPiechartWithTagLayoutBinding
import com.example.gokeep.util.ColorHelper
import com.example.gokeep.util.TextFormatHelper
import com.example.gokeep.view.adpter.StaticTagAdapter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class PieChartWithTagLayout: LinearLayout {
    private val TAG = PieChartWithTagLayout::javaClass.name
    private lateinit var binding: ComponentsPiechartWithTagLayoutBinding
    private lateinit var adapter: StaticTagAdapter
    private var staticMonthlyTagDataList: ArrayList<StaticMonthlyTagData> = arrayListOf()

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
        initAdapter()
    }

    private fun initPieChart() = with(binding) {
        /**
         *  Styling PieChart
         **/
        pieChart.holeRadius = 80F
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart.setCenterTextSize(22f);
        pieChart.setCenterTextColor(ContextCompat.getColor(context, R.color.bluePrimary))
    }

    private fun initAdapter() = with(binding){
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = StaticTagAdapter(arrayListOf())
        adapter.setOnItemClickListener(object : StaticTagAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                pieChart.highlightValue(position.toFloat(), 0, false)
            }
        })
        tagRecyclerView.layoutManager = linearLayoutManager
        tagRecyclerView.adapter = adapter
    }

    private fun renderAdapter(list: ArrayList<StaticMonthlyTagData>) {
        adapter.addAll(list)
        adapter.notifyDataSetChanged()
    }

    private fun generateDataSet(list: ArrayList<StaticMonthlyTagData>): PieDataSet {
        val entries: ArrayList<PieEntry> = arrayListOf()
        val listColors = ArrayList<Int>()
        val defaultColor = ContextCompat.getColor(context, R.color.while_gray)
        for (index in list.indices) {
            val data = list[index]
            entries.add(PieEntry(data.sumSpending.toFloat(), data.tag))
            listColors.add(ColorHelper.getStaticColorMap(context, data.tag) ?: defaultColor)
        }
        val set = PieDataSet(entries, "")
        set.colors =listColors

        return set
    }

    private fun generateTagSpannable(tag: String, sum: String): SpannableString {
        val finalString = tag + "\n" + sum
        val index = finalString.indexOf("\n")
        val sb = SpannableString(finalString)
        sb.setSpan(RelativeSizeSpan(0.75F), 0 , index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        sb.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(context, R.color.gray)),
            0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb
    }

    /**
     * @param list: the data for pieChart
     */
    fun setData(list: ArrayList<StaticMonthlyTagData>) = with(binding){
        staticMonthlyTagDataList = list
        // Create DataSet and data for PieChart
        val set = generateDataSet(list)
        val data = PieData(set)
        pieChart.data = data

        // Remove labels on PieChart
        pieChart.setDrawEntryLabels(false)
        pieChart.data?.setDrawValues(false)
        pieChart.invalidate()

        // Set PieChart center text
        val sumCost = TextFormatHelper.moneyFormat(list.sumBy { it.sumSpending })
        pieChart.centerText = generateTagSpannable("Total", sumCost)

        // Add data to StaticTagAdapter
        renderAdapter(list)
    }

}