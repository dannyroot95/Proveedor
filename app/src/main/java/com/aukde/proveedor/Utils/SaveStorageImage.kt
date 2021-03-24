package com.aukde.proveedor.Utils

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class SaveStorageImage : AppCompatActivity() {

    fun saveImage(image : Bitmap , dir : File , name : String){

        var successDirCreated = false
        if (!dir.exists()) {
            successDirCreated = dir.mkdir()
        }
        if (successDirCreated) {
            val imageFile = File(dir,name)

            try {

                val fOut : OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.PNG,100,fOut)
                fOut.close()

            }catch (e : Exception){
                e.printStackTrace()
            }

        }

    }

}