package com.github.apermar1301.Ejercicio5

class User(val name: String = "") {
    val id: Int = ++counter

    companion object {
        private var counter: Int = 0

        // Permite reiniciar el contador si se ejecutan tests aislados
        fun resetCounterForTesting() {
            counter = 0
        }
    }
}
