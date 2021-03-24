package com.aukde.proveedor.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.aukde.proveedor.R

class ImportDataDialog (myActivity:Activity) {
    private var activity: Activity? = myActivity
    private lateinit var dialog: AlertDialog


    @SuppressLint("InflateParams")
    fun startLoading() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.import_data_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }

}