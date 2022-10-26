package org.wit.freecycle.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import org.wit.freecycle.R
import timber.log.Timber.i
import org.wit.freecycle.databinding.ActivityLoginBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.UserModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    var user = UserModel()

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
       // var edit = false
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
            var email = binding.userEmail.text.toString()
            var password = binding.userPassword.text.toString()
            // if user isn't null ?
            // then take that value and store it somewhere that's globally accessible
            var loggedInUser = app.users.login(email, password)
            if (loggedInUser !=null) {
                app.user = loggedInUser
                val launcherIntent = Intent(this, FreeCycleListActivity::class.java)
                startActivityForResult(launcherIntent, 0)
            } else {
                val msg = "Invalid login details, try again"
                Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
            }


        }
        binding.btnSignup.setOnClickListener() {
            user.firstName = binding.firstName.text.toString()
            user.lastName = binding.lastName.text.toString()
            user.userEmail = binding.newUserEmail.text.toString()
            user.userPassword = binding.newUserPassword.text.toString()

            if (user.firstName.isNotEmpty() && user.lastName.isNotEmpty()) {
                app.users.create(user.copy())
                i("sign up Button Pressed: $user")
                setResult(RESULT_OK)
                finish()
            }
            val launcherIntent = Intent(this, FreeCycleListActivity::class.java)
            startActivityForResult(launcherIntent, 0)
        }
    }
}

