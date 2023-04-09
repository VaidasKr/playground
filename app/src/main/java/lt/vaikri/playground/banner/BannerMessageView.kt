package lt.vaikri.playground.banner

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
import lt.vaikri.playground.R
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class BannerMessageView(context: Context, attrs: AttributeSet) :
    View(context, attrs), ValueAnimator.AnimatorUpdateListener {

    private val fullWidth: Int get() = resources.displayMetrics.widthPixels

    private var messageBoxHeight = 0
    private var animator: ValueAnimator? = null
    private var alphaOffset = 0F
    private var alphaDif = 0F
    private var hasStatusBarPadding: Boolean = false
    private val statusBarPaddingPaint = Paint().apply {
        isAntiAlias = true
        color = context.getColor(R.color.purple_700)
        style = Paint.Style.FILL
    }
    private val statusBarPaddingRect = Rect(0, 0, fullWidth, 0)
    private val padding = (2 * resources.displayMetrics.density).roundToInt()
    private val textPaint = TextPaint()
        .apply {
            isAntiAlias = true
            textSize = resources.displayMetrics.scaledDensity * 20
            letterSpacing = 0.025f
            color = 0x00FFFFFF
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
            if (hasStatusBarPadding) statusBarPaddingRect.height() + messageBoxHeight else messageBoxHeight
        )
    }

    fun render(state: StatusBannerState) {
        animator?.cancel()
        val newMessage = state.renderMessage()
        staticLayout = buildStaticTextLayout(newMessage)
        if (newMessage.isBlank()) {
            textPaint.alpha = 0
            messageBoxHeight = 0
        } else {
            textPaint.alpha = 255
            messageBoxHeight = textHeightWightPadding
        }
        requestLayout()
    }

    fun renderSmooth(state: StatusBannerState) {
        animator?.cancel()
        val newMessage = state.renderMessage()
        if (newMessage.isBlank()) {
            animateHeightAndAlpha(0, 0)
        } else {
            staticLayout = buildStaticTextLayout(newMessage)
            animateHeightAndAlpha(textHeightWightPadding, 255)
        }
    }

    fun setHasStatusBarPadding(hasPadding: Boolean) {
        hasStatusBarPadding = hasPadding
        requestLayout()
    }

    fun setStatusBarColor(color: Int) {
        statusBarPaddingPaint.color = color
        invalidate()
    }

    private fun buildStaticTextLayout(text: String): StaticLayout =
        StaticLayout.Builder.obtain(text, 0, text.length, textPaint, fullWidth - 2 * padding)
            .setAlignment(Layout.Alignment.ALIGN_CENTER)
            .build()

    private fun animateHeightAndAlpha(targetHeight: Int, targetAlpha: Int) {
        val currentHeight = messageBoxHeight
        if (currentHeight == targetHeight) {
            textPaint.alpha = targetAlpha
            invalidate()
            return
        }
        alphaOffset = textPaint.alpha.toFloat()
        alphaDif = targetAlpha - alphaOffset
        val dif = currentHeight - targetHeight
        val duration =
            (dif.absoluteValue / singleLineHeight.toFloat() * FULL_DURATION).toLong().coerceAtMost(FULL_DURATION)
        animateHeightWightAlpha(currentHeight, targetHeight, duration)
    }

    private fun animateHeightWightAlpha(from: Int, to: Int, duration: Long) {
        val animator = ValueAnimator.ofInt(from, to)
        animator.duration = duration
        animator.addUpdateListener(this)
        this.animator = animator
        animator.start()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        messageBoxHeight = animation.animatedValue as Int
        textPaint.alpha = (alphaOffset + alphaDif * animation.animatedFraction).roundToInt()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.RED)
        var height = messageBoxHeight
        if (hasStatusBarPadding) {
            height += statusBarPaddingRect.height()
            canvas.drawRect(statusBarPaddingRect, statusBarPaddingPaint)
        }
        canvas.save()
        val offset = height - staticLayout.height - padding.toFloat()
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

