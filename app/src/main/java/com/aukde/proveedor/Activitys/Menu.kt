package com.aukde.proveedor.Activitys

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aukde.proveedor.Providers.AuthProviders
import com.aukde.proveedor.Providers.TokenProvider
import com.aukde.proveedor.Providers.UserProvider
import com.aukde.proveedor.R
import com.aukde.proveedor.Utils.ImportDataDialog
import com.aukde.proveedor.Utils.SaveStorageImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView
import es.dmoral.toasty.Toasty
import java.io.File

class Menu : AppCompatActivity() {

    var userData :      UserProvider? = null
    private var id :    String = ""
    var nameProvider  : TextView? = null
    private var nameCategory : TextView? = null
    private var logo : CircleImageView? = null
    private lateinit var saveInStorage : SaveStorageImage

    lateinit var sharedName : SharedPreferences
    private lateinit var path : String
    lateinit var file : File
    val name = "profile.png"
    lateinit var mDialog : ImportDataDialog
    private lateinit var tokenProvider : TokenProvider

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppThemePink)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, Array(2) {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1)
            }
        }

        mDialog       = ImportDataDialog(this)
        saveInStorage = SaveStorageImage()
        sharedName    = getSharedPreferences("keyName", MODE_PRIVATE)
        id            = AuthProviders().userID()
        userData      = UserProvider()
        nameProvider  = findViewById(R.id.nameUser)
        nameCategory  = findViewById(R.id.nameCategory)
        logo          = findViewById(R.id.logo)
        path          = Environment.getExternalStorageDirectory().absolutePath+"/"+"ProviderData"
        file          = File(path)
        tokenProvider = TokenProvider()

        val listButton: CardView = findViewById(R.id.btnListOrders)
        listButton.setOnClickListener{
            startActivity(Intent(this, com.aukde.proveedor.Activitys.OrderList::class.java))
        }

        getBasicInformation()
    }

    private fun getBasicInformation(){

        if(!file.exists()){
            mDialog.startLoading()
            userData!!.getUserData(id).addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("CommitPrefEdits")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        nameProvider!!.text = snapshot.child("nombre empresa").value.toString()
                        val editorName: SharedPreferences.Editor = sharedName.edit()
                        editorName.putString("KeyName", nameProvider!!.text.toString())
                        editorName.apply()

                        val editorCategory: SharedPreferences.Editor = sharedName.edit()
                        nameCategory!!.text = snapshot.child("categoria").value.toString()
                        editorCategory.putString("KeyCategory", nameCategory!!.text.toString())
                        editorCategory.apply()

                        val urlImage: String = snapshot.child("photo").value.toString()
                        Glide.with(this@Menu)
                            .load(urlImage)
                            .into(logo!!)
                        Glide.with(this@Menu)
                            .load(urlImage)
                            .into(object : CustomTarget<Drawable>() {
                                override fun onResourceReady(
                                    resource: Drawable,
                                    transition: Transition<in Drawable>?
                                ) {
                                    val bitmap: Bitmap = (resource as BitmapDrawable).bitmap
                                    saveInStorage.saveImage(bitmap, file, name)
                                    generateToken()
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                }

                            })
                        mDialog.dismissDialog()
                    } else {
                        Toasty.error(this@Menu, "Error de BD", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toasty.error(this@Menu, "Error de servidor", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else{
            sharedData()
        }

    }

    private fun sharedData(){
        val toLoad = File(Environment.getExternalStorageDirectory().absolutePath + "/ProviderData/profile.png")
        nameProvider!!.text = sharedName.getString("KeyName", "")
        nameCategory!!.text  = sharedName.getString("KeyCategory", "")
        Glide.with(this@Menu).load(toLoad).into(logo!!)
    }

   private fun generateToken(){
       tokenProvider.createToken(id)
   }

   private fun quit(){
        val start = Intent(Intent.ACTION_MAIN)
        start.addCategory(Intent.CATEGORY_HOME)
        start.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(start)
    }

    override fun onBackPressed() {
        quit()
    }

}

