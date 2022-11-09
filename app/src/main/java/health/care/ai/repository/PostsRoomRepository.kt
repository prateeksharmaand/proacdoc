/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:03 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:03 PM
 ************************************************/

package health.care.ai.repository

import health.care.ai.room.dao.CartDAO
import health.care.ai.room.dao.UserDAO
import health.care.ai.room.entity.Cart


class PostsRoomRepository(private val userDAO: UserDAO, private val cartDAO: CartDAO) {
    suspend fun checkisUserLoggedIn() = userDAO.getUsers();
    suspend fun AddUser(userModel: health.care.ai.api.models.User.UserModel?) =
        userDAO.AddUser(userModel)

    suspend fun deleteUser() = userDAO.delete()
    suspend fun updateProfile(userModel: health.care.ai.api.models.User.UserModel) =
        userDAO.updatePofile(userModel)

    fun checkisUserLoggedInFlow() = userDAO.getUsersFlow();
    fun getcart() = cartDAO.getCart();


    suspend fun addTest(cart: Cart) = cartDAO.addTest(cart)

    suspend fun delete(model: Cart) = cartDAO.delete(model)
    suspend fun clearCart() = cartDAO.clearCart()

    suspend fun isCartItemExist(code: String) = cartDAO.isCartItemExist(code)
}