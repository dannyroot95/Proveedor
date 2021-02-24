package com.aukde.proveedor.Activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aukde.proveedor.Providers.AuthProviders
import com.aukde.proveedor.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.dmoral.toasty.Toasty

class Main : AppCompatActivity() {

    var edtEmail : TextInputEditText? = null
    var edtPassword : TextInputEditText? = null
    lateinit var mAuth:FirebaseAuth
    var mDatabaseReference:DatabaseReference? = null
    var Database:FirebaseDatabase?=null
    var mSharedPreference: SharedPreferences? = null
    var authProviders: AuthProviders? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppThemePink)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnLogin  = findViewById<Button>(R.id.btnLogin)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        mAuth = FirebaseAuth.getInstance()
        Database= FirebaseDatabase.getInstance()
        mDatabaseReference=Database?.reference!!
        authProviders = AuthProviders()
        mSharedPreference = applicationContext.getSharedPreferences("tipoUsuario", Context.MODE_PRIVATE)

        btnLogin.setOnClickListener(View.OnClickListener {
            login()
        })

    }
    private fun login() {

        var email : String = edtEmail?.text.toString()
        var password : String = edtPassword?.text.toString()
        val editor = mSharedPreference!!.edit()

        if (!email.isEmpty() && !password.isEmpty()){
            if (password.length>=6){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    val intent = Intent(this@Main, Menu::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }

            }
            else{
                Toasty.error(this,"La contraseña debe tener mas de 6 carácteres",Toast.LENGTH_SHORT,false).show()
            }
        }
        else{
            Toasty.error(this,"Complete todos los campos",Toast.LENGTH_SHORT,false).show()
        }
    }
    override fun onStart(){
        super.onStart()
        if(authProviders?.session() == true){
            startActivity(Intent(this@Main, Menu::class.java))
            finish()
        }
    }



}
