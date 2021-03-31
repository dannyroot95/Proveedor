package com.aukde.proveedor.Providers

import com.aukde.proveedor.Models.Token
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId

class TokenProvider {

    private var mDatabase: DatabaseReference? = null

    init {
        mDatabase = FirebaseDatabase.getInstance().reference.child("Tokens")
    }

    fun createToken(id: String){
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            val token = Token(instanceIdResult.token)
            mDatabase!!.child(id).setValue(token)
        }
    }

    fun getToken(idUser: String?): DatabaseReference {
        return mDatabase!!.child(idUser!!)
    }

}