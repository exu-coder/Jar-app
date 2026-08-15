package com.j2merunner.engine.midp

import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface

/**
 * Bridge for javax.microedition.lcdui.Graphics
 * Wraps Android Canvas with J2ME-compatible API
 */
class Graphics(private val androidCanvas: AndroidCanvas, private val paint: Paint) {

    companion object {
        const val HCENTER = 1
        const val VCENTER = 2
        const val LEFT = 4
        const val RIGHT = 8
        const val TOP = 16
        const val BOTTOM = 32
        const val BASELINE = 64
    }

    private var clipX = 0
    private var clipY = 0
    private var clipWidth = androidCanvas.width
    private var clipHeight = androidCanvas.height
    private var translateX = 0
    private var translateY = 0

    private val font = Paint().apply {
        typeface = Typeface.DEFAULT
        textSize = 12f
    }

    /**
     * Set the current color
     */
    fun setColor(rgb: Int) {
        paint.color = 0xFF000000.toInt() or (rgb and 0xFFFFFF)
    }

    /**
     * Set color using RGB components
     */
    fun setColor(red: Int, green: Int, blue: Int) {
        paint.color = android.graphics.Color.rgb(red, green, blue)
    }

    /**
     * Set grayscale color
     */
    fun setGrayScale(value: Int) {
        val gray = value.coerceIn(0, 255)
        paint.color = android.graphics.Color.rgb(gray, gray, gray)
    }

    /**
     * Get the current red component
     */
    fun getRedComponent(): Int = android.graphics.Color.red(paint.color)

    /**
     * Get the current green component
     */
    fun getGreenComponent(): Int = android.graphics.Color.green(paint.color)

    /**
     * Get the current blue component
     */
    fun getBlueComponent(): Int = android.graphics.Color.blue(paint.color)

    /**
     * Get the current grayscale value
     */
    fun getGrayScale(): Int {
        val color = paint.color
        val r = android.graphics.Color.red(color)
        val g = android.graphics.Color.green(color)
        val b = android.graphics.Color.blue(color)
        return (r + g + b) / 3
    }

    /**
     * Get current color
     */
    fun getColor(): Int = paint.color and 0xFFFFFF

    /**
     * Set stroke style
     */
    fun setStrokeStyle(style: Int) {
        // SOLID = 0, DOTTED = 1
        // Android doesn't support dotted strokes easily, keep solid
    }

    /**
     * Draw a line
     */
    fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int) {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        androidCanvas.drawLine(
            (x1 + translateX).toFloat(),
            (y1 + translateY).toFloat(),
            (x2 + translateX).toFloat(),
            (y2 + translateY).toFloat(),
            paint
        )
    }

    /**
     * Draw a rectangle
     */
    fun drawRect(x: Int, y: Int, width: Int, height: Int) {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        androidCanvas.drawRect(
            (x + translateX).toFloat(),
            (y + translateY).toFloat(),
            (x + width + translateX).toFloat(),
            (y + height + translateY).toFloat(),
            paint
        )
    }

    /**
     * Fill a rectangle
     */
    fun fillRect(x: Int, y: Int, width: Int, height: Int) {
        paint.style = Paint.Style.FILL
        androidCanvas.drawRect(
            (x + translateX).toFloat(),
            (y + translateY).toFloat(),
            (x + width + translateX).toFloat(),
            (y + height + translateY).toFloat(),
            paint
        )
    }

    /**
     * Draw a rounded rectangle
     */
    fun drawRoundRect(x: Int, y: Int, width: Int, height: Int, arcWidth: Int, arcHeight: Int) {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        androidCanvas.drawRoundRect(
            (x + translateX).toFloat(),
            (y + translateY).toFloat(),
            (x + width + translateX).toFloat(),
            (y + height + translateY).toFloat(),
            arcWidth.toFloat(),
            arcHeight.toFloat(),
            paint
        )
    }

    /**
     * Fill a rounded rectangle
     */
    fun fillRoundRect(x: Int, y: Int, width: Int, height: Int, arcWidth: Int, arcHeight: Int) {
        paint.style = Paint.Style.FILL
        androidCanvas.drawRoundRect(
            (x + translateX).toFloat(),
            (y + translateY).toFloat(),
            (x + width + translateX).toFloat(),
            (y + height + translateY).toFloat(),
            arcWidth.toFloat(),
            arcHeight.toFloat(),
            paint
        )
    }

    /**
     * Draw an arc
     */
    fun drawArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        androidCanvas.drawArc(
            (x + translateX).toFloat(),
            (y + translateY).toFloat(),
            (x + width + translateX).toFloat(),
            (y + height + translateY).toFloat(),
            startAngle.toFloat(),
            arcAngle.toFloat(),
            false,
            paint
        )
    }

    /**
     * Fill an arc
     */
    fun fillArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        paint.style = Paint.Style.FILL
        androidCanvas.drawArc(
            (x + translateX).toFloat(),
            (y + translateY).toFloat(),
            (x + width + translateX).toFloat(),
            (y + height + translateY).toFloat(),
            startAngle.toFloat(),
            arcAngle.toFloat(),
            false,
            paint
        )
    }

    /**
     * Draw a string
     */
    fun drawString(str: String, x: Int, y: Int, anchor: Int) {
        val fm = paint.fontMetrics
        val textWidth = paint.measureText(str)
        val textHeight = fm.descent - fm.ascent

        var drawX = (x + translateX).toFloat()
        var drawY = (y + translateY).toFloat()

        if (anchor and HCENTER != 0) drawX -= textWidth / 2
        if (anchor and RIGHT != 0) drawX -= textWidth
        if (anchor and VCENTER != 0) drawY += textHeight / 2 - fm.descent
        if (anchor and TOP != 0) drawY -= fm.ascent
        if (anchor and BASELINE == 0 && anchor and BOTTOM != 0) drawY -= fm.descent

        paint.style = Paint.Style.FILL
        androidCanvas.drawText(str, drawX, drawY, paint)
    }

    /**
     * Draw a substring
     */
    fun drawSubstring(str: String, offset: Int, len: Int, x: Int, y: Int, anchor: Int) {
        drawString(str.substring(offset, offset + len), x, y, anchor)
    }

    /**
     * Draw a character
     */
    fun drawChar(character: Char, x: Int, y: Int, anchor: Int) {
        drawString(character.toString(), x, y, anchor)
    }

    /**
     * Draw characters
     */
    fun drawChars(data: CharArray, offset: Int, length: Int, x: Int, y: Int, anchor: Int) {
        drawString(String(data, offset, length), x, y, anchor)
    }

    /**
     * Draw an image
     */
    fun drawImage(img: Image, x: Int, y: Int, anchor: Int) {
        val bitmap = img.getBitmap() ?: return

        var drawX = x + translateX
        var drawY = y + translateY

        if (anchor and HCENTER != 0) drawX -= img.getWidth() / 2
        if (anchor and RIGHT != 0) drawX -= img.getWidth()
        if (anchor and VCENTER != 0) drawY -= img.getHeight() / 2
        if (anchor and BOTTOM != 0) drawY -= img.getHeight()

        androidCanvas.drawBitmap(bitmap, drawX.toFloat(), drawY.toFloat(), paint)
    }

    /**
     * Draw a region of an image
     */
    fun drawRegion(
        src: Image,
        x_src: Int, y_src: Int,
        width: Int, height: Int,
        transform: Int,
        x_dest: Int, y_dest: Int,
        anchor: Int
    ) {
        // TODO: Implement transform support
        val bitmap = src.getBitmap() ?: return
        val srcRect = Rect(x_src, y_src, x_src + width, y_src + height)
        val dstRect = Rect(x_dest, y_dest, x_dest + width, y_dest + height)
        androidCanvas.drawBitmap(bitmap, srcRect, dstRect, paint)
    }

    /**
     * Draw RGB data
     */
    fun drawRGB(rgbData: IntArray, offset: Int, scanlength: Int, x: Int, y: Int, width: Int, height: Int, processAlpha: Boolean) {
        val bitmap = Bitmap.createBitmap(rgbData, offset, scanlength, width, height, Bitmap.Config.ARGB_8888)
        androidCanvas.drawBitmap(bitmap, (x + translateX).toFloat(), (y + translateY).toFloat(), paint)
    }

    /**
     * Set clip region
     */
    fun setClip(x: Int, y: Int, width: Int, height: Int) {
        clipX = x + translateX
        clipY = y + translateY
        clipWidth = width
        clipHeight = height
        androidCanvas.clipRect(clipX, clipY, clipX + clipWidth, clipY + clipHeight)
    }

    /**
     * Get clip X
     */
    fun getClipX(): Int = clipX - translateX

    /**
     * Get clip Y
     */
    fun getClipY(): Int = clipY - translateY

    /**
     * Get clip width
     */
    fun getClipWidth(): Int = clipWidth

    /**
     * Get clip height
     */
    fun getClipHeight(): Int = clipHeight

    /**
     * Clip a rectangle
     */
    fun clipRect(x: Int, y: Int, width: Int, height: Int) {
        androidCanvas.clipRect(
            x + translateX,
            y + translateY,
            x + width + translateX,
            y + height + translateY
        )
    }

    /**
     * Translate coordinate system
     */
    fun translate(x: Int, y: Int) {
        translateX += x
        translateY += y
        androidCanvas.translate(x.toFloat(), y.toFloat())
    }

    /**
     * Get translate X
     */
    fun getTranslateX(): Int = translateX

    /**
     * Get translate Y
     */
    fun getTranslateY(): Int = translateY

    /**
     * Get canvas width
     */
    fun getWidth(): Int = androidCanvas.width

    /**
     * Get canvas height
     */
    fun getHeight(): Int = androidCanvas.height
}
