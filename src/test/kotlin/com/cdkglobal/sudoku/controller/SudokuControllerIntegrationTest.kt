package com.cdkglobal.sudoku.controller

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.jayway.restassured.RestAssured
import com.jayway.restassured.response.Response
import io.kotlintest.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SudokuControllerIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @BeforeEach
    fun baseBefore() {
        RestAssured.port = port
    }

    val testResourcesDir = "src/test/resources"

    val objectMapper: ObjectMapper = ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
        .registerModule(JavaTimeModule())
        .registerModule(KotlinModule())

    @Test
    fun `sudoku controller should return custom error message for sudoku file which does not have the correct size`() {
        val gridNotCorrectSize = "gridNotCorrectSize.txt"
        val gridNotCorrectSizeFile = File("$testResourcesDir/$gridNotCorrectSize")
        val response = uploadFile(gridNotCorrectSizeFile)

        response.statusCode shouldBe HttpStatus.BAD_REQUEST.value()
        response.contentType shouldBe MediaType.APPLICATION_JSON_VALUE
        val body = objectMapper.readTree(response.body.asByteArray())
        body.get("fileName").textValue() shouldBe gridNotCorrectSize
        body.get("code").intValue() shouldBe -1
        body.get("message").textValue() shouldBe "Invalid grid size: 3"
    }

    @Test
    fun `sudoku controller should return one for non-valid sudoku file`() {
        val nonValid = "nonValidFile.txt"
        val nonValidFile = File("$testResourcesDir/$nonValid")
        val response = uploadFile(nonValidFile)

        response.statusCode shouldBe HttpStatus.OK.value()
        response.contentType shouldBe MediaType.APPLICATION_JSON_VALUE
        val body = objectMapper.readTree(response.body.asByteArray())
        body.get("fileName").textValue() shouldBe nonValid
        body.get("code").intValue() shouldBe 1
        body.get("message").textValue() shouldBe SudokuController.INVALID_SUDOKU
    }

    @Test
    fun `sudoku controller should return zero for valid sudoku file`() {
        val valid = "validFile.txt"
        val validFile = File("$testResourcesDir/$valid")
        val response = uploadFile(validFile)

        response.statusCode shouldBe HttpStatus.OK.value()
        response.contentType shouldBe MediaType.APPLICATION_JSON_VALUE
        val body = objectMapper.readTree(response.body.asByteArray())
        body.get("fileName").textValue() shouldBe valid
        body.get("code").intValue() shouldBe 0
        body.get("message").textValue() shouldBe SudokuController.VALID_SUDOKU
    }

    private fun uploadFile(
        file: File,
        apiPath: String = "/sudoku"
    ): Response {
        return RestAssured.given()
            .multiPart(file)
            .`when`()
            .post(apiPath)
            .thenReturn()
    }
}