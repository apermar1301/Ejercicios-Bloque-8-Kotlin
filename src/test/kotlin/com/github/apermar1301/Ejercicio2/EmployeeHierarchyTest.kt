package com.github.apermar1301.Ejercicio2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Tests para Ejercicio 2: Jerarquía de Empleados")
class EmployeeHierarchyTest {

    @Test
    @DisplayName("Employee debe almacenar 'name' y 'id' y devolverlos en getDetails()")
    fun testEmployeeBaseClass() {
        val employee = Employee("Carlos Pérez", 101)

        assertEquals("Carlos Pérez", employee.name)
        assertEquals(101, employee.id)

        val details = employee.getDetails()
        assertTrue(details.contains("101"), "getDetails() debe incluir el ID")
        assertTrue(details.contains("Carlos Pérez"), "getDetails() debe incluir el nombre")
    }

    @Test
    @DisplayName("Manager debe heredar de Employee y añadir 'department' en getDetails()")
    fun testManagerInheritanceAndDetails() {
        val manager = Manager("Beatriz Morales", 201, "Ingeniería")

        assertTrue(manager is Employee, "Manager debe ser una subclase de Employee")
        assertEquals("Beatriz Morales", manager.name)
        assertEquals(201, manager.id)
        assertEquals("Ingeniería", manager.department)

        val details = manager.getDetails()
        assertTrue(details.contains("201"), "getDetails() debe incluir el ID")
        assertTrue(details.contains("Beatriz Morales"), "getDetails() debe incluir el nombre")
        assertTrue(details.contains("Ingeniería"), "getDetails() debe incluir el departamento")
    }

    @Test
    @DisplayName("Developer debe heredar de Employee y admitir vararg de lenguajes de programación")
    fun testDeveloperInheritanceAndVarargs() {
        val dev = Developer("David Sanz", 301, "Kotlin", "Java", "Python")

        assertTrue(dev is Employee, "Developer debe ser una subclase de Employee")
        assertEquals("David Sanz", dev.name)
        assertEquals(301, dev.id)
        assertArrayEquals(arrayOf("Kotlin", "Java", "Python"), dev.programmingLanguages)

        val details = dev.getDetails()
        assertTrue(details.contains("301"))
        assertTrue(details.contains("David Sanz"))
        assertTrue(details.contains("Kotlin"))
        assertTrue(details.contains("Java"))
        assertTrue(details.contains("Python"))
    }

    @Test
    @DisplayName("Developer debe funcionar correctamente cuando no se pasan lenguajes o se pasa uno solo")
    fun testDeveloperWithSingleOrEmptyLanguages() {
        val devEmpty = Developer("Laura Díaz", 302)
        assertEquals(0, devEmpty.programmingLanguages.size)
        assertTrue(devEmpty.getDetails().contains("Laura Díaz"))

        val devSingle = Developer("Andrés Cruz", 303, "Rust")
        assertEquals(1, devSingle.programmingLanguages.size)
        assertTrue(devSingle.getDetails().contains("Rust"))
    }

    @Test
    @DisplayName("SeniorDeveloper debe representar el 3er nivel de la jerarquía de herencia")
    fun testHierarchyThreeLevels() {
        val seniorDev = SeniorDeveloper("Fernando Gil", 401, 10, "Kotlin", "TypeScript")

        // Nivel 3 -> hereda de Developer (Nivel 2) -> hereda de Employee (Nivel 1)
        assertTrue(seniorDev is Developer, "SeniorDeveloper debe heredar de Developer")
        assertTrue(seniorDev is Employee, "SeniorDeveloper debe ser indirectamente un Employee")

        // Verificación estructural con Reflection
        assertEquals(Developer::class.java, SeniorDeveloper::class.java.superclass, "El supertipo directo de SeniorDeveloper debe ser Developer")
        assertEquals(Employee::class.java, Developer::class.java.superclass, "El supertipo directo de Developer debe ser Employee")

        val details = seniorDev.getDetails()
        assertTrue(details.contains("Fernando Gil"))
        assertTrue(details.contains("401"))
        assertTrue(details.contains("Kotlin"))
        assertTrue(details.contains("TypeScript"))
        assertTrue(details.contains("10"))
    }

    @Test
    @DisplayName("Polimorfismo: una colección de Employee debe despachar getDetails() correspondiente a cada subclase")
    fun testPolymorphicDetailsDispatch() {
        val employees: List<Employee> = listOf(
            Employee("Genérico", 1),
            Manager("Jefa", 2, "Recursos Humanos"),
            Developer("Programador", 3, "Kotlin"),
            SeniorDeveloper("Senior", 4, 8, "Kotlin", "Go")
        )

        val detailsList = employees.map { it.getDetails() }

        assertEquals(4, detailsList.size)
        assertTrue(detailsList[0].contains("Genérico"))
        assertTrue(detailsList[1].contains("Recursos Humanos"))
        assertTrue(detailsList[2].contains("Kotlin"))
        assertTrue(detailsList[3].contains("8"))
    }

    @Test
    @DisplayName("Validación de nombre no en blanco y id no negativo")
    fun testEmployeeValidations() {
        assertThrows<IllegalArgumentException> {
            Employee("", 1)
        }
        assertThrows<IllegalArgumentException> {
            Employee("   ", 1)
        }
        assertThrows<IllegalArgumentException> {
            Employee("Test", -1)
        }
    }
}
