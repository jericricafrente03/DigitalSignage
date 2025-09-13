package com.jeric.bitteldigitalsignage.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.databinding.FragmentSplashScreenBinding
import com.jeric.bitteldigitalsignage.ui.home.HomeScreen
import com.jeric.bitteldigitalsignage.ui.signup.SignupScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomSplash : AppCompatActivity() {

    private val customSplashViewModel: CustomSplashViewModel by viewModels()
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                customSplashViewModel.uiState.observe(this@CustomSplash) {
                    when (it) {
                        CustomSplashUiState.Authenticated -> {
                            startActivity(Intent(this@CustomSplash, HomeScreen::class.java))
                            finish()
                        }
                        CustomSplashUiState.Splash -> {
                            startActivity(Intent(this@CustomSplash, HomeScreen::class.java))
//                            startActivity(Intent(this@CustomSplash, SignupScreen::class.java))
                            finish()
                        }
                    }
                }
            }
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })
    }
}