package hu.bme.aut.android.publictransport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import hu.bme.aut.android.publictransport.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if(binding.etEmailAddress.text.toString().isEmpty()) {
                binding.etEmailAddress.requestFocus()
                binding.etEmailAddress.error = "Please enter your email address"
            }
            else if(binding.etPassword.text.toString().isEmpty()) {
                binding.etPassword.requestFocus()
                binding.etPassword.error = "Please enter your password"
            }
            else {
                startActivity(Intent(this, ListActivity::class.java))
            }
        }
    }
}
// NEPTUN: JOYAXJ