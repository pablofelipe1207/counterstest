package com.cornershop.counterstest.presentation.ui.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.cornershop.counterstest.R

class IconButton : AppCompatButton {
    private var mIcon: Bitmap? = null
    private var mPaint: Paint? = null
    private var mSrcRect: Rect? = null
    private var mIconPadding = 0
    private var mIconSize = 0

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?) : super(context!!) {}

    override fun onDraw(canvas: Canvas) {
        val shift = (mIconSize + mIconPadding) / 2
        canvas.save()
        canvas.translate(shift.toFloat(), 0F)
        super.onDraw(canvas)
        if (mIcon != null) {
            val textWidth: Float = getPaint().measureText(getText() as String?)
            val left = (getWidth() / 2f - textWidth / 2f - mIconSize - mIconPadding)
            val top: Int = getHeight() / 2 - mIconSize / 2
            val destRect = Rect(left.toInt(), top, (left + mIconSize).toInt(), top + mIconSize)
            canvas.drawBitmap(mIcon!!, mSrcRect, destRect, mPaint)
        }
        canvas.restore()
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.IconButton)
        for (i in 0 until array.indexCount) {
            val attr = array.getIndex(i)
            when (attr) {
                R.styleable.IconButton_iconSrc -> mIcon = drawableToBitmap(array.getDrawable(attr))
                R.styleable.IconButton_iconPadding -> mIconPadding = array.getDimensionPixelSize(attr, 0)
                R.styleable.IconButton_iconSize -> mIconSize = array.getDimensionPixelSize(attr, 0)
                else -> {
                }
            }
        }
        array.recycle()
        if (mIcon != null) {
            mPaint = Paint()
            mSrcRect = Rect(0, 0, mIcon!!.width, mIcon!!.height)
        }
    }

    companion object {
        fun drawableToBitmap(drawable: Drawable?): Bitmap {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
            drawable.draw(canvas)
            return bitmap
        }
    }
}