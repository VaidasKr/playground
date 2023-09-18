package lt.vaikri.playground.button

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.button.MaterialButtonToggleGroup
import lt.vaikri.playground.R
import java.util.*

class TestButtonActivity : AppCompatActivity(R.layout.activity_test_button) {

    private val buttons by lazy {
        arrayOf<PrimaryButton>(
            findViewById(R.id.primaryButton),
            findViewById(R.id.primaryExtendedButton)
        )
    }
    private val textView by lazy { findViewById<TextView>(R.id.textView) }
    private val messages = Array(10) { "" }
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for (i in messages.indices) {
            messages[i] = ""
        }

        textView.setOnClickListener {  }

        index = 0

        val group = findViewById<MaterialButtonToggleGroup>(R.id.buttonGroup)

        group.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when (checkedId) {
                R.id.enabledButton -> {
                    toggle { it.isEnabled = isChecked }
                }
                R.id.progressButton -> {
                    toggle { it.showProgress(isChecked) }
                }
                R.id.iconButton -> {
                    if (isChecked) {
                        toggle { it.icon = AppCompatResources.getDrawable(this, R.drawable.ic_forward) }
                    } else {
                        toggle { it.icon = null }
                    }
                }
            }
        }

        for (i in buttons.indices) {
            buttons[i].setOnClickListener { addMessage("${i + 1}") }
        }
    }

    @SuppressLint("NewApi")
    private fun addMessage(tag: String) {
        val date = Date()
        val message = "From $tag on $date"
        messages[index] = message

        val joined = buildString {
            for (i in messages.indices) {
                val adjusted = (index - i).mod(messages.size)
                val text = messages[adjusted]
                if (text.isNotEmpty()) {
                    appendLine(text)
                }
            }
        }

        textView.text = joined

        index++
        if (index >= messages.size) index = 0
    }

    private inline fun toggle(onUpdate: (PrimaryButton) -> Unit) {
        for (i in buttons.indices) onUpdate(buttons[i])
    }
}
