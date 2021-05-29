package com.example.gokeep.view.ui.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gokeep.databinding.BottomSheetDialogAddPhotoBinding
import com.example.gokeep.view.ui.fragment.CreateItemFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddPhotoBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDialogAddPhotoBinding? = null
    private val binding get() = _binding!!
    private var listener: AddPhotoBottomSheetDialog.AddPhotoBottomSheetDialogListener? = null

    interface AddPhotoBottomSheetDialogListener {
        fun cameraOnclick()
        fun galleryOnclick()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetDialogAddPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        cameraLayout.setOnClickListener {
            listener?.cameraOnclick()
        }
        galleryLayout.setOnClickListener {
            listener?.galleryOnclick()
        }
    }

    open fun setListener(listener: AddPhotoBottomSheetDialogListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddPhotoBottomSheetDialog"
        @JvmStatic
        fun newInstance() =
            AddPhotoBottomSheetDialog()
    }
}