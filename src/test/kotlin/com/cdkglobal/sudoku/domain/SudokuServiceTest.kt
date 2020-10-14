package com.cdkglobal.sudoku.domain

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SudokuServiceTest {

    @Autowired
    private lateinit var sudokuService: SudokuService

    @Test
    fun `verify that validateSudoku should return zero for valid grid`() {
        val validGrid = arrayOf(
            intArrayOf(8, 2, 5, 4, 7, 1, 3, 9, 6),
            intArrayOf(1, 9, 4, 3, 2, 6, 5, 7, 8),
            intArrayOf(3, 7, 6, 9, 8, 5, 2, 4, 1),
            intArrayOf(5, 1, 9, 7, 4, 3, 8, 6, 2),
            intArrayOf(6, 3, 2, 5, 9, 8, 4, 1, 7),
            intArrayOf(4, 8, 7, 6, 1, 2, 9, 3, 5),
            intArrayOf(2, 6, 3, 1, 5, 9, 7, 8, 4),
            intArrayOf(9, 4, 8, 2, 6, 7, 1, 5, 3),
            intArrayOf(7, 5, 1, 8, 3, 4, 6, 2, 9)
        )
        sudokuService.validateSudoku(validGrid) shouldBe 0
    }

    @Test
    fun `verify that validateSudoku should return one for invalid grid`() {
        val nonValidGrid = arrayOf(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        )
        sudokuService.validateSudoku(nonValidGrid) shouldBe 1
    }

    @Test
    fun `verify that validateSudoku should return one when there are numbers greater than 9 present`() {
        val nonValidGrid = arrayOf(
            intArrayOf(100, 2, 5, 4, 7, 1, 3, 9, 6),
            intArrayOf(1, 9, 4, 3, 2, 6, 5, 7, 8),
            intArrayOf(3, 7, 6, 9, 8, 5, 2, 4, 1),
            intArrayOf(5, 1, 9, 7, 4, 3, 8, 6, 2),
            intArrayOf(6, 3, 2, 5, 9, 8, 4, 1, 7),
            intArrayOf(4, 8, 7, 6, 1, 2, 9, 3, 5),
            intArrayOf(2, 6, 3, 1, 5, 9, 7, 8, 4),
            intArrayOf(9, 4, 8, 2, 6, 7, 1, 5, 3),
            intArrayOf(7, 5, 1, 8, 3, 4, 6, 2, 9)
        )
        sudokuService.validateSudoku(nonValidGrid) shouldBe 1
    }

    @Test
    fun `verify that validateSudoku should return one when there are negative numbers present`() {
        val nonValidGrid = arrayOf(
            intArrayOf(-1, 2, 5, 4, 7, 1, 3, 9, 6),
            intArrayOf(1, 9, 4, 3, 2, 6, 5, 7, 8),
            intArrayOf(3, 7, 6, 9, 8, 5, 2, 4, 1),
            intArrayOf(5, 1, 9, 7, 4, 3, 8, 6, 2),
            intArrayOf(6, 3, 2, 5, 9, 8, 4, 1, 7),
            intArrayOf(4, 8, 7, 6, 1, 2, 9, 3, 5),
            intArrayOf(2, 6, 3, 1, 5, 9, 7, 8, 4),
            intArrayOf(9, 4, 8, 2, 6, 7, 1, 5, 3),
            intArrayOf(7, 5, 1, 8, 3, 4, 6, 2, 9)
        )
        sudokuService.validateSudoku(nonValidGrid) shouldBe 1
    }

    @Test
    fun `verify that validateSudoku should return one when one of the rows is shorter than expected`() {
        val nonValidGrid = arrayOf(
            intArrayOf(8, 2, 5, 4, 7, 1, 3, 9),
            intArrayOf(1, 9, 4, 3, 2, 6, 5, 7, 8),
            intArrayOf(3, 7, 6, 9, 8, 5, 2, 4, 1),
            intArrayOf(5, 1, 9, 7, 4, 3, 8, 6, 2),
            intArrayOf(6, 3, 2, 5, 9, 8, 4, 1, 7),
            intArrayOf(4, 8, 7, 6, 1, 2, 9, 3, 5),
            intArrayOf(2, 6, 3, 1, 5, 9, 7, 8, 4),
            intArrayOf(9, 4, 8, 2, 6, 7, 1, 5, 3),
            intArrayOf(7, 5, 1, 8, 3, 4, 6, 2, 9)
        )
        sudokuService.validateSudoku(nonValidGrid) shouldBe 1
    }
}