package com.example.gokeep.view.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gokeep.R
import com.example.gokeep.data.model.SpendingStaticData
import com.example.gokeep.databinding.FragmentHistoryBinding
import com.example.gokeep.view.adpter.StaticAdapter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.time.format.TextStyle

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var staticAdapter: StaticAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initStaticRecyclerView()
        initPieChart()
    }

    private fun initStaticRecyclerView() = with(binding) {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val fakeData = fakeTestingStaticData()
        staticAdapter = StaticAdapter(fakeData, fakeData.maxBy{ it.spending }?.spending ?: 0)
        staticRecyclerView.layoutManager = linearLayoutManager
        staticRecyclerView.adapter = staticAdapter
    }

    private fun initPieChart() = with(binding) {
        val entries: ArrayList<PieEntry> = arrayListOf()
        val listColors = ArrayList<Int>()
        // Insert fake data for testing.
        entries.add(PieEntry(18.5f, "Green"))
        listColors.add(ContextCompat.getColor(requireContext(), R.color.greenSuccess))
        entries.add(PieEntry(26.7f, "Yellow"))
        listColors.add(ContextCompat.getColor(requireContext(), R.color.yellow))
        entries.add(PieEntry(24.0f, "Red"))
        listColors.add(ContextCompat.getColor(requireContext(), R.color.red))
        entries.add(PieEntry(30.8f, "Blue"))
        listColors.add(ContextCompat.getColor(requireContext(), R.color.bluePrimary))
        // Create DataSet and data for PieChart
        val set = PieDataSet(entries, "")
        set.colors =listColors
        val data = PieData(set)
        /**
         *  Set data for PieChart and Styling PieChart
         **/
        pieChart.data = data
        pieChart.holeRadius = 80F
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.centerText = "-$12,589"
        // Remove labels on PieChart
        pieChart.setDrawEntryLabels(false)
        pieChart.data.setDrawValues(false)
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart.setCenterTextSize(18f);
        pieChart.setCenterTextColor(ContextCompat.getColor(requireContext(), R.color.bluePrimary))
        pieChart.invalidate()
    }

    private fun fakeTestingStaticData(): ArrayList<SpendingStaticData> {
        val spendingStaticData: ArrayList<SpendingStaticData> = arrayListOf()
        val month = listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        val spending = listOf(
            10000, 12300, 14500, 9000, 18000, 15454,
            23847, 12345, 15655, 12311, 22234, 9001
        )
        for (i in 0 .. 11) {
            val data = SpendingStaticData(
                spending[i],
                month[i],
                false
            )
            spendingStaticData.add(data)
        }
        spendingStaticData[5].isSelected = true


        return spendingStaticData
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}