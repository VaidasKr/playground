package lt.vaikri.playground.banner

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

// FIXME add text alpha animation
//  add top inset padding if needed
//  correct for text size
class BannerMessageView(context: Context, attrs: AttributeSet) :
    View(context, attrs), ValueAnimator.AnimatorUpdateListener {

    private val fullWidth: Int get() = resources.displayMetrics.widthPixels

    private var contentHeight = 0
    private var animator: ValueAnimator? = null
    private var hasStatusBarPadding: Boolean = false
    private var statusBarPaddingColor: Int = 0x33000000
    private val statusBarPaddingRect = Rect()
    private val padding = (2 * resources.displayMetrics.density).roundToInt()
    private val textPaint = TextPaint()
        .apply {
            textSize = resources.displayMetrics.scaledDensity * 20
            letterSpacing = 0.025f
            color = Color.WHITE
        }
    private var staticLayout = buildStaticTextLayout("")
    private val textHeightWightPadding = staticLayout.height + 2 * padding
    private val singleLineHeight: Int = textHeightWightPadding

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        if (insets != null) {
            val size = WindowInsetsCompat.toWindowInsetsCompat(insets)
                .getInsets(WindowInsetsCompat.Type.statusBars()).top
            statusBarPaddingRect.bottom = size
            if (hasStatusBarPadding) {
                requestLayout()
            }
        }
        return super.onApplyWindowInsets(insets)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            fullWidth,
            if (hasStatusBarPadding) statusBarPaddingRect.height() + contentHeight else contentHeight
        )
    }

    fun render(state: StatusBannerState) {
        animator?.cancel()
        val newMessage = state.renderMessage()
        staticLayout = buildStaticTextLayout(newMessage)
        contentHeight = if (newMessage.isBlank()) 0 else textHeightWightPadding
        requestLayout()
    }

    fun renderSmooth(state: StatusBannerState) {
        animator?.cancel()
        val newMessage = state.renderMessage()
        if (newMessage.isBlank()) {
            animateToHeight(0)
        } else {
            staticLayout = buildStaticTextLayout(newMessage)
            animateToHeight(textHeightWightPadding)
        }
    }

    fun setHasStatusBarPadding(hasPadding: Boolean) {
        hasStatusBarPadding = hasPadding
        requestLayout()
    }

    fun setStatusBarColor(color: Int) {
        statusBarPaddingColor = color
        invalidate()
    }

    private fun buildStaticTextLayout(text: String): StaticLayout =
        StaticLayout.Builder.obtain(text, 0, text.length, textPaint, fullWidth - 2 * padding)
            .setAlignment(Layout.Alignment.ALIGN_CENTER)
            .build()

    private fun animateToHeight(targetHeight: Int) {
        val currentHeight = contentHeight
        if (currentHeight == targetHeight) {
            invalidate()
            return
        }
        val dif = currentHeight - targetHeight
        val duration =
            (dif.absoluteValue.coerceAtMost(singleLineHeight) / singleLineHeight.toFloat() * FULL_DURATION).toLong()
        animateHeight(currentHeight, targetHeight, duration)
    }

    private fun animateHeight(from: Int, to: Int, duration: Long) {
        val animator = ValueAnimator.ofInt(from, to)
        animator.duration = duration
        animator.addUpdateListener(this)
        this.animator = animator
        animator.start()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        contentHeight = animation.animatedValue as Int
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.RED)
        canvas.save()
        val offset = contentHeight - staticLayout.height - padding.toFloat()
        canvas.translate(padding.toFloat(), offset)
        staticLayout.draw(canvas)
        canvas.restore()
    }

    fun attach(lifecycleOwner: LifecycleOwner) {
        val service = StatusBannerService()
        lifecycleOwner.lifecycleScope.launch {
            val statusBannerState = service.stateFlow.value
            render(statusBannerState)
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                var lastState = statusBannerState
                service.stateFlow.collect { state ->
                    if (state != lastState) {
                        renderSmooth(state)
                    }
                    lastState = state
                }
            }
        }
    }

    companion object {
        private const val FULL_DURATION = 1500L
    }
}

