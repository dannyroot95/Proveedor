package com.aukde.proveedor.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.aukde.proveedor.Providers.AuthProviders
import com.aukde.proveedor.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty

class Main : AppCompatActivity() {

    var edtEmail : TextInputEditText? = null
    var edtPassword : TextInputEditText? = null
   
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppThemePink)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnLogin  = findViewById<Button>(R.id.btnLogin)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)

        btnLogin.setOnClickListener(View.OnClickListener {
            login()
        })

    }

    private fun login() {

        var email : String = edtEmail?.text.toString()
        var password : String = edtPassword?.text.toString()

        if (!email.isEmpty() && !password.isEmpty()){
            if (password.length>=6){
                // terminar de implementar

            }
            else{
                Toasty.error(this,"La contraseña debe tener mas de 6 carácteres",Toast.LENGTH_SHORT,false).show()
            }
        }
        else{
            Toasty.error(this,"Complete todos los campos",Toast.LENGTH_SHORT,false).show()
        }
    }
}