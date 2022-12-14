/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 27/06/22, 11:22 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 27/06/22, 11:22 PM
 ************************************************/


package health.data.ai.proacdoc.room

import androidx.room.Database
import androidx.room.RoomDatabase
import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.room.dao.CartDAO
import health.data.ai.proacdoc.room.dao.MedicineDAO
import health.data.ai.proacdoc.room.dao.MedicineTimingDAO
import health.data.ai.proacdoc.room.dao.UserDAO
import health.data.ai.proacdoc.room.entity.Cart
import health.data.ai.proacdoc.room.entity.MedicineEntity
import health.data.ai.proacdoc.room.entity.MedicineTimingsEntity


@Database(entities = [UserModel::class, Cart::class,MedicineEntity::class,MedicineTimingsEntity::class], version = 15)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDAO
    abstract fun userDAO(): UserDAO
    abstract fun medicineDao(): MedicineDAO
    abstract fun medicineTimingDao():MedicineTimingDAO
}