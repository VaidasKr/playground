package lt.vaikri.playground.button

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.StyleableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import lt.vaikri.playground.R
import kotlin.math.ceil

class MyProgressButton(context: Context, attrs: AttributeSet) : MaterialButton(context, attrs) {
    private val iconSize: Int
    private var startIcon: Drawable? = null
    private var startIconOffset: Int = 0
    private var progressDrawableOffset: Int = 0
    private val spec = CircularProgressIndicatorSpec(context, null).apply {
        indicatorColors = intArrayOf(Color.WHITE)
    }

    private val progressDrawable: IndeterminateDrawable<CircularProgressIndicatorSpec>

    private var displayedProgressDrawable: IndeterminateDrawable<CircularProgressIndicatorSpec>? = null

    init {
        progressDrawable = IndeterminateDrawable.createCircularDrawable(context, spec)
        val density = resources.displayMetrics.density
        iconSize = (density * 24 + 0.5f).toInt()
        compoundDrawablePadding = (density * 8 + 0.5f).toInt()
        insetTop = 0
        insetBottom = 0
        cornerRadius = (density * 5 + 0.5f).toInt()
        stateListAnimator = null
        isAllCaps = false
        val enabledDisabledStates =
            arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_enabled))
        val accentColor = Color.WHITE

        val elementColors = ColorStateList(
            enabledDisabledStates,
            intArrayOf(accentColor, Color.DKGRAY)
        )

        setTextColor(elementColors)
        TextViewCompat.setCompoundDrawableTintList(this, elementColors)
        backgroundTintList = ColorStateList(
            enabledDisabledStates,
            intArrayOf(Color.BLUE, Color.LTGRAY)
        )
        rippleColor = ColorStateList.valueOf(ColorUtils.setAlphaComponent(accentColor, 0x40))
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MyProgressButton,
            0, 0
        )
        val showProgress: Boolean
        try {
            AppCompatResources.getDrawable(context, R.drawable.ic_forward)
            val icon = getDrawable(typedArray, R.styleable.MyProgressButton_startIcon)
            if (icon != null) {
                startIcon = DrawableCompat.wrap(icon).mutate()
            }
            showProgress = typedArray.getBoolean(R.styleable.MyProgressButton_showProgress, false)
        } finally {
            typedArray.recycle()
        }
        if (showProgress) {
            displayedProgressDrawable = progressDrawable
            progressDrawable.setVisible(true, true, true)
        }
        setIcons()
    }

    private fun getDrawable(typedArray: TypedArray, @StyleableRes index: Int): Drawable? {
        if (typedArray.hasValue(index)) {
            val resourceId = typedArray.getResourceId(index, 0)
            if (resourceId != 0) {
                return AppCompatResources.getDrawable(context, resourceId)
            }
        }
        return null
    }

    fun showProgress(show: Boolean) {
        if (displayedProgressDrawable != null == show) return
        if (show) {
            displayedProgressDrawable = progressDrawable
            progressDrawable.setVisible(true, true, true)
        } else {
            displayedProgressDrawable = null
            progressDrawable.setVisible(false, true, false)
        }
        setIcons()
        updateIconPositions()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        updateIconPositions()
    }

    private fun updateIconPositions() {
        val hasIcon = startIcon != null
        val hasProgress = displayedProgressDrawable != null
        if (!hasIcon && !hasProgress) {
            setCompoundDrawablesRelative(null, null, null, null)
            return
        }

        val availableSpaceForDrawables = measuredWidth - getTextLayoutWidth() - paddingEnd - paddingStart

        var iconOffset = startIconOffset
        var progressOffset = progressDrawableOffset

        if (hasIcon && hasProgress) {
            iconOffset = (availableSpaceForDrawables) / 2 - compoundDrawablePadding - iconSize
            progressOffset = -iconOffset
        } else if (hasIcon) {
            iconOffset = (availableSpaceForDrawables - compoundDrawablePadding - iconSize) / 2
        } else if (hasProgress) {
            progressOffset = (availableSpaceForDrawables - compoundDrawablePadding - iconSize) / -2
        }

        if (iconOffset != startIconOffset || progressOffset != progressDrawableOffset) {
            startIconOffset = iconOffset
            progressDrawableOffset = progressOffset

            setIcons()
        }
    }

    private fun setIcons() {
        startIcon?.setBounds(startIconOffset, 0, startIconOffset + iconSize, iconSize)
        progressDrawable.setBounds(progressDrawableOffset, 0, progressDrawableOffset + iconSize, iconSize)
        displayedProgressDrawable?.setVisible(true, true, true)
        setCompoundDrawablesRelative(startIcon, null, displayedProgressDrawable, null)
    }

    override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
        super.onTextChanged(charSequence, i, i1, i2)
        @Suppress("SENSELESS_COMPARISON") // called from super before initializing finished
        if (progressDrawable != null) {
            updateIconPositions()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (enabled) {
            spec.indicatorColors = intArrayOf(Color.WHITE)
        } else {
            spec.indicatorColors = intArrayOf(Color.DKGRAY)
        }
        displayedProgressDrawable?.setVisible(true, true, true)
    }

    private fun getTextLayoutWidth(): Int {
        var maxWidth = 0f
        val lineCount = lineCount
        for (line in 0 until lineCount) {
            maxWidth = maxWidth.coerceAtLeast(layout.getLineWidth(line))
        }
        return ceil(maxWidth).toInt()
    }
}
