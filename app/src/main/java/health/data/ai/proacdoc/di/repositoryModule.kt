/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/07/22, 4:47 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/07/22, 4:47 PM
 ************************************************/


package health.data.ai.proacdoc.di


import health.data.ai.proacdoc.repository.*
import org.koin.dsl.module

val repositoryModule = module {


    single { PostsRoomRepository(userDAO = get(), cartDAO = get()) }
    single { LoginServerRepository(get()) }


    single { MedicalReportsRepository(postsServices = get()) }
    single { SmartHealthRepository(postsServices = get()) }
    single { LabTestsRepository(postsServices = get()) }
    single { MedicineRoomRepository(medicineDAO = get(), medicineTimingDAO = get()) }
    single { AbhaRepository(get()) }








}