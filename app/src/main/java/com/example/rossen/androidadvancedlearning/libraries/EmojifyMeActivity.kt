package com.example.rossen.androidadvancedlearning.libraries

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.rossen.androidadvancedlearning.R
import kotlinx.android.synthetic.main.activity_main_libraries.*
import timber.log.Timber
import java.io.File
import java.io.IOException

class EmojifyMeActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_STORAGE_PERMISSION = 1

    private val FILE_PROVIDER_AUTHORITY = "com.example.android.fileprovider"

    private lateinit var mTempPhotoPath: String

    private lateinit var mResultsBitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_libraries)

        // Set up Timber
        Timber.plant(Timber.DebugTree())

        initListeners()
    }

    private fun initListeners() {
        save_button.setOnClickListener {
            // Delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath)

            // Save the image
            BitmapUtils.saveImage(this, mResultsBitmap)
        }

        share_button.setOnClickListener {
            BitmapUtils.deleteImageFile(this, mTempPhotoPath)

            // Save the image
            BitmapUtils.saveImage(this, mResultsBitmap)

            // Share the image
            BitmapUtils.shareImage(this, mTempPhotoPath)
        }

        emojify_button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // If you do not have permission, request it
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_STORAGE_PERMISSION)
            } else {
                // Launch the camera if the permission exists
                launchCamera()
            }
        }

        clear_button.setOnClickListener {
            image_view.setImageResource(0)
            emojify_button.visibility = View.VISIBLE
            title_text_view.visibility = View.VISIBLE
            share_button.visibility = View.GONE
            save_button.visibility = View.GONE
            clear_button.visibility = View.GONE

            // Delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        // Called when you request permission to read and write to external storage
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    launchCamera()
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Creates a temporary image file and captures a picture to store in it.
     */
    private fun launchCamera() {

        // Create the capture image intent
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the temporary File where the photo should go
            val photoFile: File
            try {
                photoFile = BitmapUtils.createTempImageFile(this)
                // Get the path of the temporary file
                mTempPhotoPath = photoFile.absolutePath

                // Get the content URI for the image file
                val photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile)

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (ex: IOException) {
                // Error occurred while creating the File
                ex.printStackTrace()
                return
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage()
        } else {

            // Otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath)
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private fun processAndSetImage() {

        // Toggle Visibility of the views
        emojify_button.visibility = View.GONE
        title_text_view.visibility = View.GONE
        save_button.visibility = View.VISIBLE
        share_button.visibility = View.VISIBLE
        clear_button.visibility = View.VISIBLE

        // Resample the saved image to fit the ImageView
        mResultsBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath)


        // Detect the faces and overlay the appropriate emoji
       mResultsBitmap= Emojifier.detectFaces(this,mResultsBitmap)
        //  mResultsBitmap = Emojifier.detectFacesandOverlayEmoji(this, mResultsBitmap)

        // Set the new bitmap to the ImageView
        image_view.setImageBitmap(mResultsBitmap)
    }
}