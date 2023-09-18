package lt.vaikri.playground.banner

import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import lt.vaikri.playground.R
import lt.vaikri.playground.ui.theme.PlaygroundTheme

class TestBannerActivity : AppCompatActivity(R.layout.activity_test_banner) {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, windowInsets ->
            val system = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<MarginLayoutParams> {
                bottomMargin = system.bottom
                leftMargin = system.left
                rightMargin = system.right
                topMargin = system.top
            }
            windowInsets
        }
        findViewById<BannerMessageView>(R.id.bannerMessageView).apply {
            setHasStatusBarPadding(true)
            attach(this@TestBannerActivity)
        }
        findViewById<ComposeView>(R.id.composeView).apply {
            setContent {
                PlaygroundTheme {
                    BannerTestContent()
                }
            }
        }
    }
}
