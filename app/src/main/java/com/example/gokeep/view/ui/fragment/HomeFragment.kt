package com.example.gokeep.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gokeep.R
import com.example.gokeep.data.model.Goal
import com.example.gokeep.databinding.FragmentHomeBinding
import com.example.gokeep.databinding.HomeHeaderLayoutBinding
import com.example.gokeep.view.adpter.GoalAdapter
import com.example.gokeep.view.adpter.TutorialAdapter
import com.example.gokeep.view.ui.activity.TutorialActivity

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

    }

    private fun initHomeHeaderLayout() = with(homeHeaderLayoutBinding) {
        /**
         * Init goalRecyclerView
         */
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        /**
         * Add fake data for testing
         */
        val adapter = GoalAdapter(arrayListOf()).apply {
            addAllItem(fakeData)
        }
        goalRecyclerView.layoutManager = linearLayoutManager
        goalRecyclerView.adapter = adapter


    }

    private val fakeData =
        mutableListOf<Goal>(
             Goal(
                 "summer vacation to Thailand",
                 "https://scandasia.com/wp-content/uploads/2021/03/11reasonsthailand.jpg",
                 70,
                 1622346511L,
                 1622029711L
             ),
            Goal(
                "Buy a Guitar",
                "https://cdn.store-assets.com/s/180631/f/3891188.jpeg",
                100,
                1622346511L,
                1622029711L
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