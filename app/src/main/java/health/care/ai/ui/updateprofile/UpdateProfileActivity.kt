/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 4:37 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 4:37 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 03/06/22, 10:45 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/06/22, 10:37 AM
 ************************************************/


package health.care.ai.ui.updateprofile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import health.care.ai.databinding.ActivityUpdateprofileBinding

import health.care.ai.ui.login.LoginViewModel
import health.care.ai.utils.Utils

import kotlinx.android.synthetic.main.activity_updateprofile.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateprofileBinding

    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var file: File


    private lateinit var user: health.care.ai.api.models.User.UserModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUpdateprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgUpload.setOnClickListener(View.OnClickListener {

        })
        initObserver()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        loginViewModel.getLoggedInUser();
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

            onBackPressed()

        })
        binding.UpdateNow.setOnClickListener {
            if (binding.etName.text?.isEmpty() == true) {
                binding.etNameLayout.error = "Enter User Name"
            } else {
                Utils.CompanionClass.hideKeyboard(binding.etName,this)
                binding.etNameLayout.error = null
                user.Name = etName.text.toString()
                val updateProfileModel =
                    health.care.ai.api.models.login.profile.UpdateProfileRequest()
                updateProfileModel.UserId = user.UserId
                updateProfileModel.image = user.Image
                updateProfileModel.name = user.Name
              //  loginViewModel.UpdateProfile(updateProfileModel)

            }
        }
    }

    fun initObserver() {
        loginViewModel.userDetails.observe(this, Observer {
            user = it
            Glide.with(health.care.ai.application.MainApp.instance)
                .load(user.Image)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.userImage)
            var name: String? = user.Name
            binding.etName.setText(name)
        })
        loginViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })




        loginViewModel.updateProfileResponse.observe(this, Observer {
            if (it.results.data == "0") {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                loginViewModel.updateUserProfile(user)
            } else {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })

    }


}