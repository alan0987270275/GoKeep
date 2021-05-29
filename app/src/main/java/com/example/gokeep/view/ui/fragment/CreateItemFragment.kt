package com.example.gokeep.view.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.gokeep.R
import com.example.gokeep.databinding.FragmentCreateItemBinding
import com.example.gokeep.view.ui.components.AddPhotoBottomSheetDialog
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
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
class CreateItemFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    // param1 to determine show which kind of layout.
    private var param1: String? = null
    private val TAG = CreateItemFragment::class.java.name
    private val FINAL_CHOOSE_PHOTO = 1
    private val RC_CAMERA_PERM = 123
    private val RC_GALLERY_PERM = 124
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
        initTakePhotoLayout()
        initCalenderView()
    }

    private fun initTakePhotoLayout() = with(binding) {
        val addPhotoBottomSheetDialog = AddPhotoBottomSheetDialog.newInstance()
        addPhotoBottomSheetDialog.setListener(object : AddPhotoBottomSheetDialog.AddPhotoBottomSheetDialogListener{
            override fun cameraOnclick() {
                cameraTask()
                addPhotoBottomSheetDialog.dismiss()
            }

            override fun galleryOnclick() {
                galleryTask()
                addPhotoBottomSheetDialog.dismiss()
            }
        })

        addPictureLayout.setOnClickListener {
            addPhotoBottomSheetDialog.show(parentFragmentManager, AddPhotoBottomSheetDialog.TAG)
        }
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

    private fun hasCameraPermission(): Boolean {
        return if(context != null) {
            EasyPermissions.hasPermissions(requireContext(),
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }else{
            false
        }
    }

    private fun hasGalleryPermission(): Boolean {
        return if(context != null) {
            EasyPermissions.hasPermissions(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }else{
            false
        }
    }

    fun galleryTask() {
        if (hasGalleryPermission()) {
            // Have permission, do the thing!
            openAlbum()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_gallery),
                RC_GALLERY_PERM,
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, FINAL_CHOOSE_PHOTO)
    }

    fun cameraTask() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_camera),
                RC_CAMERA_PERM,
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode:Int,
                                            permissions:Array<String>,
                                            grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    @SuppressLint("StringFormatMatches")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            FINAL_CHOOSE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                    else{
                    }
                }

            }
            AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE -> {
                val yes = getString(R.string.yes)
                val no = getString(R.string.no)
                val str = getString(
                    R.string.returned_from_app_settings_to_activity,
                    if (hasCameraPermission()) yes else no
                )
                // Do something after user returned from app settings screen, like showing a Toast.
                Toast.makeText(requireContext(),str, Toast.LENGTH_LONG).show()
            }
        }

    }

    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        if (DocumentsContract.isDocumentUri(requireContext(), uri)){
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri?.authority){
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = imagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selsetion)
            }
            else if ("com.android.providers.downloads.documents" == uri?.authority){
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = imagePath(contentUri, null)
            }
        } else if ("content".equals(uri?.scheme, ignoreCase = true)){
            imagePath = imagePath(uri, null)
        } else if ("file".equals(uri?.scheme, ignoreCase = true)){
            imagePath = uri?.path
        }
        displayImage(imagePath)
    }

    private fun imagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = context?.contentResolver?.query(uri!!, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            setPhotoToShow(bitmap)
        }
        else {
            Toast.makeText(requireContext(), "Failed to get image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setPhotoToShow(bitmap: Bitmap) = with(binding) {
        photoImageView.setImageBitmap(bitmap)
        photoImageView.visibility = View.VISIBLE
        addPictureLayout.background = null
        cameraImageView.visibility = View.GONE
        cameraTextView.visibility = View.GONE
    }



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