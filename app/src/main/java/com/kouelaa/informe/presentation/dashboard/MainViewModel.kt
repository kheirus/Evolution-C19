package com.kouelaa.informe.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kouelaa.informe.domain.entities.Todo
import com.kouelaa.informe.domain.usecases.GetTodoUseCase
import com.kouelaa.informe.framework.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


class MainViewModel(
    private val useCase: GetTodoUseCase,
    dispatcher: CoroutineDispatcher

) : BaseViewModel(dispatcher) {

    private val _repositories = MutableLiveData<Todo>()
    val repositories: LiveData<Todo> get() = _repositories

    override fun handleException() {

    }

    fun loadRepos() {
        launch {
            val randomInteger = (1..10).shuffled().first()

            _repositories.value = useCase(randomInteger)
        }
    }
}