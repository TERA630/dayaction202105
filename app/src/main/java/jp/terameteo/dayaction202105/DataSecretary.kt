package jp.terameteo.dayaction202105

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.text.format.DateFormat.getBestDateTimePattern
import java.util.*

class DataSecretary {

    fun getTodayString(): String {
        val local = Locale.JAPAN
        val pattern = getBestDateTimePattern(local, "YYYYEEEMMMd")
        val dateFormat = SimpleDateFormat(pattern, local)
        return dateFormat.format(System.currentTimeMillis())
    }

    fun saveIntToPreference(_key: String, _int:Int, context: Context) {
        val preferences = context.getSharedPreferences(_key, Context.MODE_PRIVATE)
        val preferenceEditor = preferences.edit()
        preferenceEditor.putInt(_key, _int)
        preferenceEditor.apply()
    }
    fun loadIntFromPreference(_key: String, context: Context): Int {
        val preferences = context.getSharedPreferences(_key, Context.MODE_PRIVATE)
        return preferences.getInt(_key,0)
    }


}
