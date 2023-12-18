package com.cursosdedesarrollo.demoatos.smoke

import com.cursosdedesarrollo.demoatos.controllers.MainController
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("😱")
class SmokeTests @Autowired constructor(
    // carga el controlador para hacer pruebas de carga
    private val controller: MainController,
    // Cargar el gestor de peticiones a la aplicación Web
    // hace una especie de Mock que carga la aplicación y hace peticioens
    private val mockMvc: MockMvc
){


    @Test
    @DisplayName("Prueba de humo del controlador Principal")
    @Throws(Exception::class)
    fun miRestControllerLoads() {
        assertThat(controller).isNotNull()
    }



    @Test
    @Throws(Exception::class)
    fun shouldReturnHello() {
        // realizar una petición
        mockMvc.perform( // hacer un método get en la petición
            // indicando la ruta de acceso
            get("/")
        ) // imprimir por pantalla el resultado
            .andDo(print())
            // comprobamos que el status es 200 OK
            .andExpect(status().isOk)
            // comprobamos que el contenido es lo que esperamos
            .andExpect(content().string(containsString("Hola Mundo")))
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnHelloBonito() {
        // realizar una petición
        mockMvc.perform( // hacer un método get en la petición
            // indicando la ruta de acceso
            get("/")
        ) // imprimir por pantalla el resultado
            .andDo(print())
            // comprobamos que el status es 200 OK
            .andExpect{
                status().isOk
                content().string(containsString("Hola Mundo"))
            }
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnHelloAunMasBonito() {
        // realizar una petición
        mockMvc
            .get("/")
            .andDo {print()}
            // comprobamos que el status es 200 OK
            .andExpect{
                status { isOk() }
                content {
                    containsString("Hola Mundo")
                }
            }
    }
}