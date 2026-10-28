# Contexto y Análisis del Proyecto: Ejercicios Bloque 8 (Kotlin)

Este documento recopila los enunciados oficiales, el análisis de discrepancias del código actual respecto a dichos requisitos y las soluciones propuestas para alcanzar una implementación idiomática y correcta.

---

## 1. Enunciados Oficiales

### Ejercicio 1: BankAccount
Define una clase en Kotlin llamada `BankAccount` que tenga las siguientes propiedades y métodos:
* `accountHolder`: un valor no nulo de tipo `String` que representa el nombre del titular de la cuenta.
* `balance`: un valor de tipo `Double` que representa el saldo de la cuenta y debe ser mayor o igual a 0. Si se intenta asignar un valor negativo, el setter debe lanzar una excepción `IllegalArgumentException` con el mensaje `Balance must be non-negative`.
* `transactionHistory`: una lista de cadenas (`MutableList<String>`) que almacena un historial de transacciones realizadas sobre la cuenta. Desde fuera, esta propiedad solo debe ser accesible para consulta, en forma de `List<String>`.
* `deposit(amount: Double)`: Método que permite depositar una cantidad de dinero en la cuenta. Este método debe validar que el monto a depositar sea positivo, lanzando una excepción `IllegalArgumentException` en caso contrario, actualizar el saldo y registrar la transacción en `transactionHistory`.
* `withdraw(amount: Double)`: Método que permite retirar una cantidad de dinero de la cuenta. Este método debe validar que el monto a retirar sea positivo y que el saldo sea suficiente, lanzando una excepción `IllegalArgumentException` en caso contrario. Debe actualizar el saldo y registrar la transacción en `transactionHistory`.

### Ejercicio 2: Jerarquía de Empleados
Diseña una jerarquía de clases para representar empleados en una empresa. La jerarquía debe incluir al menos tres niveles de clases, utilizando herencia para representar diferentes tipos de empleados:
* `Employee` (Clase base). Propiedades y métodos:
  * `name`: un valor no nulo de tipo `String` que representa el nombre del empleado.
  * `id`: un valor de tipo `Int` que representa el identificador del empleado.
  * `getDetails()`: un método que devuelve una cadena con el nombre y el id del empleado.
* `Manager` (Subclase de `Employee`). Propiedades y métodos:
  * `department`: un valor no nulo de tipo `String` que representa el departamento que gestiona el gerente.
  * `getDetails()`: Sobrescribe el método `getDetails()` de la clase base para incluir el departamento en la cadena devuelta.
* `Developer` (Subclase de `Employee`). Propiedades y métodos:
  * `programmingLanguages`: un `vararg` de cadenas que representa los lenguajes de programación que conoce el desarrollador.
  * `getDetails()`: Sobrescribe el método `getDetails()` de la clase base para incluir los lenguajes de programación en la cadena devuelta.

### Ejercicio 3: Sistema de Gestión de Reservas de Hotel
Define un sistema para gestionar reservas en un hotel. El sistema debe incluir las siguientes clases:
* `Hotel` (Clase principal). Propiedades y métodos:
  * `name`: un valor no nulo de tipo `String` que representa el nombre del hotel.
  * `roomCount`: un valor de tipo `Int` que representa el número total de habitaciones en el hotel.
  * `getHotelInfo()`: Un método que devuelve la información del hotel, incluyendo el nombre y el número de habitaciones.
* `Reservation` (Clase anidada estática dentro de `Hotel`). Propiedades y métodos:
  * `guestName`: un valor no nulo de tipo `String` que representa el nombre del huésped.
  * `roomNumber`: un valor de tipo `Int` que representa el número de habitación reservada.
  * `getReservationDetails()`: Un método que devuelve los detalles de la reserva, incluyendo el nombre del huésped y el número de habitación.
* `Employee` (Clase anidada NO estática dentro de `Hotel`). Propiedades y métodos:
  * `employeeName`: un valor no nulo de tipo `String` que representa el nombre del empleado.
  * `position`: un valor de tipo `String` que representa el cargo del empleado en el hotel.
  * `getEmployeeDetails()`: Un método que devuelve los detalles del empleado, incluyendo el nombre y el cargo.
  * `getEmployeeTotalRooms()`: Un método que devuelve el número total de habitaciones del hotel en el que trabaja el empleado.
* Define una función `main()` que llame a los métodos de las clases descritas.

### Ejercicio 4: Singleton ConfigurationManager
Define un singleton `ConfigurationManager` que administre la configuración global de una aplicación, para lo que usará un mapa privado `MutableMap<String, String>`, que podemos crear con la función `mutableMapOf()`. El `ConfigurationManager` debe permitir:
* Almacenar un par clave-valor de configuración.
* Obtener el valor asociado a una clave de configuración.
* Obtener una cadena que represente el estado actual de todas las configuraciones almacenadas utilizando la propiedad `entries` del mapa.

### Ejercicio 5: Clase User con ID Autocalculado
Define una clase `User` de manera que el valor de su propiedad `id` se calcule automáticamente, de manera que el primer objeto que se cree de la clase `User` tenga como `id` el 1, el segundo objeto el 2, y así sucesivamente, sin que el código que instancia la clase `User` tenga que hacer ningún cálculo para ello.

---

## 2. Análisis Detallado y Diagnóstico del Código Actual

### Ejercicio 1: BankAccount (`src/main/kotlin/Ejercicio1/BankAccount.kt`)
* **Estado:** ⚠️ Requiere correcciones.
* **Discrepancias encontradas:**
  1. **Falta setter personalizado:** `balance` está declarado como `var` en el constructor primario y solo se evalúa en `init`. No hay un setter explícito `set(value)` que intercepte asignaciones directas posteriores a la instanciación (`cuenta.balance = -10.0`).
  2. **Violación de encapsulamiento:** `transactionHistory` está expuesto públicamente como `MutableList<String>`, permitiendo mutaciones externas. Además, existe un método vacío residual `getTheTransactionHistory(): List<String>? { return null }`.
  3. **Validación de importe positivo:** `deposit` y `withdraw` usan `>= 0.0` en vez de `> 0.0` (un depósito o retiro de `0.0` no es positivo).
  4. **Mensaje de error incoherente:** En `withdraw`, la condición `require(amount <= balance)` lanza el mensaje `"Amount must be less than zero"`, cuando el motivo real es saldo insuficiente.

### Ejercicio 2: Jerarquía de Empleados (`src/main/kotlin/Ejercicio2/`)
* **Estado:** ⚠️ Requiere correcciones.
* **Discrepancias encontradas:**
  1. **Niveles de herencia insuficientes:** Solo existen 2 niveles (`Employee` -> `Manager` y `Developer`). El enunciado exige al menos tres niveles de clases.
  2. **Tipo de `id`:** Se ha usado `Long` en lugar de `Int`.
  3. **Nombre del método:** Se nombró `getDetail()` (singular) en lugar de `getDetails()` (plural).
  4. **Presentación de datos:** En `Developer`, se imprime `${programmingLanguages.asList()}` en lugar de un formateo limpio (`joinToString`).

### Ejercicio 3: Hotel, Reservation y Employee (`src/main/kotlin/Ejercicio3/`)
* **Estado:** ❌ Error conceptual crítico.
* **Discrepancias encontradas:**
  1. **Inversión conceptual de anidamiento:**
     * `Reservation` debía ser **estática** (en Kotlin: `class Reservation` anidada sin `inner`), pero se implementó como `inner class Reservation`.
     * `Employee` debía ser **NO estática** (en Kotlin: `inner class Employee`), pero se implementó como `class Employee` estática.
  2. **Parámetro artificial `workedRooms`:** Debido a que `Employee` no era `inner`, no tenía acceso al atributo `roomCount` de `Hotel`. Para compensarlo, se añadió indebidamente un parámetro `val workedRooms: Int` a su constructor.
  3. **Nombres de métodos discrepantes:**
     * `getReservationInfo()` en lugar de `getReservationDetails()`.
     * `getEmployeeDetail()` en lugar de `getEmployeeDetails()`.
  4. **Falta la función `main()`:** No se incluye la función `main()` que prueba las clases requeridas.
  5. **Archivo huérfano:** Existe un archivo vacío `Reservation.kt` en la carpeta `Ejercicio3`.

### Ejercicio 4: ConfigurationManager (`src/main/kotlin/Ejercicio4/ConfigurationManager.kt`)
* **Estado:** ⚠️ Requiere correcciones menores.
* **Discrepancias encontradas:**
  1. **Visibilidad del mapa:** `val configuration: MutableMap<String, String>` es pública por omisión. Debe ser explícitamente `private`.
  2. **Estilo:** Contiene puntos y coma (`;`) residuales no idiomáticos en Kotlin.

### Ejercicio 5: User (`src/main/kotlin/Ejercicio5/User.kt`)
* **Estado:** 🟡 Casi correcto.
* **Discrepancias encontradas:**
  1. **Mutabilidad del ID:** `var id: Long` permite modificar el identificador una vez creado el objeto. Debe ser `val id`.
  2. **Tipo de ID:** El tipo `Long` puede simplificarse a `Int` para coincidir con la convención estándar del enunciado.

---

## 3. Código Propuesto y Recomendado

### Ejercicio 1: BankAccount.kt
```kotlin
package com.github.apermar1301.Ejercicio1

class BankAccount(
    val accountHolder: String,
    initialBalance: Double = 0.0
) {
    var balance: Double = initialBalance
        set(value) {
            require(value >= 0.0) { "Balance must be non-negative" }
            field = value
        }

    init {
        require(initialBalance >= 0.0) { "Balance must be non-negative" }
    }

    private val _transactionHistory: MutableList<String> = mutableListOf()
    val transactionHistory: List<String>
        get() = _transactionHistory

    fun deposit(amount: Double) {
        require(amount > 0.0) { "Amount must be positive" }
        balance += amount
        _transactionHistory.add("Deposit: +$amount")
    }

    fun withdraw(amount: Double) {
        require(amount > 0.0) { "Amount must be positive" }
        require(amount <= balance) { "Insufficient balance" }
        balance -= amount
        _transactionHistory.add("Withdrawal: -$amount")
    }
}
```

### Ejercicio 2: Jerarquía de Empleados
#### Employee.kt (Nivel 1)
```kotlin
package com.github.apermar1301.Ejercicio2

open class Employee(
    val name: String,
    val id: Int
) {
    init {
        require(id >= 0) { "ID cannot be negative" }
        require(name.isNotBlank()) { "Name cannot be blank" }
    }

    open fun getDetails(): String = "ID: $id, Nombre: $name"
}
```

#### Manager.kt (Nivel 2)
```kotlin
package com.github.apermar1301.Ejercicio2

open class Manager(
    name: String,
    id: Int,
    val department: String
) : Employee(name, id) {

    override fun getDetails(): String = "${super.getDetails()}, Departamento: $department"
}
```

#### Developer.kt (Nivel 2)
```kotlin
package com.github.apermar1301.Ejercicio2

open class Developer(
    name: String,
    id: Int,
    vararg val programmingLanguages: String
) : Employee(name, id) {

    override fun getDetails(): String {
        val languages = programmingLanguages.joinToString(", ")
        return "${super.getDetails()}, Lenguajes: [$languages]"
    }
}
```

#### SeniorDeveloper.kt (Nivel 3)
```kotlin
package com.github.apermar1301.Ejercicio2

class SeniorDeveloper(
    name: String,
    id: Int,
    val yearsOfExperience: Int,
    vararg programmingLanguages: String
) : Developer(name, id, *programmingLanguages) {

    override fun getDetails(): String {
        return "${super.getDetails()}, Años de experiencia: $yearsOfExperience"
    }
}
```

### Ejercicio 3: Hotel.kt
```kotlin
package com.github.apermar1301.Ejercicio3

class Hotel(
    val name: String,
    val roomCount: Int
) {
    fun getHotelInfo(): String = "Hotel: $name, Habitaciones: $roomCount"

    // Anidada estática (sin inner)
    class Reservation(
        val guestName: String,
        val roomNumber: Int
    ) {
        fun getReservationDetails(): String = "Huésped: $guestName, Habitación: $roomNumber"
    }

    // Anidada NO estática / interna (con inner, vinculada a la instancia de Hotel)
    inner class Employee(
        val employeeName: String,
        val position: String
    ) {
        fun getEmployeeDetails(): String = "Empleado: $employeeName, Cargo: $position"

        fun getEmployeeTotalRooms(): Int = roomCount
    }
}

fun main() {
    val hotel = Hotel("Gran Hotel Central", 120)
    println(hotel.getHotelInfo())

    // Instancia de clase estática anidada
    val reservation = Hotel.Reservation("Carlos Gómez", 101)
    println(reservation.getReservationDetails())

    // Instancia de inner class asociada al hotel
    val employee = hotel.Employee("Laura Pérez", "Recepcionista")
    println(employee.getEmployeeDetails())
    println("Habitaciones del hotel donde trabaja: ${employee.getEmployeeTotalRooms()}")
}
```

### Ejercicio 4: ConfigurationManager.kt
```kotlin
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
}
```

### Ejercicio 5: User.kt
```kotlin
package com.github.apermar1301.Ejercicio5

class User(val name: String = "") {
    val id: Int = ++counter

    companion object {
        private var counter = 0
    }
}
```

---

## 4. Tabla Resumen de Estado

| Ejercicio | Estado | Diagnóstico Principal |
| :--- | :--- | :--- |
| **1. BankAccount** | ⚠️ Requiere cambios | Falta setter con validación en `balance`, encapsular `_transactionHistory` y corregir condición de monto positivo. |
| **2. Jerarquía Empleados** | ⚠️ Requiere cambios | Agregar 3er nivel de herencia, usar `Int` para `id` y renombrar a `getDetails()`. |
| **3. Hotel & Anidadas** | ❌ Error conceptual | Invertir `inner` (`Reservation` es estática, `Employee` es `inner`), eliminar `workedRooms` y añadir `main()`. |
| **4. ConfigurationManager** | ⚠️ Requiere cambios | Hacer `private` el mapa `configuration` y eliminar punto y coma. |
| **5. User** | 🟡 Casi completo | Cambiar `var id` por `val id` inmutable. |
