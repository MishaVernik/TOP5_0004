package kqb.das.awkie.schedule

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kqb.das.awkie.R
import kotlinx.android.synthetic.main.click_count_item_view.view.*

class ClickCountItemView : FrameLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        inflate(context, R.layout.click_count_item_view, this)
    }

    fun setText(text: String) {
        view.text = text
    }
}