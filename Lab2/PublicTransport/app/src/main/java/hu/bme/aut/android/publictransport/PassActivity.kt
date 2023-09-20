package hu.bme.aut.android.publictransport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.publictransport.databinding.ActivityPassBinding

class PassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassBinding

    companion object {
        const val KEY_DATE_STRING = "KEY_DATE_STRING"
        const val KEY_TYPE_STRING = "KEY_TYPE_STRING"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTicketType.text = intent.getStringExtra(KEY_TYPE_STRING)
        binding.tvDates.text = intent.getStringExtra(KEY_DATE_STRING)
    }
}
// JOYAXJ