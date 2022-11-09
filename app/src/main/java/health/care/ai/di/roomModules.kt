/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 22/06/22, 2:06 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 22/06/22, 2:06 PM
 ************************************************/


package health.care.ai.di

import androidx.room.Room
import health.care.ai.room.PostsDatabase
import health.care.ai.room.dao.MedicineTimingDAO

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val postDatabaseModule = module {

    single {
        Room.databaseBuilder(androidApplication(), PostsDatabase::class.java, "Ayushman")
            .fallbackToDestructiveMigration().build()
    }
    single { get<PostsDatabase>().cartDao() }
    single { get<PostsDatabase>().userDAO() }
    single { get<PostsDatabase>().medicineDao() }
    single { get<PostsDatabase>().medicineTimingDao() }

}




