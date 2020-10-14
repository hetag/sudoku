package com.cdkglobal.sudoku.controller

data class ValidationResult(
    val fileName: String?,
    val code: Int,
    val message: String
)