package com.cursosdedesarrollo.demoatos.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.io.Serializable

data class Dato(
    var id: Long,
    @NotNull
    @NotBlank
    @Size(
        min = 4,
        max = 20,
        message = "Debe tener entre 1 y 20 chars"
    )
    var cadena: String) : Serializable {
        constructor() : this(0,"") {}
}