/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/07/22, 5:40 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/07/22, 5:40 PM
 ************************************************/

package health.care.ai.application

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import health.care.ai.BuildConfig

import health.care.ai.di.*
import health.care.ai.utils.DataStoreManager
import health.care.ai.utils.Utils.CompanionClass.Companion.setAppLocale

import io.branch.referral.Branch
import kotlinx.coroutines.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApp : Application() {


    private lateinit var dataStoreManager: DataStoreManager
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    companion object {
        lateinit var instance: MainApp
            private set
        var pdfUrl: String = ""
        var recordId: Int = 0
        var fileType: Int = 0
        var smartReport: Int = 0
        var lang: String = "en"
    }

    override fun onCreate() {
        super.onCreate()
        dataStoreManager = DataStoreManager(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@MainApp)
            modules(
                listOf(
                    postDatabaseModule,
                    viewModelModule,
                    apiModule,
                    repositoryModule,
                    RetrofitModule

                )
            )

        }
        instance = this

        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);
        applicationScope.launch {
            dataStoreManager.getlanguage.collect { counter ->
                lang = counter
            }
        }


    }

    override fun onLowMemory() {
        super.onLowMemory()
        applicationScope.cancel()
    }


}