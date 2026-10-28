package com.github.apermar1301.Ejercicio3

class Hotel(
    val name: String,
    val roomCount: Int
) {
    fun getHotelInfo(): String = "Hotel: $name, Habitaciones: $roomCount"

    // Clase anidada estática (sin 'inner')
    class Reservation(
        val guestName: String,
        val roomNumber: Int
    ) {
        fun getReservationDetails(): String = "Huésped: $guestName, Habitación: $roomNumber"

        fun getReservationInfo(): String = getReservationDetails()
    }

    // Clase anidada NO estática (con 'inner', vinculada a la instancia de Hotel)
    inner class Employee(
        val employeeName: String,
        val position: String
    ) {
        fun getEmployeeDetails(): String = "Empleado: $employeeName, Cargo: $position"

        fun getEmployeeTotalRooms(): Int = roomCount

        fun getEmployeeDetail(): String = getEmployeeDetails()
    }
}

fun main() {
    val hotel = Hotel("Gran Hotel Central", 120)
    println(hotel.getHotelInfo())

    // Instancia de clase anidada estática
    val reservation = Hotel.Reservation("Carlos Gómez", 101)
    println(reservation.getReservationDetails())

    // Instancia de inner class asociada a la instancia de Hotel
    val employee = hotel.Employee("Laura Pérez", "Recepcionista")
    println(employee.getEmployeeDetails())
    println("Habitaciones del hotel donde trabaja: ${employee.getEmployeeTotalRooms()}")
}
