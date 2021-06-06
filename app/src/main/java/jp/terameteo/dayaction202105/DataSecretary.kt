package jp.terameteo.dayaction202105

import android.icu.text.SimpleDateFormat
import android.text.format.DateFormat.getBestDateTimePattern
import java.util.*

class DataSecretary {

    fun getTodayString(): String {
        val local = Locale.JAPAN
        val epochSecond = System.currentTimeMillis()
        val pattern = getBestDateTimePattern(local, "YYYYEEEMMMd")
        val dateFormat = SimpleDateFormat(pattern, local)
        val todayString = dateFormat.format(epochSecond)
        return todayString
    }


}
