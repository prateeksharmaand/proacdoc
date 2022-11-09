/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/07/22, 4:38 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/07/22, 4:38 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 23/06/22, 11:43 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 23/06/22, 11:43 AM
 ************************************************/

package health.data.ai.proacdoc.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import health.data.ai.proacdoc.room.entity.MedicineEntity


class MedicineListDiffCallback(oldEmployeeList: List<MedicineEntity>, newEmployeeList: List<MedicineEntity>) :
    DiffUtil.Callback() {
    private val mOldEmployeeList: List<MedicineEntity>
    private val mNewEmployeeList: List<MedicineEntity>
    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].Id == mNewEmployeeList[newItemPosition].Id&&mOldEmployeeList[oldItemPosition].Isfavourite == mNewEmployeeList[newItemPosition].Isfavourite
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList.get(oldItemPosition).equals(mNewEmployeeList.get(newItemPosition));
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    init {
        mOldEmployeeList = oldEmployeeList
        mNewEmployeeList = newEmployeeList
    }
}