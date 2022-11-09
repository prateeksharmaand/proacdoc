/*************************************************
 * Created by Efendi Hariyadi on 09/08/22, 5:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 09/08/22, 5:39 PM
 ************************************************/

package health.data.ai.proacdoc.ui.heartratemonitor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.android.play.core.review.ReviewManagerFactory
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.ActivityHeartRateMonitorBinding

import health.data.ai.proacdoc.heartmonitor.camera.HeartRateOmeter
import health.data.ai.proacdoc.ui.heartratedetails.HeartRateDetailsActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_heart_rate_monitor.*
import kotlinx.coroutines.*
import net.kibotu.kalmanrx.jama.Matrix
import net.kibotu.kalmanrx.jkalman.JKalman

class HeartRateMonitorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeartRateMonitorBinding
    var subscription: CompositeDisposable? = null
    var increment: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeartRateMonitorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animationView: LottieAnimationView = findViewById(R.id.img)
        animationView.setAnimation("hb.json")
        animationView.loop(true)
        animationView.playAnimation()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        try {
            val manager = ReviewManagerFactory.create(applicationContext)
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val reviewInfo = task.result
                    val flow = manager.launchReviewFlow(this, reviewInfo)
                    flow.addOnCompleteListener { _ ->

                    }

                } else {

                }
            }
        } catch (e: Exception) {
        }

    }

    private fun startWithPermissionCheck() {
        if (!hasPermission(Manifest.permission.CAMERA)) {
            checkPermissions(REQUEST_CAMERA_PERMISSION, Manifest.permission.CAMERA)
            return
        }

        val kalman = JKalman(2, 1)

        // measurement [x]
        val m = Matrix(1, 1)

        // transitions for x, dx
        val tr = arrayOf(doubleArrayOf(1.0, 0.0), doubleArrayOf(0.0, 1.0))
        kalman.transition_matrix = Matrix(tr)

        // 1s somewhere?
        kalman.error_cov_post = kalman.error_cov_post.identity()


        val bpmUpdates = HeartRateOmeter()
            .withAverageAfterSeconds(1)
            .setFingerDetectionListener(this::onFingerChange)
            .bpmUpdates(binding.preview)
            .subscribe({

                if (it.value == 0)
                    return@subscribe

                m.set(0, 0, it.value.toDouble())
                kalman.Predict()
                // corrected state [x, dx]
                val c = kalman.Correct(m)

                val bpm = it.copy(value = c.get(0, 0).toInt())

                onBpm(bpm)
            }, Throwable::printStackTrace)

        subscription?.add(bpmUpdates)
    }


    private fun onBpm(bpm: HeartRateOmeter.Bpm) {
//        Log.v("HeartRateOmeter", "[onBpm] $bpm")
        binding.txtBPM.text = bpm.value.toString()

    }

    private fun onFingerChange(fingerDetected: Boolean) {
        increment = 0
        binding.circularProgressBar.progress = 0f
        var jobs = CoroutineScope(Dispatchers.Main).launch() {
            val totalSeconds = 25
            val tickSeconds = 1
            for (second in totalSeconds downTo tickSeconds) {
                if (binding.llMain.visibility == View.VISIBLE) {
                    increment += 1
                }

                val animationDuration = 1000L
                binding.circularProgressBar.setProgressWithAnimation(
                    increment.toFloat(),
                    animationDuration
                )


                delay(1000)
                if (second == 5) {
                    if (binding.llMain.visibility == View.VISIBLE) {
                        val intent = Intent(this@HeartRateMonitorActivity, HeartRateDetailsActivity::class.java)
                        intent.putExtra("record",txtBPM.text.toString().toInt())
                        startActivity(intent)
                        finish()
                    }


                }
            }

        }

        if (fingerDetected) {

            binding.cardplacefinger.visibility = View.GONE
            binding.imgtop.visibility = View.GONE
            binding.llMain.visibility = View.VISIBLE


        } else {
            jobs.cancel()
            binding.cardplacefinger.visibility = View.VISIBLE
            binding.llMain.visibility = View.GONE
            binding.imgtop.visibility = View.VISIBLE
            increment = 0
            binding.circularProgressBar.progress = 0f
            // timer.cancel()


        }


    }

// region lifecycle

    override fun onResume() {
        super.onResume()

        dispose()
        subscription = CompositeDisposable()

        startWithPermissionCheck()
    }

    override fun onPause() {
        dispose()
        super.onPause()
    }

    private fun dispose() {
        if (subscription?.isDisposed == false)
            subscription?.dispose()
    }


    companion object {
        private val REQUEST_CAMERA_PERMISSION = 123
    }

    private fun checkPermissions(callbackId: Int, vararg permissionsId: String) {
        when {
            !hasPermission(*permissionsId) -> try {
                ActivityCompat.requestPermissions(this, permissionsId, callbackId)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun hasPermission(vararg permissionsId: String): Boolean {
        var hasPermission = true

        permissionsId.forEach { permission ->
            hasPermission = hasPermission
                    && ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        return hasPermission
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startWithPermissionCheck()
                }
            }
        }
    }

}

