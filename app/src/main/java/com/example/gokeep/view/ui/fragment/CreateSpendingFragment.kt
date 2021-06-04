package com.example.gokeep.view.ui.fragment

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.example.gokeep.data.localdb.DatabaseBuilder
import com.example.gokeep.data.localdb.DatabaseHelperImpl
import com.example.gokeep.data.localdb.entity.Spending
import com.example.gokeep.data.model.categoryDataList
import com.example.gokeep.data.model.getTagByPosition
import com.example.gokeep.databinding.FragmentCreateSpendingBinding
import com.example.gokeep.util.DateHelper
import com.example.gokeep.util.ViewModelFactory
import com.example.gokeep.view.adpter.CategoryAdapter
import com.example.gokeep.viewmodel.RoomDBViewModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateSpendingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateSpendingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = CreateSpendingFragment::javaClass.name

    private var _binding: FragmentCreateSpendingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RoomDBViewModel
    private lateinit var cateGoryAdapter: CategoryAdapter
    private var dateTimeStamp: Long = 0L

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
        _binding = FragmentCreateSpendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext().applicationContext))
            )
        ).get(RoomDBViewModel::class.java)
    }

    private fun initView() = with(binding) {
        cancelButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        initCalendarView()
        initCreateButton()
        initCategory()
    }

    private fun initCreateButton() = with(binding) {
        createSpendingButton.setOnClickListener {
            val tag = getTagByPosition(cateGoryAdapter.getSelected())
            val title = goalTitleEditText.text.toString()
            val cost = goalBudgetEditText.text.toString().toInt()
            val time = dateTimeStamp
            val spending = Spending(
                0,
                tag,
                title,
                cost,
                time
            )
            viewModel.insertSpending(spending)
            parentFragmentManager.popBackStack()
        }
    }

    private fun initCategory() = with(binding) {
        val horizontalLinearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            context,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        cateGoryAdapter = CategoryAdapter(categoryDataList)
        categoryRecyclerView.layoutManager = horizontalLinearLayoutManager
        categoryRecyclerView.adapter = cateGoryAdapter
    }

    private fun initCalendarView() = with(binding) {
        val calendar = Calendar.getInstance()
        var selectedCalendar = Calendar.getInstance()
        val onDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            selectedCalendar.set(year, monthOfYear, dayOfMonth)
            dateTimeStamp = selectedCalendar.timeInMillis
            dateButton.text = DateHelper.format("yyyy / MM / dd", selectedCalendar.time)
        }

        val datePickerDialog = context?.let {
            DatePickerDialog(it, onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        }
        dateButton.setOnClickListener {
            datePickerDialog?.show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateSpendingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            CreateSpendingFragment()
//            CreateSpendingFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}