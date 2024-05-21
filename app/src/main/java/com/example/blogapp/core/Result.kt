package com.example.blogapp.core

import java.lang.Exception


sealed class Result<out T>{

    // Retornar estados

    // Resultado de carga
    class Loading<out T>: Result<T>()

    // Resultado de exito
    data class Success<out T>(val data: T): Result<T>()

    // Resultado de fallo
    data class Failure(val exception: Exception): Result<Nothing>()
}