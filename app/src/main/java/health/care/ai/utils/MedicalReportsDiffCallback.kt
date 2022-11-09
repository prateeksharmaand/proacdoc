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

package health.care.ai.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import health.care.ai.api.models.medicalreport.getreportspdf.Data


class MedicalReportsDiffCallback(oldEmployeeList: List<Data>, newEmployeeList: List<Data>) :
    DiffUtil.Callback() {
    private val mOldEmployeeList: List<Data>
    private val mNewEmployeeList: List<Data>
    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].recordId == mNewEmployeeList[newItemPosition].recordId&&mOldEmployeeList[oldItemPosition].fileUrl == mNewEmployeeList[newItemPosition].fileUrl
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList.get(oldItemPosition).equals(mNewEmployeeList.get(newItemPosition));
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    init {
        mOldEmployeeList = oldEmployeeList
        mNewEmployeeList = newEmployeeList
    }
}