package tech.abd3lraouf.work.datastorewithrxjava3

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object MessagePreferencesSerializer : Serializer<MessagePreferences> {
    override val defaultValue: MessagePreferences = MessagePreferences.getDefaultInstance()

    override fun readFrom(input: InputStream): MessagePreferences {
        try {
            return MessagePreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: MessagePreferences, output: OutputStream) = t.writeTo(output)
}