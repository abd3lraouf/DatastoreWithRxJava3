package tech.abd3lraouf.work.datastorewithrxjava3

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.rxjava3.data
import androidx.datastore.rxjava3.updateDataAsync
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class MessageRepo(context: Context) {
    private val dataStore: DataStore<MessagePreferences> by lazy {
        context.createDataStore(
            fileName = "message-preferences.pb",
            serializer = MessagePreferencesSerializer
        )
    }

    fun saveMessage(newContent: String) {
        dataStore.updateDataAsync {
            val result = it.toBuilder()
                .setContent(newContent)
                .build()
            Single.just(result)
        }
    }

    fun message(): Flowable<String> = dataStore.data().map { it.content }
}