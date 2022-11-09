/*************************************************
 * Created by Efendi Hariyadi on 04/08/22, 1:52 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 04/08/22, 12:32 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 04/08/22, 12:04 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 04/08/22, 12:04 PM
 ************************************************/

package health.data.ai.proacdoc.ui.adapters
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.application.MainApp
import health.data.ai.proacdoc.ui.reportdetails.smartreport.MedicalRecordSmartReportFragment
import health.data.ai.proacdoc.ui.reportdetails.medicalpdf.MedicalRecordsPDFFragment

class MedicalRecordsDetailsAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return if(MainApp.smartReport==1)2 else 1
    }

    override fun getItem(position: Int): Fragment {


        if(MainApp.smartReport==1)
        {
            when(position) {
                0 -> {
                    return MedicalRecordSmartReportFragment()
                }
                1 -> {
                    return MedicalRecordsPDFFragment()
                }

                else -> {
                    return MedicalRecordsPDFFragment()
                }
            }
        }
        else {
            when(position) {
                0 -> {
                    return MedicalRecordsPDFFragment()
                }

                else -> {
                    return MedicalRecordsPDFFragment()
                }
            }

        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return if(MainApp.smartReport==1)MainApp.instance.getString(R.string.SmartReport)  else MainApp.instance.getString(R.string.orignamrecord)
            }
            1 -> {
                return MainApp.instance.getString(R.string.orignamrecord)
            }

        }
        return super.getPageTitle(position)
    }

}
