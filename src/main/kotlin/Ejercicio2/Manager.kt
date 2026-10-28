package com.github.apermar1301.Ejercicio2

open class Manager(
    name: String,
    id: Int,
    val department: String
) : Employee(name, id) {

    constructor(id: Int, name: String, department: String) : this(name, id, department)

    override fun getDetails(): String = "${super.getDetails()}, Departamento: $department"

    override fun getDetail(): String = getDetails()
}
