/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:08 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:08 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 12/06/22, 11:14 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/06/22, 11:05 AM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 1:47 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 12:08 AM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 10:02 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 10:02 PM
 ************************************************/

package health.data.ai.proacdoc.ui.home


import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging

import health.care.ai.proacdoc.databinding.ActivityHomeBinding
import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.application.MainApp

import health.data.ai.proacdoc.ui.homefragment.LabTestViewModel
import health.data.ai.proacdoc.ui.login.LoginActivity
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.ui.mobilenumber.MobileNumberActivity
import health.data.ai.proacdoc.ui.viewcart.ViewCartActivity
import health.data.ai.proacdoc.utils.DataStoreManager
import health.data.ai.proacdoc.utils.Utils.CompanionClass.Companion.setAppLocale
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

import health.care.ai.proacdoc.R
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val homeViewModel: HomeViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private val labTestViewModel: LabTestViewModel by viewModel()
    private lateinit var file: File

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    private lateinit var user: UserModel

    private lateinit var dataStoreManager: DataStoreManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        permissionsBuilder(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.SCHEDULE_EXACT_ALARM
        ).build().send { result ->
            if (result.allGranted()) {
                // All the permissions are granted.
            }
        }
        dataStoreManager = DataStoreManager(this)
        initUI()
        initObserver()

        setupWithNavController(binding.bottomNavigationView, navController)



        loginViewModel.checkLoggedInUserFlow();

        try {
            val appUpdateManager = AppUpdateManagerFactory.create(this)

// Returns an intent object that you use to check for an update.
            val appUpdateInfoTask = appUpdateManager.appUpdateInfo

// Checks that the platform will allow the specified type of update.
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        // Pass the intent that is returned by 'getAppUpdateInfo()'.
                        appUpdateInfo,
                        // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                        AppUpdateType.IMMEDIATE,
                        // The current activity making the update request.
                        this,
                        // Include a request code to later monitor this update request.
                        101
                    )

                }
            }
        } catch (e: Exception) {
        }


    }


    fun initUI() {

        setSupportActionBar(binding.toolbar)
        navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar, R.string.drawer_open,
            R.string.drawer_close
        )

        binding.imgChangelanguage.setOnClickListener(View.OnClickListener {

            openeAddBottomSheet()

            lifecycleScope.launch {
                /* dataStoreManager.setLanguage("hi")

                 val intent = intent
                 finish()
                 startActivity(intent)
                 overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);*/

            }


        })
        binding.relNotify.setOnClickListener(View.OnClickListener {

            /*   lifecycleScope.launch {
                   dataStoreManager.setLanguage("en")

                   val intent = intent
                   finish()
                   startActivity(intent)
                   overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

               }*/


            val intent = Intent(this, ViewCartActivity::class.java)
            startActivity(intent)
        })
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        appBarConfiguration = AppBarConfiguration(
            navController.graph,
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId








            if (id == R.id.logout) {

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder
                    .setTitle("Logout!")
                    .setMessage("Are you sure you want to  logout?")
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            CoroutineScope(Dispatchers.IO).launch {

                                GoogleSignIn.getClient(
                                    applicationContext,
                                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .build()
                                ).signOut()

                                startActivity(
                                    Intent(
                                        applicationContext, LoginActivity
                                        ::class.java
                                    )
                                )
                                finish()

                            }
                        })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()


            }


            if (id == R.id.updatephone) {

                val intent = Intent(
                    this, MobileNumberActivity
                    ::class.java
                )
                intent.putExtra("fromLogin", false)
                startActivity(
                    intent
                )


            }


            NavigationUI.onNavDestinationSelected(menuItem, navController)
            val drawer = findViewById<View>(R.id.drawerLayout) as DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
            true
        })


    }


    override fun onOptionsItemSelected(item: MenuItem) =
        item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item)


    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration)

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()

        }
    }


    fun initObserver() {


        loginViewModel.userDetails.observe(this, Observer {
            user = it
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (it.isComplete) {

                    homeViewModel.updateToken(user.UserId, it.result.toString())

                }
            }

            try {
                val headerLayout: View = nav_view.getHeaderView(0)
                var userName = headerLayout.findViewById<TextView>(R.id.txtNavName)
                var userImage = headerLayout.findViewById<ImageView>(R.id.userImage)
                userName.text = user.Name

                Glide.with(health.data.ai.proacdoc.application.MainApp.instance)

                    .load(user.Image).placeholder(resources.getDrawable(R.drawable.ic_user))
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(userImage)
            } catch (e: Exception) {
            }

        })


        /* homeViewModel.loading.observe(this, Observer {
             if (it) {
                 binding.progressBar.visibility = View.VISIBLE
             } else {
                 binding.progressBar.visibility = View.GONE
             }
         })*/
        labTestViewModel.cartCount.observe(this, Observer {


            if (it == 0) {
                binding.tvNotificationBadge.visibility = View.GONE

            } else {
                binding.tvNotificationBadge.text = it.toString()
                binding.tvNotificationBadge.visibility = View.VISIBLE
            }

        })
        labTestViewModel.getTestCount()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale(MainApp.lang)))
    }

    fun openeAddBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.TransparentDialog)
        bottomSheetDialog.setContentView(R.layout.chnagelanguagebottomsheet)
        val lottieenglish = bottomSheetDialog.findViewById<LottieAnimationView>(R.id.lottieenglish)
        val lottieHindi = bottomSheetDialog.findViewById<LottieAnimationView>(R.id.lottieHindi)
        val crdEnglish = bottomSheetDialog.findViewById<CardView>(R.id.crdEnglish)
        val crdHindi = bottomSheetDialog.findViewById<CardView>(R.id.crdHindi)



        if (MainApp.lang == "en"||MainApp.lang =="") {
            lottieenglish!!.visibility = View.VISIBLE
            lottieHindi!!.visibility = View.INVISIBLE
            crdHindi!!.setOnClickListener(View.OnClickListener {
                lifecycleScope.launch {
                    dataStoreManager.setLanguage("hi")

                    val intent = intent
                    finish()
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
                Toast.makeText(
                    applicationContext,
                    "Updating Language Preference",
                    Toast.LENGTH_SHORT
                ).show()

            })

        } else {
            lottieHindi!!.visibility = View.VISIBLE
            lottieenglish!!.visibility = View.INVISIBLE

            crdEnglish!!.setOnClickListener(View.OnClickListener {
                lifecycleScope.launch {
                    dataStoreManager.setLanguage("en")

                    val intent = intent
                    finish()
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }

                Toast.makeText(
                    applicationContext,
                    "Updating Language Preference",
                    Toast.LENGTH_SHORT
                ).show()

            })


        }

        bottomSheetDialog.show()

    }
}