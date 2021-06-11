package com.example.gokeep.view.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gokeep.R
import com.example.gokeep.data.localdb.DatabaseBuilder
import com.example.gokeep.data.localdb.DatabaseHelperImpl
import com.example.gokeep.data.model.SpendingGroupByTag
import com.example.gokeep.data.model.StaticMonthlySumData
import com.example.gokeep.data.model.StaticMonthlySumDataFromDB
import com.example.gokeep.data.model.StaticMonthlyTagData
import com.example.gokeep.databinding.FragmentHistoryBinding
import com.example.gokeep.util.ColorHelper
import com.example.gokeep.util.DateHelper
import com.example.gokeep.util.Status
import com.example.gokeep.util.ViewModelFactory
import com.example.gokeep.view.adpter.SpendingAdapter
import com.example.gokeep.view.adpter.StaticAdapter
import com.example.gokeep.viewmodel.StaticDataViewModel
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.*

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
    private val TAG = HistoryFragment::javaClass.name
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: StaticDataViewModel
    private lateinit var staticAdapter: StaticAdapter
    private lateinit var historyAdapter: SpendingAdapter

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
        initViewModel()
        initView()
        initObserver()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext().applicationContext))
            )
        ).get(StaticDataViewModel::class.java)
    }

    private fun initObserver() {
        viewModel.getStaticMonthlySumDataFromDB().observe(requireActivity(), Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list -> renderStaticDataList(list) }

                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Log.e(TAG,"VIEWMODEL ERROR: ${it.message}")
                }
            }
        })

        viewModel.getStaticMonthlyTagData().observe( requireActivity(), Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list -> initPieChartWithTagLayout(list) }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Log.e(TAG,"VIEWMODEL ERROR: ${it.message}")
                }
            }
        })
    }

    private fun initView() {
        initStaticRecyclerView()
        initPieChartWithTagLayout(arrayListOf())
        initHistoryRecyclerView()
    }

    private fun initStaticRecyclerView() = with(binding) {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        staticAdapter = StaticAdapter(arrayListOf(), 0)
        staticRecyclerView.layoutManager = linearLayoutManager
        staticRecyclerView.adapter = staticAdapter
    }

    private fun renderStaticDataList(list: ArrayList<StaticMonthlySumDataFromDB>) {
        // convert StaticMonthlySumDataFromDB List to Map
        val map = list.associateBy ( {it._monthTitle}, {it} )
        val itemList = arrayListOf<StaticMonthlySumData>()
        for (i in 0 .. 11) {
            var data = StaticMonthlySumData(
                0,
                DateHelper.monthMap[i],
                false
            )
            map[i]?.apply {
                data = StaticMonthlySumData(
                    this.sumSpending,
                    this.monthTitle,
                    false
                )
            }
            itemList.add(data)
        }
        staticAdapter.addAllItem(itemList)
        staticAdapter.notifyDataSetChanged()
    }

    private fun initPieChartWithTagLayout(list: ArrayList<StaticMonthlyTagData>) = with(binding) {
        pieChartWithTagLayout.setData(list)
    }

    private fun initHistoryRecyclerView() = with(binding) {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        historyAdapter = SpendingAdapter(fakeTestingHistoryData)
        historyRecyclerView.layoutManager = linearLayoutManager
        historyRecyclerView.adapter = historyAdapter
    }

    private val fakeTestingHistoryData =
        arrayListOf(
            SpendingGroupByTag(
                "Transport",
                "Bus, Taxi",
                1000,
                1622640656931
            ),
            SpendingGroupByTag(
                "Grocery",
                "Beef, Beer, Candy",
                23569,
                1622640656931
            ),
            SpendingGroupByTag(
                "Shopping",
                "Clothes, Shoes",
                5200,
                1622640656931
            ),
            SpendingGroupByTag(
                "Billing",
                "Utility, Car",
                15000,
                1622640656931
            )
        )

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