package com.github.apermar1301.Ejercicio4

object ConfigurationManager {

    private val configuration: MutableMap<String, String> = mutableMapOf()

    fun set(key: String, value: String) {
        configuration[key] = value
    }

    fun get(key: String): String? {
        return configuration[key]
    }

    fun getAllConfigurations(): String {
        return configuration.entries.joinToString(separator = "\n") { "${it.key} = ${it.value}" }
    }

    // Métodos alias para compatibilidad
    fun addConfiguration(key: String, value: String) {
        set(key, value)
    }

    fun getConfiguration(key: String): String? {
        return get(key)
    }

    fun getAllConfiguration(): String {
        return getAllConfigurations()
    }

    // Método utilitario para limpiar en tests
    fun clear() {
        configuration.clear()
    }
}
