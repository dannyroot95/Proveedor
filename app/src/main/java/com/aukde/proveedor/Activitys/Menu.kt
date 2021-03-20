package com.aukde.proveedor.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.aukde.proveedor.Providers.AuthProviders
import com.aukde.proveedor.Providers.UserProvider
import com.aukde.proveedor.R
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView
import es.dmoral.toasty.Toasty

class Menu : AppCompatActivity() {

    var userData :      UserProvider? = null
    private var id :    String = ""
    var nameProvider  : TextView? = null
    private var nameCategory : TextView? = null
    private var logo : CircleImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppThemePink)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        

        id           = AuthProviders().userID()
        userData     = UserProvider()
        nameProvider = findViewById(R.id.nameUser)
        nameCategory = findViewById(R.id.nameCategory)
        logo         = findViewById(R.id.logo)

        getBasicInformation()

    }

    private fun getBasicInformation(){
        userData!!.getUserData(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    nameProvider!!.text = snapshot.child("nombre empresa").value.toString()
                    nameCategory!!.text = snapshot.child("categoria").value.toString()
                    val urlImage : String = snapshot.child("photo").value.toString()
                    Glide.with(this@Menu)
                            .load(urlImage)
                            .into(logo!!)
                }
                else{
                    Toasty.error(this@Menu,"Error de BD",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toasty.error(this@Menu,"Error de servidor",Toast.LENGTH_SHORT).show()
            }
        })
    }

}

