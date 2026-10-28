package com.github.apermar1301.Ejercicio2

open class Developer(
    name: String,
    id: Int,
    vararg val programmingLanguages: String
) : Employee(name, id) {

    constructor(id: Int, name: String, vararg programmingLanguages: String) : this(name, id, *programmingLanguages)

    override fun getDetails(): String {
        val languages = programmingLanguages.joinToString(", ")
        return "${super.getDetails()}, Lenguajes: [$languages]"
    }

    override fun getDetail(): String = getDetails()
}
