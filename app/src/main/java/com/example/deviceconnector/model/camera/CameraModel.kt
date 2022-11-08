package com.example.deviceconnector.model.camera

data class CameraModel(
    var name : String,
    var recordingTime : String = "9:55:55",
    var recordingDate : String = "9:55:55 Tuesday 20.09.2022",
    var image : Int,
    var isLive : Boolean = true

)
