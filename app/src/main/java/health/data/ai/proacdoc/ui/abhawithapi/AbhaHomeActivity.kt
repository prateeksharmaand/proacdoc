/*************************************************
 * Created by Efendi Hariyadi on 11/10/22, 1:35 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/10/22, 12:28 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/10/22, 12:27 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/10/22, 12:26 PM
 ************************************************/

package health.data.ai.proacdoc.ui.abhawithapi

import android.content.Intent
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import health.care.ai.proacdoc.databinding.ActivityAbhaHomeBinding
import health.data.ai.proacdoc.ui.createabhaaadharactivity.CreateAbhaAdharActivity


class AbhaHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAbhaHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

    }

    fun initUI() {


        setSupportActionBar(binding.toolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

            onBackPressed()

        })

        binding.btnCreateAbha.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, CreateAbhaAdharActivity::class.java)
            startActivity(intent)
        })


    }


}