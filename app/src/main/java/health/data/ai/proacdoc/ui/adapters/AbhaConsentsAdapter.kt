
package health.data.ai.proacdoc.ui.adapters
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.application.MainApp
import health.data.ai.proacdoc.ui.abhaconsentdetails.*
import health.data.ai.proacdoc.ui.reportdetails.smartreport.MedicalRecordSmartReportFragment
import health.data.ai.proacdoc.ui.reportdetails.medicalpdf.MedicalRecordsPDFFragment

class AbhaConsentsAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {


        return when(position) {
            0 -> {
                AbhaConsentRequestedFragment()
            }
            1 -> {
                AbhaConsentgrantedFragment()
            }
            2 -> {
                AbhaConsentDeniedFragment()
            }

            else -> {
                AbhaConsentExpiredFragment()
            }}

    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Pending"
            }
            1 -> {
                return "Granted"
            }
            2 -> {
                return "Denied"
            }
            3 -> {
                return "Expired"
            }

        }
        return super.getPageTitle(position)
    }


}
