package health.data.ai.proacdoc.ui.alllabtest


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import health.data.ai.proacdoc.api.models.labtest.login.ProfileTest.Data

import health.care.ai.proacdoc.databinding.ActivityAllLabTestsBinding
import health.data.ai.proacdoc.ui.adapters.HomeProfileTestsAdapter
import health.data.ai.proacdoc.ui.profilelabtestdetails.LabTestProfileDetailsActivity


class AllLabTestsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllLabTestsBinding

    private lateinit var data:  ArrayList<Data>

    private lateinit var postAdapter: HomeProfileTestsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllLabTestsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initUI()
        initObserver()



    }


    fun initUI() {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })


    }


    fun initObserver() {
        data = intent.extras!!.get("record") as  ArrayList<Data>

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        postAdapter =
            HomeProfileTestsAdapter(HomeProfileTestsAdapter.OnItemClickListener { record ->

                val intent = Intent(this, LabTestProfileDetailsActivity::class.java)
                intent.putExtra("record", record)
                startActivity(intent)


            }, data, this)
        binding.recyclerView.adapter = postAdapter
        binding.recyclerView.itemAnimator = null;


    }


}