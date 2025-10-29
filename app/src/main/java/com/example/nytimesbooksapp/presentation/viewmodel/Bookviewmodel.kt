package com.example.nytimesbooksapp.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytimesbooksapp.data.common.Resources
import com.example.nytimesbooksapp.domain.model.Bookmodel
import com.example.nytimesbooksapp.domain.usecase.Bookusecase
import com.example.nytimesbooksapp.presentation.state.Bookstate
import com.example.nytimesbooksapp.ui.theme.Keyprefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Bookviewmodel @Inject constructor(private val usecase: Bookusecase, private val datastore: Keyprefs): ViewModel() {

  private val _state = mutableStateOf<Bookstate>(Bookstate.Loading)
    val state: State<Bookstate> = _state
  private val _isrefreshing = mutableStateOf(false)
    val isrefreshing: State<Boolean> = _isrefreshing
    val isDarkMood= datastore.isDarkMood.stateIn(viewModelScope, SharingStarted.Lazily,false)
    val lastSyncDate=datastore.lastSyncDate.stateIn(viewModelScope, SharingStarted.Lazily,"Never")
  private val _detailbook= mutableStateOf<Bookmodel?>(null)
    val detailbook:State<Bookmodel?> = _detailbook
    val selecteddate= datastore.selecteddate.stateIn(viewModelScope, SharingStarted.Lazily,"")



    fun setdetailbook(bookdetail: Bookmodel?){
        _detailbook.value=bookdetail
    }


    fun darktoggle(isDark: Boolean){
        viewModelScope.launch {
            datastore.savetheme(isDark)
        }
    }
    fun lastsyncdate(data: String){
        viewModelScope.launch {
            datastore.savelastsync(data)        }

    }




    init {
        getbooks()
    }

    fun getbooks() {
        viewModelScope.launch {
            usecase().collect { result ->
                when (result) {
                    is Resources.Loading -> {
                        _state.value = Bookstate.Loading

                    }

                    is Resources.Success -> {
                        val data = result.data ?: emptyList()
                        if (data.isEmpty()) {
                            _state.value = Bookstate.Empty
                        } else {
                            _state.value = Bookstate.Success(data)
                        }
                    }

                    is Resources.Error -> {

                        val message = if (result.message?.contains("Unable to resolve host") == true)
                            "No Internet Connection"
                        else
                            result.message ?: "Something went wrong"
                        _state.value = Bookstate.Error(message)                    }
                }
            }
        }

    }

    fun refreshscreen() {

        viewModelScope.launch {
            _isrefreshing.value = true
            delay(3000L)
            _isrefreshing.value = false
            getbooks()


            val now = java.text.SimpleDateFormat(
                "hh:mm a",
                java.util.Locale.getDefault()
            ).format(java.util.Date())

            lastsyncdate(now)



        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun dateselected(date: String) {
        viewModelScope.launch {

            datastore.savedate(date)

            try {
                _state.value = Bookstate.Loading
                val result = usecase.dateby(date)
                if (result.isEmpty()) {
                    _state.value = Bookstate.Empty
                } else {
                    _state.value = Bookstate.Success(result)
                }
            } catch (e: Exception) {
                _state.value = Bookstate.Error("Failed to fetch by date: ${e.message}")
            }
        }
    }





    fun searchbooks(qurey: String){
        if (qurey.isBlank()){
            getbooks()
            return
        }
        viewModelScope.launch {
            try {
                _state.value= Bookstate.Loading
                val result=usecase.searchbooks(qurey)
                _state.value= Bookstate.Success(result)

            }catch (e: Exception){
                _state.value= Bookstate.Error("Search Failed:${e.message}")
            }
        }
    }

}