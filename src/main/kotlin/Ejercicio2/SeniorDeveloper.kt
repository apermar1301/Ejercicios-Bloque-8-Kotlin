package com.github.apermar1301.Ejercicio2

class SeniorDeveloper(
    name: String,
    id: Int,
    val yearsOfExperience: Int,
    vararg programmingLanguages: String
) : Developer(name, id, *programmingLanguages) {

    constructor(id: Int, name: String, yearsOfExperience: Int, vararg programmingLanguages: String) :
        this(name, id, yearsOfExperience, *programmingLanguages)

    init {
        require(yearsOfExperience >= 0) { "Years of experience cannot be negative" }
    }

    override fun getDetails(): String {
        return "${super.getDetails()}, Años de experiencia: $yearsOfExperience"
    }

    override fun getDetail(): String = getDetails()
}
