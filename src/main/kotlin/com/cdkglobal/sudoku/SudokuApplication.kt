package com.cdkglobal.sudoku

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class SudokuApplication

fun main(args: Array<String>) {
	runApplication<SudokuApplication>(*args)
}
