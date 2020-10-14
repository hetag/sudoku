package com.cdkglobal.sudoku.domain

import com.cdkglobal.sudoku.controller.SudokuController.Companion.GRID_SIZE
import org.springframework.stereotype.Service
import java.util.HashSet

@Service
class SudokuService {

    fun validateSudoku(grid: Array<IntArray>): Int {
        // Checking the rows and columns.
        for (i in 0..8) {
            val rowResult: Int = validateRow(i, grid)
            val columnResult: Int = validateColumn(i, grid)
            if (rowResult < 1 || columnResult < 1) {
                return 1
            }
        }
        val subSquaresResult: Int = validateSubSquares(grid)
        // if any one the subsquares is invalid, then the board is invalid.
        return if (subSquaresResult < 1) {
            1
        } else {
            0
        }
    }

    private fun validateRow(rowIndex: Int, grid: Array<IntArray>): Int {
        val row = grid[rowIndex]
        if (row.size != GRID_SIZE) {
            return -1
        }
        val set: MutableSet<Int> = HashSet()
        for (value in row) {
            if (value < 0 || value > 9) {
                return -1
            } else if (value != 0) {
                if (!set.add(value)) {
                    return 0
                }
            }
        }
        return 1
    }

    private fun validateColumn(col: Int, grid: Array<IntArray>): Int {
        val set: MutableSet<Int> = HashSet()
        for (i in 0..8) {
            if (grid[i][col] < 0 || grid[i][col] > 9) {
                return 1
            } else if (grid[i][col] != 0) {
                if (!set.add(grid[i][col])) {
                    return 0
                }
            }
        }
        return 1
    }

    private fun validateSubSquares(grid: Array<IntArray>): Int {
        var row = 0
        while (row < 9) {
            var col = 0
            while (col < 9) {
                val set: MutableSet<Int> = HashSet()
                for (r in row until row + 3) {
                    for (c in col until col + 3) {
                        // Checking for values outside 0 and 9;
                        if (grid[r][c] < 0 || grid[r][c] > 9) {
                            return -1
                        } else if (grid[r][c] != 0) {
                            if (!set.add(grid[r][c])) {
                                return 0
                            }
                        }
                    }
                }
                col += 3
            }
            row += 3
        }
        return 1
    }
}