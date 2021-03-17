package com.sendbird.progressview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class ProgressView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    private fun drawProgressBackground() {

    }

    private fun drawProgress() {

    }
}