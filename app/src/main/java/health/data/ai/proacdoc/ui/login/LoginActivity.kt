/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:37 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:37 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 1:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 1:39 PM
 ************************************************/

package health.data.ai.proacdoc.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.ActivityLoginBinding

import health.data.ai.proacdoc.ui.home.HomeActivity
import health.data.ai.proacdoc.ui.mobilenumber.MobileNumberActivity
import health.data.ai.proacdoc.ui.privacy.PrivacyPolicyActivity
import health.data.ai.proacdoc.utils.DataStoreManager
import io.branch.referral.Branch
import io.branch.referral.Branch.BranchReferralInitListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dataStoreManager: DataStoreManager


    private val loginViewModel: LoginViewModel by viewModel()
    var postId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()


        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)




        //   initObserver()
        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnGoogle.setOnClickListener {
            signInGoogle()
        }
        binding.btnSignIn.setOnClickListener(View.OnClickListener {


            signInGoogle()
        })

        initObserver()

    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.lvLogin.visibility = View.GONE
                var user = health.data.ai.proacdoc.api.models.User.UserModel()
                user.Name = account.displayName.toString().uppercase(Locale.ROOT)
                user.EmailAddress = account.email
                user.postId = postId
                user.Image = account.photoUrl?.toString()

                loginViewModel.loginUser(user)
            }
        }
    }


    private fun initObserver() {

        loginViewModel.loginStatus.observe(this) {


            if (it) {

                startActivity(
                    Intent(
                        this, HomeActivity
                        ::class.java
                    )
                )
                finish()


            } else {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Privacy Policy Consent")
                builder.setMessage("This app collects, transmits, syncs Primary Account information Email Address to enable features like Create Login and ABHA Id.")
                builder.setPositiveButton(R.string.taptoaccept) { dialog, which ->
                    binding.lvLogin.visibility = View.VISIBLE
                    signInGoogle()
                }
                builder.setNegativeButton(R.string.viewprivacypolicy) { dialog, which ->
                    startActivity(
                        Intent(
                            this, PrivacyPolicyActivity
                            ::class.java
                        ))
                }
                builder.show()








            }


        }
        loginViewModel.errorMessage.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        loginViewModel.checkLoginStatus()
        loginViewModel.loginResponse.observe(this) {
            binding.progressBar.visibility = View.GONE

            it.results.let { it1 ->

                loginViewModel.saveUser(it.results.data)


                if (it.results.data.mobile == null || it.results.data.mobile.isNullOrEmpty()) {

                    val intent = Intent(
                        this, MobileNumberActivity
                        ::class.java
                    )
                    intent.putExtra("fromLogin", true)
                    startActivity(
                        intent
                    )
                    finish()
                } else {
                    startActivity(
                        Intent(
                            this, HomeActivity
                            ::class.java
                        )
                    )
                    finish()
                }


            }


        }

    }


    override fun onStart() {
        super.onStart()
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener)
            .withData(if (intent != null) intent.data else null).init()
    }

    private val branchReferralInitListener =
        BranchReferralInitListener { linkProperties, error ->
            val sessionParams = Branch.getInstance().latestReferringParams
            postId = sessionParams.optInt("~campaign", 0)


        }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        // if activity is in foreground (or in backstack but partially visible) launching the same
        // activity will skip onStart, handle this case with reInitSession
        if (intent != null &&
            intent.hasExtra("branch_force_new_session") &&
            intent.getBooleanExtra("branch_force_new_session", false)
        ) {
            Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit()
        }
    }


}