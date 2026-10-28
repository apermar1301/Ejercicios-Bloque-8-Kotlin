package com.github.apermar1301.Ejercicio4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.reflect.Modifier

@DisplayName("Tests para Ejercicio 4: Singleton ConfigurationManager")
class ConfigurationManagerTest {

    @BeforeEach
    fun setUp() {
        ConfigurationManager.clear()
    }

    @Test
    @DisplayName("ConfigurationManager debe ser un Singleton (objeto único)")
    fun testIsSingleton() {
        val instance1 = ConfigurationManager
        val instance2 = ConfigurationManager
        assertSame(instance1, instance2, "Ambas referencias deben apuntar a la misma instancia Singleton")
    }

    @Test
    @DisplayName("El mapa subyacente de configuración debe ser privado para garantizar la encapsulación")
    fun testInternalMapIsPrivate() {
        val field = ConfigurationManager::class.java.declaredFields.firstOrNull {
            it.name == "configuration"
        }

        assertNotNull(field, "El campo 'configuration' debe existir en ConfigurationManager")
        assertTrue(Modifier.isPrivate(field!!.modifiers), "El mapa 'configuration' debe tener modificador de acceso 'private'")
    }

    @Test
    @DisplayName("Debe permitir almacenar y recuperar pares clave-valor de configuración")
    fun testSetAndGetConfiguration() {
        ConfigurationManager.set("apiUrl", "https://api.empresa.com")
        ConfigurationManager.set("timeout", "5000")

        assertEquals("https://api.empresa.com", ConfigurationManager.get("apiUrl"))
        assertEquals("5000", ConfigurationManager.get("timeout"))
    }

    @Test
    @DisplayName("Debe devolver null al consultar una clave que no ha sido configurada")
    fun testGetNonExistentKeyReturnsNull() {
        assertNull(ConfigurationManager.get("claveInexistente"))
    }

    @Test
    @DisplayName("Debe sobrescribir el valor si se almacena una clave ya existente")
    fun testOverwriteExistingKey() {
        ConfigurationManager.set("env", "staging")
        assertEquals("staging", ConfigurationManager.get("env"))

        ConfigurationManager.set("env", "production")
        assertEquals("production", ConfigurationManager.get("env"))
    }

    @Test
    @DisplayName("getAllConfigurations() debe devolver una cadena con todas las entradas de configuración")
    fun testGetAllConfigurations() {
        ConfigurationManager.set("app.name", "MiApp")
        ConfigurationManager.set("app.version", "1.0.0")

        val allConfigs = ConfigurationManager.getAllConfigurations()

        assertTrue(allConfigs.contains("app.name"), "Debe contener app.name")
        assertTrue(allConfigs.contains("MiApp"), "Debe contener el valor MiApp")
        assertTrue(allConfigs.contains("app.version"), "Debe contener app.version")
        assertTrue(allConfigs.contains("1.0.0"), "Debe contener el valor 1.0.0")
    }

    @Test
    @DisplayName("Compatibilidad de métodos alias (addConfiguration, getConfiguration, getAllConfiguration)")
    fun testAliasMethodsCompatibility() {
        ConfigurationManager.addConfiguration("theme", "dark")
        assertEquals("dark", ConfigurationManager.getConfiguration("theme"))
        assertTrue(ConfigurationManager.getAllConfiguration().contains("theme"))
    }
}
