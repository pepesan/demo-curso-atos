package com.cursosdedesarrollo.demoatos.controllers

import com.cursosdedesarrollo.demoatos.dtos.Dato
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import java.util.*

@RestController
@RequestMapping(value = ["/api/v1/advice", "/advice"])
class APIResponseEntityAdviceController {
    var listado: MutableList<Dato> = mutableListOf()
    var lastID: Long = 0L

    @GetMapping("/")
    fun index(): ResponseEntity<MutableList<Dato>> {
        //return ResponseEntity.ok(this.listado)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return ResponseEntity<MutableList<Dato>>(
            this.listado,
            headers,
            HttpStatus.OK
        )
    }

    @PostMapping("/")
    fun addDato( @Valid @RequestBody dato:  Dato): ResponseEntity<Dato> {
        lastID++
        dato.id = lastID
        listado.add(dato)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return ResponseEntity<Dato>(
            dato,
            headers,
            HttpStatus.OK
        )
    }

    @GetMapping("/clear")
    fun clear(): ResponseEntity<MutableList<Dato>> {
        this.listado = mutableListOf()
        this.lastID = 0L
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return ResponseEntity<MutableList<Dato>>(

            this.listado,
            headers,
            HttpStatus.OK
        )
    }

    @GetMapping("/{id}")
    fun showDatoById(@PathVariable("id") id: Long): ResponseEntity<Dato> {
        val d: Dato = listado
            .stream()
            .filter { dato: Dato? -> dato?.id == id }
            .findFirst()
            .orElseThrow<ResourceNotFoundException> {
                ResourceNotFoundException(
                    "Not found with id = $id"
                )
            }
        val headers= HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return ResponseEntity<Dato>(
                d,
                headers,
                HttpStatus.OK)

    }

    @PutMapping("/{id}")
    fun editDatoById(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dato: Dato
    ): ResponseEntity<Dato> {

        val d: Optional<Dato> =
            listado
                .stream()
                .filter { elemento: Dato? ->
                    elemento?.id == id
                }
                .findFirst()
        val headers= HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        if (d.isPresent) {
            dato.id = id
            listado.remove(d.get())
            listado.add(dato)
            return ResponseEntity<Dato>(
                dato,
                headers,
                HttpStatus.OK)
        }else{
            return ResponseEntity<Dato>(
                Dato(),
                headers,
                HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDatoById(@PathVariable id: Long): ResponseEntity<Dato> {
        val d: Optional<Dato> = listado
            .stream()
            .filter {
                elemento: Dato? ->
                    elemento?.id == id
            }
            .findFirst()
        val headers= HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        if (d.isPresent) {
            listado.remove(d.get())
            return ResponseEntity<Dato>(
                d.get(),
                headers,
                HttpStatus.OK)
        }else{
            return ResponseEntity<Dato>(
                Dato(),
                headers,
                HttpStatus.NOT_FOUND)
        }
    }
}