package com.aukde.proveedor.Providers

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserProvider {

    private var mDatabase: DatabaseReference? = null

    fun getUserData(id : String) : DatabaseReference {
        mDatabase = FirebaseDatabase.getInstance().reference
        return mDatabase!!
                .child("Usuarios")
                .child("com.aukde.proveedor.Models.ProviderEntity")
                .child(id)
    }

}