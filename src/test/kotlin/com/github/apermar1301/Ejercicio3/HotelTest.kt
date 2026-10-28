package com.github.apermar1301.Ejercicio3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.reflect.Modifier

@DisplayName("Tests para Ejercicio 3: Hotel, Reservation y Employee")
class HotelTest {

    @Test
    @DisplayName("Hotel debe almacenar 'name' y 'roomCount' y devolver su información en getHotelInfo()")
    fun testHotelInitializationAndInfo() {
        val hotel = Hotel("Hotel Miramar", 80)

        assertEquals("Hotel Miramar", hotel.name)
        assertEquals(80, hotel.roomCount)

        val info = hotel.getHotelInfo()
        assertTrue(info.contains("Hotel Miramar"), "getHotelInfo() debe contener el nombre")
        assertTrue(info.contains("80"), "getHotelInfo() debe contener el número de habitaciones")
    }

    @Test
    @DisplayName("Reservation debe ser una clase anidada estática (sin 'inner') e instanciable directamente")
    fun testReservationIsStaticNestedClass() {
        // Se debe poder instanciar de forma estática sin referencia a una instancia previa de Hotel
        val reservation = Hotel.Reservation("Lucía Méndez", 204)

        assertEquals("Lucía Méndez", reservation.guestName)
        assertEquals(204, reservation.roomNumber)

        val details = reservation.getReservationDetails()
        assertTrue(details.contains("Lucía Méndez"))
        assertTrue(details.contains("204"))

        // Verificación estructural con Reflection: debe ser static a nivel de bytecode y NO tener referencia this$0 a Hotel
        val reservationClass = Hotel.Reservation::class.java
        assertTrue(Modifier.isStatic(reservationClass.modifiers), "Reservation debe ser una clase anidada estática en la JVM")
        val outerReferenceField = reservationClass.declaredFields.firstOrNull { it.name.startsWith("this$0") }
        assertNull(outerReferenceField, "Reservation no debe poseer puntero outer a la instancia envolvente Hotel")
    }

    @Test
    @DisplayName("Employee debe ser una clase anidada NO estática (inner class) e instanciada desde una instancia de Hotel")
    fun testEmployeeIsInnerClassAndAccessesOuterRoomCount() {
        val hotel1 = Hotel("Resort Paraíso", 250)
        val employee1 = hotel1.Employee("Javier Roca", "Conserje")

        assertEquals("Javier Roca", employee1.employeeName)
        assertEquals("Conserje", employee1.position)

        val details = employee1.getEmployeeDetails()
        assertTrue(details.contains("Javier Roca"))
        assertTrue(details.contains("Conserje"))

        // Acceso al atributo 'roomCount' de la instancia de Hotel externa
        assertEquals(250, employee1.getEmployeeTotalRooms(), "getEmployeeTotalRooms() debe devolver el roomCount del hotel asociado")

        val hotel2 = Hotel("Hotel Boutique", 15)
        val employee2 = hotel2.Employee("Sofía Castro", "Directora")
        assertEquals(15, employee2.getEmployeeTotalRooms())

        // Verificación estructural con Reflection: NO debe ser static y DEBE poseer referencia sintética a Hotel
        val employeeClass = Hotel.Employee::class.java
        assertFalse(Modifier.isStatic(employeeClass.modifiers), "Employee debe ser una inner class (NO estática)")
        val outerReferenceField = employeeClass.declaredFields.firstOrNull { it.name.startsWith("this$0") }
        assertNotNull(outerReferenceField, "Employee debe poseer referencia sintética 'this$0' a Hotel")
    }

    @Test
    @DisplayName("La función main() debe existir y ejecutarse correctamente")
    fun testMainFunctionExecutesWithoutError() {
        assertDoesNotThrow {
            main()
        }
    }
}
