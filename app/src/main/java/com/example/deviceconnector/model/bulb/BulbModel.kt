package com.example.deviceconnector.model.bulb

data class BulbModel(
    var name : String,
    var bulbcolor : Int = 1,
    var image : Int ,
    var isLive : Boolean = true

)
