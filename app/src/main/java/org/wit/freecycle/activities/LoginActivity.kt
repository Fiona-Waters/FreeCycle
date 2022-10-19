package org.wit.freecycle.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.freecycle.R
import timber.log.Timber.i
import org.wit.freecycle.databinding.ActivityLoginBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.FreecycleModel
import org.wit.freecycle.models.UserModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        var edit = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        app = application as MainApp
        i("Login Activity Started...")

        // TODO use the code below if allowing a user to update their details
//        if (intent.hasExtra("user_edit")) {
//            edit = true
//            user = intent.extras?.getParcelable("user_edit")!!
//            binding.firstName.setText(user.firstName)
//            binding.lastName.setText(user.lastName)
//            binding.userEmail.setText(user.userEmail)
//            binding.userPassword.setText(user.userPassword)
//
//        }
        binding.btnSubmit.setOnClickListener() {
            // TODO call login function using form details
            val launcherIntent = Intent(this, FreeCycleListActivity::class.java)
            startActivityForResult(launcherIntent, 0)

        }
        binding.btnSignup.setOnClickListener() {
            user.firstName = binding.firstName.text.toString()
            user.lastName = binding.lastName.text.toString()
            user.userEmail = binding.userEmail.text.toString()
            user.userPassword = binding.userPassword.text.toString()
        }


    }

}