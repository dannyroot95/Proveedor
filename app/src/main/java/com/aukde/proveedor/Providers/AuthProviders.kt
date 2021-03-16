package com.aukde.proveedor.Providers

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthProviders {

    var mAuth: FirebaseAuth

    fun login(email: String?, password: String?): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email!!, password!!)
    }

    fun proveedorID(): String {
        return mAuth.currentUser!!.uid }

    fun logout() {
        mAuth.signOut() }

    fun session(): Boolean {
        var existe = false
        if (mAuth.currentUser != null) {
            existe = true }
        return existe
    }

    init {
        mAuth = Firebase.auth
    }

}