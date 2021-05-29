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
import androidx.annotation.RequiresApi
import androidx.core.util.Pair
import com.example.gokeep.databinding.FragmentCreateItemBinding
import com.example.gokeep.view.ui.activity.MainActivity
import com.example.gokeep.view.ui.components.AddPhotoBottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CeateItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateItemFragment : Fragment() {

    // param1 to determine show which kind of layout.
    private var param1: String? = null
    private val TAG = CreateItemFragment::class.java.name
//    private var param2: String? = null

    private var _binding: FragmentCreateItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {

        cancelButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        addPictureLayout.setOnClickListener {
            (activity as MainActivity).showAddPhotoBottomSheetDialog()
        }
        initCalenderView()

    }


    private fun initCalenderView() = with(binding) {
        val calendar = Calendar.getInstance()
        var selectedCalendar = Calendar.getInstance()
        val onDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            selectedCalendar.set(year, monthOfYear, dayOfMonth)

        }

        val datePickerDialog = context?.let {
                DatePickerDialog(it, onDateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH))
        }
        fromDateButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                datePickerDialog?.setOnDateSetListener(onDateSetListener(fromDateButton, selectedCalendar))
            }
            datePickerDialog?.show()
        }
        toDateButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                datePickerDialog?.setOnDateSetListener(onDateSetListener(toDateButton, selectedCalendar))
            }
            datePickerDialog?.show()
        }
    }

    private fun onDateSetListener(view: View, selectedCalendar: Calendar) = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        selectedCalendar.set(year, monthOfYear, dayOfMonth)
        (view as Button).text = format("yyyy / MM / dd", selectedCalendar.time)
    }

    private fun format(format: String, date: Date) = SimpleDateFormat(format, Locale.TAIWAN).format(date)

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 to determine show which kind of layout.
         * @return A new instance of fragment CeateItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            CreateItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}