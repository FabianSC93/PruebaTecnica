package com.example.examen.ui.gallery

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.examen.R
import com.example.examen.core.Utils
import com.example.examen.data.model.ImagesModel
import com.example.examen.databinding.GalleryFragmentBinding
import com.example.examen.ui.gallery.adapter.ImagesAdapter
import com.example.examen.ui.location.LocationFragment
import com.example.examen.ui.movies.MoviesViewModel
import com.example.examen.ui.movies.MoviesViewState
import com.example.examen.ui.movies.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GalleryFragment: Fragment() {
    private var imageList: MutableList<ImagesModel> = mutableListOf()
    private lateinit var mBinding: GalleryFragmentBinding
    private lateinit var mContext: Context
    private val adapter: ImagesAdapter = ImagesAdapter(imageList)

    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = GalleryFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initElements()
        initUiState()
    }

    private fun initElements() {
        mContext = mBinding.root.context
        mBinding.rvImages.layoutManager = GridLayoutManager(mBinding.root.context, 3)
        mBinding.rvImages.adapter = adapter

        mBinding.apply {
            btnGallery.setOnClickListener {
                checkGalleryPermission()
            }
            btnCamera.setOnClickListener {
                checkCameraPermission()
            }
            btnUpload.setOnClickListener {
                uploadImages()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initUiState(){
        viewModel.uiGalleryState.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { state ->
            when(state){
                is GalleryViewState.ImageReady -> {
                    imageList.remove(state.item)
                    adapter.notifyDataSetChanged()
                    if(imageList.isEmpty()) Toast.makeText(mContext, R.string.message_upload_ready, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun uploadImages() {
        if(imageList.isEmpty()) Toast.makeText(mContext, R.string.message_empty_list, Toast.LENGTH_SHORT).show()
        else viewModel.uploadImage(imageList)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {

            if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
                if (data!!.clipData != null) {
                    val count = data.clipData!!.itemCount
                    if (count>0) {
                        for (i in 0 until count) {
                            imageList.add(ImagesModel(data.clipData!!.getItemAt(i).uri))
                        }
                    }
                } else{
                    val imageUri = data.data
                    imageList.add(ImagesModel(imageUri))
                    adapter.notifyDataSetChanged()
                }
                adapter.notifyDataSetChanged()
            } else if (requestCode == CAMERA_REQUEST) {
                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                imageList.add(ImagesModel(Utils.getImageUri(photo, mContext)))
                adapter.notifyDataSetChanged()
            }else {
                Toast.makeText(mContext, "No has tomado una imagén", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(mContext, "Hubo un problema", Toast.LENGTH_LONG)
                .show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkGalleryPermission() {
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            selectPhotoGallery()

        } else {
            requestLocationPermissions(
                R.string.message_dialog_permissions_gallery,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                CODE_STORAGE)
        }
    }

    private fun checkCameraPermission() {
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            selectPhotoCamara()
        } else {
            requestLocationPermissions(
                R.string.message_dialog_permissions_camera,
                Manifest.permission.CAMERA,
                CODE_CAMERA
            )
        }
    }

    private fun requestLocationPermissions(message: Int, permission: String, code: Int) {
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)) {
            AlertDialog.Builder(mContext)
                .setTitle(R.string.title_dialog_permissions)
                .setMessage(message)
                .setPositiveButton(R.string.button_permissions) { _, _ ->
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(permission),
                        code
                    )
                }
                .create()
                .show()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission),
                code
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CODE_STORAGE -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectPhotoGallery()
            }else {
                Toast.makeText(mContext, R.string.gallery_denied, Toast.LENGTH_SHORT).show()
            }

            CODE_CAMERA -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectPhotoCamara()
            }else {
                Toast.makeText(mContext, R.string.camera_denied, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectPhotoGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST)
    }

    private fun selectPhotoCamara() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    companion object {
        const val CODE_STORAGE = 2
        const val CODE_CAMERA = 3
        const val CAMERA_REQUEST = 100
        const val GALLERY_REQUEST = 200
    }

}