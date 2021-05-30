package com.example.gokeep.view.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.gokeep.databinding.FragmentHomeBinding
import com.example.gokeep.databinding.FragmentOnBoardingBinding
import com.example.gokeep.view.ui.activity.MainActivity
import com.example.gokeep.view.ui.activity.OnBoardingActivity

private const val ARG_POSITION = "param"
private const val ARG_BACKGROUND_COLOR = "param1"
private const val ARG_RESOURCE = "param2"
private const val ARG_TITLE1 = "param3"
private const val ARG_TITLE2 = "param4"
private const val ARG_MESSAGE = "param5"

class OnboardingFragmennt : Fragment() {

    private var position: Int? = null
    private var backgroundColor: Int? = null
    private var imageId: Int? = null
    private var titleText1: String? = null
    private var titleText2: String? = null
    private var message: String? = null

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            backgroundColor = it.getInt(ARG_BACKGROUND_COLOR)
            imageId = it.getInt(ARG_RESOURCE)
            titleText1 = it.getString(ARG_TITLE1)
            titleText2 = it.getString(ARG_TITLE2)
            message = it.getString(ARG_MESSAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        backgroundColor?.let { container.setBackgroundColor(it) }
        Glide.with(imageView.context)
            .load(imageId)
            .into(imageView)
        titleTextView1.text = titleText1
        titleTextView2.text = titleText2
        messageTextView.text = message
        // Last Page need to show the button to let user continue the flow
        button.visibility = if(position == 2) View.VISIBLE else View.GONE
        button.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }
        skipTextView.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param: Int,
                        param1: Int,
                        param2: Int,
                        param3: String,
                        param4: String,
                        param5: String) =
            OnboardingFragmennt().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, param)
                    putInt(ARG_BACKGROUND_COLOR, param1)
                    putInt(ARG_RESOURCE, param2)
                    putString(ARG_TITLE1, param3)
                    putString(ARG_TITLE2, param4)
                    putString(ARG_MESSAGE, param5)
                }
            }
    }

}