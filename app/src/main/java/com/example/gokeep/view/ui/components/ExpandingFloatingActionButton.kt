package com.example.gokeep.view.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gokeep.R
import com.example.gokeep.databinding.ComponentsExpandingFloatingActionButtonBinding
import com.example.gokeep.view.ui.activity.MainActivity

class ExpandingFloatingActionButton: ConstraintLayout {

    private lateinit var binding: ComponentsExpandingFloatingActionButtonBinding
    // Animation for FAB
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_to_bottom_anim) }
    private var clicked = false

    private var jumpToLayout1 = 0
    private var jumpToLayout2 = 0
    private var jumpToParam1:String? = null
    private var jumpToParam2 :String? = null

    private var listener: ExpandingFloatingActionButtonListener? = null

    interface ExpandingFloatingActionButtonListener {
        fun firstButtonOnClick()
        fun secondButtonOnClick()
    }

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
        binding = ComponentsExpandingFloatingActionButtonBinding.inflate(
            LayoutInflater.from(context), this, true
        )

        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) = with(binding) {
        if (attrs != null) {
            val attributes = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ExpandingFloatingActionButton,
                0, 0
            )
            setGoalTextView.text = attributes.getString(R.styleable.ExpandingFloatingActionButton_text1)
            setSpendingTextView.text = attributes.getString(R.styleable.ExpandingFloatingActionButton_text2)
            setGoalFab.setImageResource(
                attributes.getResourceId(R.styleable.ExpandingFloatingActionButton_image1, R.drawable.ic_add_a_photo_black_24dp)
            )
            setSpendingFab.setImageResource(
                attributes.getResourceId(R.styleable.ExpandingFloatingActionButton_image2, R.drawable.ic_baseline_attach_money_24)
            )
        }
        fabBackground.setOnClickListener {
            fabOnClick()
        }

        addActionFab.setOnClickListener {
            fabOnClick()
        }
        setGoalFab.setOnClickListener {
            fabOnClick()
            listener?.firstButtonOnClick()
        }
        setSpendingFab.setOnClickListener {
            fabOnClick()
            listener?.secondButtonOnClick()
        }
    }

    private fun fabOnClick() {
        setFabVisibility()
        setFabAnimation()
        clicked = !clicked
    }

    private fun setFabAnimation() = with(binding) {
        if (!clicked) {
            goalFabLayout.startAnimation(fromBottom)
            spendingFabLayout.startAnimation(fromBottom)
            addActionFab.startAnimation(rotateOpen)
        } else {
            goalFabLayout.startAnimation(toBottom)
            spendingFabLayout.startAnimation(toBottom)
            addActionFab.startAnimation(rotateClose)
        }
    }

    private fun setFabVisibility() = with(binding) {
        if (!clicked) {
            goalFabLayout.visibility = View.VISIBLE
            spendingFabLayout.visibility = View.VISIBLE
            setGoalFab.isEnabled = true
            setSpendingFab.isEnabled = true
            fabBackground.isEnabled = true
            fabBackground.visibility = View.VISIBLE
        } else {
            goalFabLayout.visibility = View.INVISIBLE
            spendingFabLayout.visibility = View.INVISIBLE
            setGoalFab.isEnabled = false
            setSpendingFab.isEnabled = false
            fabBackground.isEnabled = false
            fabBackground.visibility = View.INVISIBLE
        }
    }

    open fun setListener(listener: ExpandingFloatingActionButtonListener) {
        this.listener = listener
    }


}