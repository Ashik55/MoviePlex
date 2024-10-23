package com.asshiq.movieplex.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asshiq.movieplex.api.ApiState
import com.asshiq.movieplex.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    // Saving only NewsResponse instead of the Resource wrapper
    val productsResponse = MutableStateFlow<List<String>?>(null)

    // Separate variables to handle loading and error states
    val isLoading = MutableStateFlow(true)

    // Separate variables to handle loading and error states
    val errorMessage = MutableStateFlow<String?>(null)

    init {
        getPost()
    }


    private fun getPost() {
        viewModelScope.launch {
            repository.getPosts(

            ).collect { resource ->
                when (resource) {
                    is ApiState.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is ApiState.Success -> {
                        isLoading.value = false // Stop loading
                        productsResponse.value = resource.data // Set news data
                    }

                    is ApiState.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }
    }

}