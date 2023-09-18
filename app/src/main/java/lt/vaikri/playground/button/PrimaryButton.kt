package lt.vaikri.playground.button

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable

class PrimaryButton(context: Context, attrs: AttributeSet) : MaterialButton(context, attrs) {
    private val spec = CircularProgressIndicatorSpec(context, null)

    private val progressDrawable by lazy(LazyThreadSafetyMode.NONE) {
        IndeterminateDrawable.createCircularDrawable(context, spec)
    }

    private var showProgress = false
    private var cachedDisplayIcon: Drawable? = null
    private var cachedEnabled = isEnabled

    init {
        spec.indicatorColors =
            intArrayOf(iconTint.getColorForState(intArrayOf(-android.R.attr.state_enabled), Color.WHITE))
        cachedDisplayIcon = icon
    }

    fun showProgress(showProgress: Boolean) {
        if (showProgress == this.showProgress) return
        this.showProgress = showProgress
        if (showProgress) {
            super.setIcon(progressDrawable)
            super.setEnabled(false)
        } else {
            super.setIcon(cachedDisplayIcon)
            super.setEnabled(cachedEnabled)
        }
    }

    override fun setIcon(icon: Drawable?) {
        cachedDisplayIcon = icon
        if (showProgress) return
        super.setIcon(icon)
    }

    override fun setEnabled(enabled: Boolean) {
        if (!showProgress) {
            super.setEnabled(enabled)
        }
        cachedEnabled = enabled
    }
}
