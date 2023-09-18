package lt.vaikri.playground.edittext

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import lt.vaikri.playground.R

class EditTextTestActivity : AppCompatActivity(R.layout.activity_test_edit_text) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val handler = Handler(Looper.getMainLooper())
        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            handler.postDelayed(
                {
                    editText.setText("")
                },
                1000L
            )
        }
    }
}
