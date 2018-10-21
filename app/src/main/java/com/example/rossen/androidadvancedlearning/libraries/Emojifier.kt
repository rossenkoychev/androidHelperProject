package com.example.rossen.androidadvancedlearning.libraries

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.google.android.gms.vision.face.FaceDetector
import com.google.android.gms.vision.Frame
import android.widget.Toast
import com.example.rossen.androidadvancedlearning.R
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector.ALL_CLASSIFICATIONS
import timber.log.Timber


object Emojifier {

    private  const val TRESHOLD_VALUE = 0.5
    private const val EMOJI_SCALE_FACTOR = .9f
    private  var isLeftEyeOpen: Boolean = false
    private var isRightEyeOpen = false
    private var isSmiling = false

    fun detectFaces(context: Context, bitmap: Bitmap): Bitmap {
        val detector = FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                //  .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(ALL_CLASSIFICATIONS)
                .build()
        var resultBitmap: Bitmap = bitmap
        val frame = Frame.Builder().setBitmap(bitmap).build()

        val faces = detector.detect(frame)

        if (faces.size() == 0) Toast.makeText(context, R.string.no_faces_message, Toast.LENGTH_LONG).show()
        else {
            // Log the number of faces
            Timber.d("detectFaces: number of faces = " + faces.size())

            for (i in 0 until faces.size()) {
                val face = faces.valueAt(i)
                val emoji = whichEmoji(face)
                var emojiBitmap: Bitmap
                when (emoji) {
                    Emoji.LEFT_CLOSED_RIGHT_CLOSED_FROWNING -> emojiBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.closed_frown)
                    Emoji.LEFT_CLOSED_RIGHT_OPEN_FROWNING -> emojiBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.leftwinkfrown)
                    Emoji.LEFT_CLOSED_RIGHT_OPEN_SMILING -> emojiBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.leftwink)
                    Emoji.LEFT_CLOSED_RIGHT_CLOSED_SMILING -> emojiBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.closed_smile)
                    Emoji.LEFT_OPEN_RIGHT_CLOSED_FROWNING -> emojiBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.rightwinkfrown)
                    Emoji.LEFT_OPEN_RIGHT_CLOSED_SMILING -> emojiBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.rightwink)
                    Emoji.LEFT_OPEN_RIGHT_OPEN_FROWNING -> emojiBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.frown)
                    Emoji.LEFT_OPEN_RIGHT_OPEN_SMILING -> emojiBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.smile)
                }

                resultBitmap = addBitmapToFace(resultBitmap, emojiBitmap, face)
            }
        }
        detector.release()

        return resultBitmap
    }

    private fun addBitmapToFace(backgroundBitmap: Bitmap, emojiBitmap: Bitmap, face: Face): Bitmap {
        var emojiBitmap2 = emojiBitmap

        // Initialize the results bitmap to be a mutable copy of the original image
        val resultBitmap = Bitmap.createBitmap(backgroundBitmap.width,
                backgroundBitmap.height, backgroundBitmap.config)

        // Scale the emoji so it looks better on the face
        val scaleFactor = EMOJI_SCALE_FACTOR

        // Determine the size of the emoji to match the width of the face and preserve aspect ratio
        val newEmojiWidth = (face.width * scaleFactor).toInt()
        val newEmojiHeight = (emojiBitmap2.height * newEmojiWidth / emojiBitmap2.width * scaleFactor).toInt()


        // Scale the emoji
        emojiBitmap2 = Bitmap.createScaledBitmap(emojiBitmap2, newEmojiWidth, newEmojiHeight, false)

        // Determine the emoji position so it best lines up with the face
        val emojiPositionX = face.position.x + face.width / 2 - emojiBitmap2.width / 2
        val emojiPositionY = face.position.y + face.height / 2 - emojiBitmap2.height / 3

        // Create the canvas and draw the bitmaps to it
        val canvas = Canvas(resultBitmap)
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null)
        canvas.drawBitmap(emojiBitmap2, emojiPositionX, emojiPositionY, null)

        return resultBitmap
    }


    private fun whichEmoji(face: Face): Emoji {
        Timber.d("left eye open probability = " + face.isLeftEyeOpenProbability)
        Timber.d("right eye open probability = " + face.isRightEyeOpenProbability)
        Timber.d("smiling probability = " + face.isSmilingProbability)

        isLeftEyeOpen = face.isLeftEyeOpenProbability >= TRESHOLD_VALUE
        isRightEyeOpen = face.isRightEyeOpenProbability >= TRESHOLD_VALUE
        isSmiling = face.isSmilingProbability >= TRESHOLD_VALUE

        val emoji: Emoji
        if (isLeftEyeOpen) {
            if (isRightEyeOpen) {
                if (isSmiling) {
                    emoji = Emoji.LEFT_OPEN_RIGHT_OPEN_SMILING
                } else {
                    emoji = Emoji.LEFT_OPEN_RIGHT_OPEN_FROWNING
                }
            } else if (isSmiling) {
                emoji = Emoji.LEFT_OPEN_RIGHT_CLOSED_SMILING
            } else {
                emoji = Emoji.LEFT_OPEN_RIGHT_CLOSED_FROWNING
            }
        } else if (isRightEyeOpen) {
            if (isSmiling) {
                emoji = Emoji.LEFT_CLOSED_RIGHT_OPEN_SMILING
            }
             else {
                emoji = Emoji.LEFT_CLOSED_RIGHT_OPEN_FROWNING
            }
        } else {
            if (isSmiling) {
                emoji = Emoji.LEFT_CLOSED_RIGHT_CLOSED_SMILING
            } else {
                emoji = Emoji.LEFT_CLOSED_RIGHT_CLOSED_FROWNING
            }
        }
        return emoji
    }
}