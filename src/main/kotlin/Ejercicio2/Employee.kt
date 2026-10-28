package com.github.apermar1301.Ejercicio2

open class Employee(
    val name: String,
    val id: Int
) {
    constructor(id: Int, name: String) : this(name, id)

    init {
        require(id >= 0) { "ID cannot be negative" }
        require(name.isNotBlank()) { "Name cannot be blank" }
    }

    open fun getDetails(): String = "ID: $id, Nombre: $name"

    open fun getDetail(): String = getDetails()
}
