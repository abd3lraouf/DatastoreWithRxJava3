package tech.abd3lraouf.work.datastorewithrxjava3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.rxjava3.core.Flowable
import tech.abd3lraouf.work.datastorewithrxjava3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userRepo: UserRepo by lazy { UserRepo(this) }
    private val messageRepo: MessageRepo by lazy { MessageRepo(this) }

    var name = ""
        set(value) {
            field = value
            binding.tvName.text = value
        }

    var age = 0
        set(value) {
            field = value
            binding.tvAge.text = "$value"
        }

    var gender = ""
        set(value) {
            field = value
            binding.tvGender.text = value
        }

    var message = ""
        set(value) {
            field = value
            binding.tvMessage.text = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepo.userName().toLiveData().observe(this, { name = it })
        userRepo.userAge().toLiveData().observe(this, { age = it })
        userRepo.userGender().toLiveData()
            .observe(this, { gender = if (it) "Male" else "Female" })
        messageRepo.message().toLiveData().observe(this, { message = it })

        binding.btnSave.setOnClickListener {
            name = binding.etName.text.toString()
            age = binding.etAge.text.toString().toInt()
            val isMale = binding.switchGender.isChecked
            gender = if (isMale) "Male" else "Female"
            message = binding.etMessage.text.toString()

            userRepo.storeUser(name, age, isMale)
            messageRepo.saveMessage(message)
        }
    }

    private fun <T : Any> Flowable<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this)
}