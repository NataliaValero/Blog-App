package com.example.blogapp.core

import java.lang.Exception

sealed class Result{

    // Retornar estados

    object Idle : Result()

    // Resultado de carga
    object Loading : Result()

    // Resultado de exito
    data class Success<T>(val data: T) : Result()

    // Resultado de fallo
    data class Failure(val exception: Exception): Result()
}
