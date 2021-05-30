package com.example.gokeep.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gokeep.R
import com.example.gokeep.data.localdb.DatabaseBuilder
import com.example.gokeep.data.localdb.DatabaseHelperImpl
import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.databinding.FragmentHomeBinding
import com.example.gokeep.databinding.HomeHeaderLayoutBinding
import com.example.gokeep.util.Status
import com.example.gokeep.util.ViewModelFactory
import com.example.gokeep.view.adpter.GoalAdapter
import com.example.gokeep.view.ui.activity.MainActivity
import com.example.gokeep.view.ui.components.ExpandingFloatingActionButton
import com.example.gokeep.viewmodel.RoomDBViewModel
import kotlinx.android.synthetic.main.activity_tutorial.*

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

    // Those properties is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var _homeHeaderLayoutBinding: HomeHeaderLayoutBinding? = null
    private val homeHeaderLayoutBinding get() = _homeHeaderLayoutBinding!!

    private lateinit var viewModel: RoomDBViewModel
    private lateinit var adapter: GoalAdapter

    // Animation for FAB
    val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_rotate_open_anim) }
    val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_rotate_close_anim) }
    val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_from_bottom_anim) }
    val toBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_to_bottom_anim) }
    var clicked = false

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
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        adapter = GoalAdapter(arrayListOf())
        goalRecyclerView.layoutManager = linearLayoutManager
        goalRecyclerView.adapter = adapter


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
        viewModel.getGoals().observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { goals ->
                        println("INININININ: "+goals.size)
                        renderList(goals)
                    }
                }
                Status.ERROR -> {

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
        adapter.addAllItem(goals)
        adapter.notifyDataSetChanged()
    }
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