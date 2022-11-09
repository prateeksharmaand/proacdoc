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
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import health.data.ai.proacdoc.room.entity.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDAO {
    @Insert
    suspend fun addTest(cart: Cart?)



    @Query("SELECT * FROM Cart")
     fun getCart(): Flow<List<Cart>>

    @Delete
    fun delete(model: Cart)




    @Query("DELETE FROM Cart")
    fun clearCart()

    @Query("SELECT EXISTS(SELECT * FROM Cart WHERE name = :name)")
    fun isCartItemExist(name: String) : Flow<Boolean>


}