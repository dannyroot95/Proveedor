package com.aukde.proveedor.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.aukde.proveedor.Providers.AuthProviders
import com.aukde.proveedor.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import es.dmoral.toasty.Toasty

class Main : AppCompatActivity() {

    var edtEmail : TextInputEditText? = null
    var edtPassword : TextInputEditText? = null
    var mAuth : AuthProviders? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppThemePink)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        mAuth = AuthProviders()

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
                mAuth?.login(email,password)?.addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this,Menu::class.java))
                        finish()
                        Toasty.success(this,"Logeado",Toast.LENGTH_SHORT).show()
                    } else{
                        Toasty.error(this,"Server error",Toast.LENGTH_SHORT).show()
                    }
                })
            } else{
                Toasty.error(this,"La contraseña debe tener mas de 6 carácteres",Toast.LENGTH_SHORT,false).show()
            }
        } else{
            Toasty.error(this,"Complete todos los campos",Toast.LENGTH_SHORT,false).show()
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth?.session() == true){
            startActivity(Intent(this,Menu::class.java))
        } else{
        }
    }
}