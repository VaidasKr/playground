package lt.vaikri.playground

import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import lt.vaikri.playground.banner.BannerMessageView
import lt.vaikri.playground.banner.StatusBannerService
import lt.vaikri.playground.banner.StatusMessage

class TestBannerActivity : AppCompatActivity(R.layout.activity_test_banner) {
    private val service by lazy { StatusBannerService() }

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
        findViewById<Button>(R.id.internetButton)
            .setOnClickListener {
                toggle(StatusMessage.NO_INTERNET)
            }
        findViewById<Button>(R.id.gpsButton).setOnClickListener {
            toggle(StatusMessage.NO_GPS)
        }
        findViewById<Button>(R.id.otherButton).setOnClickListener {
            toggle(StatusMessage.OTHER)
        }
        val textView = findViewById<TextView>(R.id.textView)
        lifecycleScope.launch {
            service.stateFlow.collect {
                if (it.messages.isEmpty()) {
                    textView.text = "Empty"
                } else {
                    textView.text = it.messages.joinToString(separator = "\n") { it.name }
                }
            }
        }
    }

    private fun toggle(message: StatusMessage) {
        if (service.state.messages.contains(message)) {
            service.removeMessage(message)
        } else {
            service.addMessage(message)
        }
    }
}
