package com.aukde.proveedor.Models

import java.io.Serializable

data class Orders (var numOrder : String = "",
                   var name     : String = "" ,
                   var lastName : String = "",
                   var status   : String = "",
                   var phone    : String = "") : Serializable
