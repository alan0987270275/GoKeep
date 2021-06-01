package com.example.gokeep.view.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gokeep.R
import com.example.gokeep.data.localdb.DatabaseBuilder
import com.example.gokeep.data.localdb.DatabaseHelperImpl
import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.databinding.FragmentHomeBinding
import com.example.gokeep.databinding.HomeBodyLayoutBinding
import com.example.gokeep.databinding.HomeHeaderLayoutBinding
import com.example.gokeep.util.Status
import com.example.gokeep.util.ViewModelFactory
import com.example.gokeep.view.adpter.CategoryAdapter
import com.example.gokeep.view.adpter.GoalAdapter
import com.example.gokeep.view.ui.activity.MainActivity
import com.example.gokeep.view.ui.components.ExpandingFloatingActionButton
import com.example.gokeep.viewmodel.RoomDBViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = HomeFragment::class.java.name

    // Those properties is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var _homeHeaderLayoutBinding: HomeHeaderLayoutBinding? = null
    private val homeHeaderLayoutBinding get() = _homeHeaderLayoutBinding!!
    private var _homeBodyLayoutBinding: HomeBodyLayoutBinding? = null
    private val homeBodyLayoutBinding get() = _homeBodyLayoutBinding!!

    private lateinit var viewModel: RoomDBViewModel
    private lateinit var goalAdapter: GoalAdapter

    data class CategoryViewData(
        val imageId: Int,
        val title: String
    )

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _homeHeaderLayoutBinding = binding.homeHeaderLayout
        _homeBodyLayoutBinding = binding.homeBodyLayout
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _homeHeaderLayoutBinding = null
        _homeBodyLayoutBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    /**
     * Init all the view
     */
    private fun initView() {

        /**
         * Init homeHeaderLayout
         */
        initHomeHeaderLayout()
        initHomeBodyLayout()
        initFab()
        initViewModel()
        initObserver()
    }

    private fun initHomeHeaderLayout() = with(homeHeaderLayoutBinding) {
        /**
         * Init goalRecyclerView
         */
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        /**
         * Add fake data for testing
         */
        goalAdapter = GoalAdapter(arrayListOf())
        goalRecyclerView.layoutManager = linearLayoutManager
        goalRecyclerView.adapter = goalAdapter
    }

    private fun initHomeBodyLayout() = with(homeBodyLayoutBinding) {

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val cateGoryAdapter = CategoryAdapter(categoryDataList)
        categoryRecyclerView.layoutManager = linearLayoutManager
        categoryRecyclerView.adapter = cateGoryAdapter
    }

    private fun initFab() = with(binding) {

        expandingFloatingActionButton.setListener(object : ExpandingFloatingActionButton.ExpandingFloatingActionButtonListener{
            override fun firstButtonOnClick() {
                (context as MainActivity).showFragment(R.layout.fragment_create_item, "Goal")
            }

            override fun secondButtonOnClick() {
                (context as MainActivity).showFragment(R.layout.fragment_create_item, "Spend")
            }
        })

    }

    private fun initObserver() {
        viewModel.getGoals().observe(requireActivity(), Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { goals ->
                        Log.d(TAG,"ININNIN: "+ goals.size)
                        renderList(goals)
                    }
                }
                Status.ERROR -> {
                    println("ERROR: "+it.message)

                }
                Status.LOADING -> {

                }
            }
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext().applicationContext))
            )
        ).get(RoomDBViewModel::class.java)
    }

    private fun renderList(goals: List<Goal>) {
        goalAdapter.addAllItem(goals)
        goalAdapter.notifyDataSetChanged()
    }

    val categoryDataList = listOf(
        CategoryViewData(
            R.drawable.icon_all,
            ""
        ),
        CategoryViewData(
            R.drawable.ic_icon_income,
            "Income"
        ),
        CategoryViewData(
            R.drawable.ic_outlined_flag_black_24dp,
            "Goal"
        ),
        CategoryViewData(
            R.drawable.ic_card_giftcard_black_24dp,
            "Shopping"
        ),
        CategoryViewData(
            R.drawable.ic_commute_black_24dp,
            "Transport"
        ),
        CategoryViewData(
            R.drawable.ic_local_grocery_store_black_24dp,
            "Grocery"
        ),
        CategoryViewData(
            R.drawable.ic_restaurant_black_24dp,
            "Restaurant"
        ),
        CategoryViewData(
            R.drawable.ic_request_quote_black_24dp,
            "Billing"
        )
    )

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}