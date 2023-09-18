package lt.vaikri.playground

import androidx.core.graphics.ColorUtils
import androidx.core.graphics.toColorInt
import org.junit.Test

class ColorFindTest {
    @Test
    fun colorOnPrimaryButton() {
        val background: Int = (0xFF9BD3F1).toColorInt()
        val text = (0xFFFFFFFF).toColorInt()
        val target = (0xFFE0F2FE).toColorInt()

        var alpha = 0xFF
        while (alpha-- > 0) {
            val textColor = ColorUtils.setAlphaComponent(text, alpha)
            val blended = ColorUtils.compositeColors(textColor, background)
            if (blended == target) {
                println("success at $alpha")
                break
            }
        }
    }

    @Test
    fun colorOnError() {

    }
}
