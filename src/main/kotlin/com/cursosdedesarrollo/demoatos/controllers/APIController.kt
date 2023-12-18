package com.cursosdedesarrollo.demoatos.controllers

import com.cursosdedesarrollo.demoatos.dtos.Dato
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*;
import java.util.*

@RestController
@RequestMapping(value = ["/api/v1/dato", "/dato"])
class APIController {
    var listado: MutableList<Dato> = mutableListOf()
    var lastID: Long = 0L

    @GetMapping("/")
    fun index(): MutableList<Dato> {
        return this.listado
    }

    @PostMapping("/")
    fun addDato( @Valid @RequestBody dato:  Dato): Dato {
        lastID++
        dato.id = lastID
        listado.add(dato)
        return dato
    }

    @GetMapping("/clear")
    fun clear(): List<Dato?> {
        this.listado = mutableListOf()
        this.lastID = 0L
        return this.listado
    }

    @GetMapping("/{id}")
    fun showDatoById(@PathVariable("id") id: Long): Dato {
        val d: Optional<Dato> = listado
            .stream()
            .filter { dato: Dato? -> dato?.id == id }
            .findFirst()
        return if (d.isPresent) {
            d.get()
        }else{
            Dato()
        }

    }

    @PutMapping("/{id}")
    fun editDatoById(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dato: Dato
    ): Dato {

        val d: Optional<Dato> =
            listado
                .stream()
                .filter { elemento: Dato? ->
                    elemento?.id == id
                }
                .findFirst()
        if (d.isPresent) {
            dato.id = id
            listado.remove(d.get())
            listado.add(dato)
            return dato
        }else{
            return Dato()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDatoById(@PathVariable id: Long): Dato {
        val d: Optional<Dato> = listado
            .stream()
            .filter {
                elemento: Dato? ->
                    elemento?.id == id
            }
            .findFirst()
        if (d.isPresent) {
            listado.remove(d.get())
            return d.get()
        }else{
            return Dato()
        }
    }
}