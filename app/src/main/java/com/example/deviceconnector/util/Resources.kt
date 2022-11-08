package com.example.deviceconnector.util

data class Resources<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {

    enum class Status {
        SUCCESS,
        CACHE,
        ERROR,
        LOADING
    }

    companion object {

        fun <T> Success(data: T?): Resources<T> {
            return Resources(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> Cache(data: T?) : Resources<T>{
            return Resources(
                Status.CACHE,
                data,
                null
            )
        }

        fun <T> Loading(data: T? = null): Resources<T> {
            return Resources(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> Error(msg: String, data: T? = null): Resources<T> {
            return Resources(
                Status.ERROR,
                data,
                msg
            )
        }
    }
}