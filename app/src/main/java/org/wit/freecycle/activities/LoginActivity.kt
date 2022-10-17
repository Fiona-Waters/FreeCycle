package org.wit.freecycle.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.wit.freecycle.R
import timber.log.Timber.i
import org.wit.freecycle.databinding.ActivityLoginBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.UserModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        var edit = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.toolbarAdd.title = title
//        setSupportActionBar(binding.toolbarAdd)
//        getSupportActionBar()?.setDisplayShowTitleEnabled(false)

        if (intent.hasExtra("user_edit")) {
            edit = true
            user = intent.extras?.getParcelable("user_edit")!!
            binding.
        }


        i("Login Activity Started...")
        // get reference to all views
        var userEmail = findViewById<EditText>(R.id.user_email)
        var userPassword = findViewById<EditText>(R.id.user_password)
        var loginButton = findViewById<Button>(R.id.btn_submit)
        var signupButton = findViewById<Button>(R.id.btn_signup)


        // set on-click listener
        loginButton.setOnClickListener {
            val user_email = userEmail.text;
            val user_password = userPassword.text;
            Toast.makeText(this@LoginActivity, user_email, Toast.LENGTH_LONG).show()

            // TODO validate the user_name and password combination
            // and verify the same

        }
    }
}