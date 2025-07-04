package com.jeric.bitteldigitalsignage.ui.components

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ScrollingTextview(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppCompatTextView(context, attrs) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        isSingleLine = true
        setSingleLine()
        ellipsize = TextUtils.TruncateAt.MARQUEE
        maxLines = 1
        marqueeRepeatLimit = -1
    }
    override fun isSelected(): Boolean {
        return true
    }
}