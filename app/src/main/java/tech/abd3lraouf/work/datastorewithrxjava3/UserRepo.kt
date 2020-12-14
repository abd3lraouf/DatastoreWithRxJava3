package tech.abd3lraouf.work.datastorewithrxjava3

import android.content.Context
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.toMutablePreferences
import androidx.datastore.preferences.core.toPreferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.rxjava3.data
import androidx.datastore.rxjava3.updateDataAsync
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class UserRepo(context: Context) {
    private val dataStore = context.createDataStore(name = "user_prefs")

    companion object {
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
        val USER_AGE_KEY = preferencesKey<Int>("USER_AGE")
        val USER_GENDER_KEY = preferencesKey<Boolean>("USER_GENDER")
    }

    fun storeUser(name: String, age: Int, isMale: Boolean) {
        dataStore.updateDataAsync {
            val result = it.toMutablePreferences()
                .apply {
                    this[USER_NAME_KEY] = name
                    this[USER_AGE_KEY] = age
                    this[USER_GENDER_KEY] = isMale
                }.toPreferences()
            Single.just(result)
        }
    }

    fun userName(): Flowable<String> = dataStore.data().map { it[USER_NAME_KEY] ?: "" }
    fun userAge(): Flowable<Int> = dataStore.data().map { it[USER_AGE_KEY] ?: 0 }
    fun userGender(): Flowable<Boolean> = dataStore.data().map { it[USER_GENDER_KEY] ?: false }
}