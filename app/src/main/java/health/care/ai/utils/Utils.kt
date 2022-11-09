/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 18/06/22, 3:29 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 18/06/22, 3:29 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 13/06/22, 12:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 13/06/22, 12:00 PM
 ************************************************/

package health.care.ai.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.text.Editable
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import health.care.ai.application.MainApp
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*


public class Utils {

    class CompanionClass {


        companion object {

            fun ConvertTimeStampintoAgo(timeStamp: Long?): String? {
                try {
                    val cal = Calendar.getInstance(Locale.getDefault())
                    cal.timeInMillis = timeStamp!!
                    val date = DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString()
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val dateObj: Date = sdf.parse(date)
                    val p = PrettyTime()
                    return p.format(dateObj)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                return ""
            }

            fun hideKeyboard(view: View, context: Context?) {
                val inputMethodManager =
                    context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }

            fun getAddressFromLatLng(
                context: Context?,
                latitude: String,
                longitude: String
            ): String {
                val geocoder: Geocoder
                val addresses: List<Address>
                geocoder = Geocoder(context, Locale.getDefault())
                return try {
                    addresses =
                        geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
                    addresses[0].getAddressLine(0)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
            }

            fun getRandomString(length: Int): String {
                val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                return (1..length)
                    .map { allowedChars.random() }
                    .joinToString("")
            }

            inline fun <T> T?.ifNull(block: () -> Unit): T? {
                if (this == null) block()
                return this@ifNull
            }

            inline fun <T> T?.ifNonNull(block: (T) -> Unit): T? {
                this?.let(block)
                return this@ifNonNull
            }
            fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


            fun Context.setAppLocale(language: String): Context {
                val locale = Locale(language)
                Locale.setDefault(locale)
                val config = resources.configuration
                config.setLocale(locale)
                config.setLayoutDirection(locale)
                return createConfigurationContext(config)
            }

        }
    }
}