package com.j2merunner.engine.midp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

/**
 * Bridge for javax.microedition.lcdui.Image
 */
class Image private constructor(private val bitmap: Bitmap?) {

    companion object {
        /**
         * Create an image from a file path
         */
        fun createImage(path: String): Image {
            val bitmap = BitmapFactory.decodeFile(path)
            return Image(bitmap)
        }

        /**
         * Create an image from an input stream
         */
        fun createImage(stream: InputStream): Image {
            val bitmap = BitmapFactory.decodeStream(stream)
            return Image(bitmap)
        }

        /**
         * Create an image from byte array
         */
        fun createImage(imageData: ByteArray, imageOffset: Int, imageLength: Int): Image {
            val bitmap = BitmapFactory.decodeByteArray(imageData, imageOffset, imageLength)
            return Image(bitmap)
        }

        /**
         * Create a mutable image
         */
        fun createImage(width: Int, height: Int): Image {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            return Image(bitmap)
        }

        /**
         * Create an immutable image from a mutable image
         */
        fun createImage(source: Image): Image {
            val copy = source.bitmap?.copy(Bitmap.Config.ARGB_8888, false)
            return Image(copy)
        }

        /**
         * Create an image from a region of another image
         */
        fun createImage(image: Image, x: Int, y: Int, width: Int, height: Int, transform: Int): Image {
            val source = image.bitmap ?: return Image(null)
            val region = Bitmap.createBitmap(source, x, y, width, height)
            return Image(region)
        }
    }

    fun getBitmap(): Bitmap? = bitmap

    fun getWidth(): Int = bitmap?.width ?: 0

    fun getHeight(): Int = bitmap?.height ?: 0

    fun isMutable(): Boolean = bitmap?.isMutable ?: false

    fun getGraphics(): Graphics? {
        if (bitmap == null || !bitmap.isMutable) return null
        val canvas = android.graphics.Canvas(bitmap)
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
        }
        return Graphics(canvas, paint)
    }
}
