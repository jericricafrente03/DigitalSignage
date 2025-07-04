package com.jeric.bitteldigitalsignage.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jeric.bitteldigitalsignage.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.ui.home.HomeScreen
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class SignupScreen : AppCompatActivity() {

    private val signupViewModel: SignupViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            viewModel = signupViewModel
            btSave.setOnClickListener { signupViewModel.areInputsValid() }
        }

        observeErrorState()
        observeUiState()
    }

    private fun observeErrorState() {
        lifecycleScope.launch {
            signupViewModel.uiErrorState.collect { errorState ->
                binding.apply {
                    ipHost.error = errorState.hostErr?.let { getString(it) }
                    isPort.error = errorState.portErr?.let { getString(it) }
                    isRoom.error = errorState.roomErr?.let { getString(it) }
                    isMac.error = errorState.macErr?.let { getString(it) }
                }
            }
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            signupViewModel.uiState.collect { uiState ->
                if (!uiState.isLoading) {
                    uiState.result?.let {

                        val toastStyle = when (it) {
                            getString(R.string.successfully) -> MotionToastStyle.SUCCESS
                            else -> MotionToastStyle.ERROR
                        }
                        showMotionToast(
                            if (toastStyle == MotionToastStyle.SUCCESS) getString(R.string.hurray) else getString(R.string.failed),
                            it,
                            toastStyle
                        )
                        if (it == getString(R.string.successfully)) {
                            startActivity(Intent(this@SignupScreen, HomeScreen::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun showMotionToast(title: String, message: String, style: MotionToastStyle) {
        MotionToast.darkToast(
            context = this,
            title = title,
            message = message,
            style = style,
            position = MotionToast.GRAVITY_BOTTOM,
            duration = MotionToast.LONG_DURATION,
            font = ResourcesCompat.getFont(this, R.font.montserrat_light)
        )
    }
}