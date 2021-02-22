package com.aukde.proveedor.Providers

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthProviders {

     var mAuth: FirebaseAuth

    init {
        mAuth = FirebaseAuth.getInstance()
    }

    fun login(email: String?, password: String?): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email!!, password!!)
    }

    val id: String
        get() = mAuth.currentUser!!.uid

    fun logout() {
        mAuth.signOut()
    }


}