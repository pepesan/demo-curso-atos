package com.cursosdedesarrollo.demoatos.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @GetMapping("/")
    fun main(): String {
        return """
            <html>
                <head>
                    <title>Hello World</title>
                </head>
                <body>
                    <h1>Hola Mundo</h1>
                </body>
            </html>
            """.trimIndent()
    }
}