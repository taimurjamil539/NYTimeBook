package com.example.nytimesbooksapp.core.common

sealed class Resources<T>(val data:T?=null,val message:String?=null) {
   data class Loading<T>(val nothing: Nothing? = null): Resources<T>()
   data class Success<T>( val resultdata:T?): Resources<T>(data = resultdata)
    data class Error<T>(val errormessage: String?): Resources<T>(message=errormessage)

}