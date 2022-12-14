/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:04 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:04 PM
 ************************************************/

package health.data.ai.proacdoc.room.dao


import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert
    suspend fun AddUser(userModel: health.data.ai.proacdoc.api.models.User.UserModel?)

    @Query("SELECT * FROM UserModel")
    suspend fun getUsers(): List<health.data.ai.proacdoc.api.models.User.UserModel>

    @Query("DELETE FROM UserModel")
    fun delete()

    @Update
    fun updatePofile(user: health.data.ai.proacdoc.api.models.User.UserModel)

    @Query("SELECT * FROM UserModel")
     fun getUsersFlow(): Flow<List<health.data.ai.proacdoc.api.models.User.UserModel>>

}