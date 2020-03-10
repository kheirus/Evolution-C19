package com.kouelaa.informe.domain.usecases

import com.kouelaa.informe.data.repository.TodoRepository
import com.kouelaa.informe.domain.entities.Todo

/**
 * Created by kheirus on 2020-02-11.
 */
class GetTodoUseCase(private val todoRepository: TodoRepository) {

    suspend operator fun invoke(id: Int): Todo = todoRepository.getTodo(id)

}