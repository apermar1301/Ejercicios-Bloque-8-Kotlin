package com.github.apermar1301.Ejercicio5

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests para Ejercicio 5: Clase User con ID Autocalculado")
class UserTest {

    @BeforeEach
    fun setUp() {
        User.resetCounterForTesting()
    }

    @Test
    @DisplayName("El primer objeto User creado debe tener como id el 1, el segundo el 2 y el tercero el 3")
    fun testAutomaticIdAssignment() {
        val user1 = User("Alicia")
        val user2 = User("Bernardo")
        val user3 = User("Carmen")

        assertEquals(1, user1.id, "El primer objeto User debe tener id = 1")
        assertEquals(2, user2.id, "El segundo objeto User debe tener id = 2")
        assertEquals(3, user3.id, "El tercer objeto User debe tener id = 3")
    }

    @Test
    @DisplayName("Los IDs deben ser estrictamente consecutivos y crecientes sin intervención del invocador")
    fun testSequentialIncrementalIds() {
        val users = List(10) { index -> User("Usuario_$index") }

        for (i in users.indices) {
            assertEquals(i + 1, users[i].id, "El usuario en el índice $i debe tener id = ${i + 1}")
        }
    }

    @Test
    @DisplayName("La propiedad id debe ser inmutable (val) y no tener setter público")
    fun testIdIsImmutable() {
        val hasSetIdMethod = User::class.java.declaredMethods.any { it.name == "setId" }
        assertFalse(hasSetIdMethod, "La propiedad 'id' debe ser de solo lectura (val) y no permitir setter público")
    }
}
