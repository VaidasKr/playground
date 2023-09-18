package lt.vaikri.playground

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.String
import kotlin.math.absoluteValue

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("lt.vaikri.playground", appContext.packageName)
    }

    @Test
    fun colorOnPrimaryButton() {
        val background: Int = Color.parseColor("#9BD3F1")
        val text = Color.parseColor("#FFFFFF")
        val target = Color.parseColor("#E0F2FE")

        var alpha = 0xFF
        var minDif = Int.MAX_VALUE
        var minDifAlpha = alpha
        var color = 0
        while (alpha-- > 0) {
            val textColor = ColorUtils.setAlphaComponent(text, alpha)
            val blended = ColorUtils.compositeColors(textColor, background)
            val dif = (blended - target).absoluteValue
            if (dif < minDif) {
                minDif = dif
                minDifAlpha = alpha
                color = blended
            }
            if (blended == target) {
                println("success at $alpha")
                break
            }
        }
        println("no matches")
        println("min dif $minDif")
        println("min dif alpha $minDifAlpha")
        println("min dif color ${String.format("#%06X", 0xFFFFFF and color)}")
        println("target ${String.format("#%06X", 0xFFFFFF and target)}")
    }
}
