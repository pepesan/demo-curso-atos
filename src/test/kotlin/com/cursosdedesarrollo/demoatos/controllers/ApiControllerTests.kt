package com.cursosdedesarrollo.demoatos.controllers

import com.cursosdedesarrollo.demoatos.dtos.Dato
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("😱 Dato")
class ApiControllerTests {
    @Autowired
    var mockMvc: MockMvc? = null
    @Autowired
    var mapper: ObjectMapper? = null
    var basePath: String = "/api/v1/dato"
    @Autowired
    private val controller: APIController? = null

    @BeforeEach
    @Throws(Exception::class)
    fun clearRestData() {
        // println("limpiando")
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .get("$basePath/clear")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
    }
    @Test
    @Throws(Exception::class)
    fun testListShouldReturnOkResult() {
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .get("$basePath/")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    @Throws(Exception::class)
    fun testListShouldReturnOkResultEmpty() {
        val listadoEsperado: List<Dato> = ArrayList<Dato>()
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .get("$basePath/")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk()) // comprobación del tipo de contenido
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
            ) // comprobación del contenido
            .andExpect(MockMvcResultMatchers.content().json(mapper!!.writeValueAsString(listadoEsperado)))
    }

    @Test
    @Throws(Exception::class)
    fun testAddShouldReturnDato() {
        // println("añadiendo")
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .post("$basePath/")
                .content(asJsonString(Dato(1, "valor")))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(mapper!!.writeValueAsString(Dato(1L, "valor"))))
    }
    fun asJsonString(obj: Any?): String {
        try {
            return ObjectMapper().writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testGetByIDShouldReturnDato() {
        // metemos el dato antes de consultarlo
        testAddShouldReturnDato()
        // println("leyendo")
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .get("$basePath/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(mapper!!.writeValueAsString(Dato(1L, "valor"))))
    }

    @Test
    @Throws(Exception::class)
    fun testGetByIDShouldNotReturnDato() {
        // println("leyendo")
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .get("$basePath/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(mapper!!.writeValueAsString(Dato())))
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateShouldReturnDato() {
        // metemos el dato antes de consultarlo
        testAddShouldReturnDato()
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .put("$basePath/1")
                .content(asJsonString(Dato(1, "valor1")))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(mapper!!.writeValueAsString(Dato(1L, "valor1"))))
    }
    @Test
    @Throws(Exception::class)
    fun testUpdateShouldNotReturnDato() {
        // metemos el dato antes de consultarlo
        // testAddShouldReturnDato()
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .put("$basePath/1")
                .content(asJsonString(Dato(1, "valor1")))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(mapper!!.writeValueAsString(Dato())))
    }

    @Test
    @Throws(Exception::class)
    fun testRemoveByIDShouldReturnDato() {
        // metemos el dato antes de consultarlo
        testAddShouldReturnDato()
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .delete("$basePath/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(mapper!!.writeValueAsString(Dato(1L, "valor"))))
    }
    @Test
    @Throws(Exception::class)
    fun testRemoveByIDShouldNotReturnDato() {
        // metemos el dato antes de consultarlo
        // testAddShouldReturnDato()
        mockMvc!!.perform(
            MockMvcRequestBuilders
                .delete("$basePath/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(mapper!!.writeValueAsString(Dato())))
    }

}