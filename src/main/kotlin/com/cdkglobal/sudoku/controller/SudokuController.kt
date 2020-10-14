package com.cdkglobal.sudoku.controller

import com.cdkglobal.sudoku.domain.SudokuService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader

@RestController
class SudokuController(
    val sudokuService: SudokuService
) {

    companion object {
        const val ROW_DELIMITER = "\n"
        const val COLUMN_DELIMITER = ","
        const val GRID_SIZE = 9
        const val VALID_SUDOKU = "VALID SUDOKU"
        const val INVALID_SUDOKU = "INVALID SUDOKU"
    }

    @PostMapping("/sudoku")
    @ResponseBody
    fun verifySudoku(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<ValidationResult> {
        val lines = file.inputStream.bufferedReader().use(BufferedReader::readText).split(ROW_DELIMITER)
        val list: MutableList<IntArray> = mutableListOf()
        lines.forEach { line ->
            val row = line.split(COLUMN_DELIMITER).map { it.trim().toInt() }.toIntArray()
            list.add(row)
        }
        val grid = list.toTypedArray()

        if (grid.size != GRID_SIZE) {
            return ResponseEntity.badRequest().body(
                ValidationResult(
                    code = -1,
                    fileName = file.originalFilename,
                    message = "Invalid grid size: ${grid.size}"
                )
            )
        }

        val validationResult = when (sudokuService.validateSudoku(grid)) {
            0 -> ValidationResult(code = 0, fileName = file.originalFilename, message = VALID_SUDOKU)
            else -> ValidationResult(code = 1, fileName = file.originalFilename, message = INVALID_SUDOKU)
        }

        return ResponseEntity.ok(validationResult)
    }
}
