/*************************************************
 * Created by Efendi Hariyadi on 10/08/22, 11:22 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 10/08/22, 11:22 AM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 10/08/22, 11:22 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 10/08/22, 11:22 AM
 ************************************************/

package health.data.ai.proacdoc.ui.mobilenumber

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import health.care.ai.proacdoc.databinding.ActivityMobileNumberBinding
import health.data.ai.proacdoc.api.models.User.UserModel

import health.data.ai.proacdoc.ui.home.HomeActivity
import health.data.ai.proacdoc.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class MobileNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMobileNumberBinding
    private lateinit var auth: FirebaseAuth
    private val loginViewModel: LoginViewModel by viewModel()
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var user: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObserver()
        loginViewModel.checkLoggedInUserFlow();

    }

    fun initUI() {


        setSupportActionBar(binding.toolbar)

        val fromLogin = intent.getBooleanExtra("fromLogin", true)
        if (fromLogin) {

        }
        else
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

                onBackPressed()

            })
        }


        auth = Firebase.auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {


                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.


                if (e is FirebaseAuthInvalidCredentialsException) {

                } else if (e is FirebaseTooManyRequestsException) {

                }


            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                binding.llMobileNo.visibility = View.GONE
                binding.btnSendOtp.visibility = View.GONE
                binding.llOtp.visibility = View.VISIBLE

                Toast.makeText(
                    applicationContext,
                    "Verification code has been sent",
                    Toast.LENGTH_SHORT
                ).show()
                storedVerificationId = verificationId
                resendToken = token
            }
        }
        binding.btnSendOtp.setOnClickListener(View.OnClickListener {
            if(binding.etMobile.text.length!=10)
            {
                Toast.makeText(
                    applicationContext,
                    "Enter Valid 10 Digit Mobile Number",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                Toast.makeText(
                    applicationContext,
                    "Setting up Verification",
                    Toast.LENGTH_SHORT
                ).show()
                startPhoneNumberVerification("+" + binding.ccp.selectedCountryCode + binding.etMobile.text)
            }

        })
        binding.btnVerifyOtp.setOnClickListener(View.OnClickListener {

            verifyPhoneNumberWithCode(storedVerificationId, binding.etOtp.otp.toString())
        })


        binding.etOtp.otpListener = (object : OTPListener {
            override fun onInteractionListener() {
                // fired when user types something in the Otpbox
            }

            override fun onOTPComplete(otp: String) {
                binding.btnVerifyOtp.performClick()
            }
        })

    }


    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {

        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)

    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    user.mobile = "+" + binding.ccp.selectedCountryCode + binding.etMobile.text
                    loginViewModel.updateProfile(user)
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                        Toast.makeText(
                            applicationContext,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // Update UI
                }
            }
    }

    fun initObserver() {
        loginViewModel.userDetails.observe(this, Observer {
            user = it
        })
        loginViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        loginViewModel.updateMobileStatus.observe(this, Observer {
            Toast.makeText(
                applicationContext,
                it.message,
                Toast.LENGTH_SHORT
            ).show()
            user.mobile = "+" + binding.ccp.selectedCountryCode + binding.etMobile.text

            loginViewModel.updateUserProfile(user)


            val fromLogin = intent.getBooleanExtra("fromLogin", true)
            if (fromLogin) {
                val intent = Intent(
                    this, HomeActivity
                    ::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(
                    intent
                )
                finish()
            }
            else
            {
                onBackPressed()
            }




























        })


    }


}