package com.adielwes.facemeshdetector

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var faceImageView: ImageView

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val selectedImageUri = result.data?.data
                faceImageView.setImageURI(selectedImageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        faceImageView = view.findViewById(R.id.faceImageView)
        val choosePictureButton = view.findViewById<Button>(R.id.choosePictureButton)
        val detectMeshButton = view.findViewById<Button>(R.id.detectMeshButton)

        choosePictureButton.setOnClickListener { onChoosePictureButtonClicked() }
        detectMeshButton.setOnClickListener { onDetectMeshButtonClicked() }
    }

    private fun onChoosePictureButtonClicked() {
        openImagePicker()
    }

    private fun onDetectMeshButtonClicked() {
        showToast()
    }

    private fun showToast() {
        Toast.makeText(activity, "Detect face mesh", Toast.LENGTH_SHORT).show()
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }
}