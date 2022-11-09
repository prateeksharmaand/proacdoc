/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 30/06/22, 4:27 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 30/06/22, 4:27 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 03/06/22, 10:45 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/06/22, 10:37 AM
 ************************************************/


package health.data.ai.proacdoc.ui.PostNotification

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import health.care.ai.proacdoc.databinding.ActivityPostnotificationBinding


import health.data.ai.proacdoc.ui.login.LoginViewModel

import org.koin.androidx.viewmodel.ext.android.viewModel

class PostNotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostnotificationBinding

    private val loginViewModel: LoginViewModel by viewModel()
    var PostId: Int = 0;
    private lateinit var user: health.data.ai.proacdoc.api.models.User.UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostnotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            PostId = intent.getIntExtra("postId", 0)

        }






        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        // postsViewModel.GetPostLikes(post.postId, UserId)
        loginViewModel.getLoggedInUser();


    }



    
}