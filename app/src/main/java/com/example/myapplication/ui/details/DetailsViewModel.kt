package com.example.myapplication.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.UserDetailsResponse
import com.example.myapplication.data.GithubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: GithubRepository): ViewModel() {

    /**
     * Stream of immutable states representative of the Details UI
     */
    private val _detailsState: MutableStateFlow<DetailsStates> = MutableStateFlow(DetailsStates.Loading)
    val detailsState: StateFlow<DetailsStates> = _detailsState

    fun getDetails(query: String) {
        Log.d("details", "query: ${query}")
        viewModelScope.launch {
            repository.getDetails(query).collect { response ->
                Log.d("details", "insidefCollect: ${response}")
                when(response) {
                    is GithubRepository.GenericResponse.ApiError -> {
                        Log.d("details", "details: api error ${response.error}")
                        _detailsState.update {
                            DetailsStates.DetailsErrorState(response.error ?: "")
                        }
                    }
                    is GithubRepository.GenericResponse.Error -> {
                        Log.d("details", "details: err ${response.error}")
                        _detailsState.update {
                            DetailsStates.DetailsErrorState(response.error ?: "")
                        }
                    }
                    is GithubRepository.GenericResponse.Success -> {
                        Log.d("details", "details: ${response.data}")
                        _detailsState.update {
                            DetailsStates.DetailsSuccessState(response.data)
                        }
                    }
                }
            }
        }

    }
}

sealed class DetailsStates {
    data class DetailsSuccessState(val userDetailsResponse: UserDetailsResponse): DetailsStates()
    data class DetailsErrorState(val error: String): DetailsStates()
    object Loading: DetailsStates()
}