package com.example.findvenues.loginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import com.example.findvenues.databinding.ActivityResetPasswordBinding


class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val MIN_PASSWORD_LENGTH = 6;
    private val loginModel by viewModels<LoginModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btResetPassword.setOnClickListener {
            performResetPassword()

        }
    }

    private fun performResetPassword() {
        if (validateInput()) {
            val email = binding.etEmail.text.toString()
            val password = binding.etNewPassword.text.toString()
            loginModel.findUserId().forEach { user ->

                if (user.email == email) {
                    loginModel.updateUserPassword(password,user.email)
                    Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        if (!isEmailValid(binding.etEmail.text.toString())) {
            binding.etEmail.error = "Please Enter a Valid Email"
            binding.etEmail.requestFocus()
            return false
        }
        if (binding.etNewPassword.text?.length!! < MIN_PASSWORD_LENGTH) {
            binding.etNewPassword.error = "Password Length must be more than $MIN_PASSWORD_LENGTH characters"
            return false
        }
        if (binding.etNewPassword.text.toString() == "") {
            binding.etNewPassword.error = "Please enter your new password"
            binding.etNewPassword.requestFocus()
            return false
        }
        if (binding.etNewPassword.text.toString() != binding.etRepeatPassword.text.toString()) {
            binding.etRepeatPassword.error = "Password does not match"
            return false
        }
        return true
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}