package com.github.apermar1301.Ejercicio1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Tests para Ejercicio 1: BankAccount")
class BankAccountTest {

    @Test
    @DisplayName("Debe inicializar la cuenta con el titular y saldo inicial correcto")
    fun testInitializationSuccess() {
        val account = BankAccount("Ana García", 150.0)
        assertEquals("Ana García", account.accountHolder)
        assertEquals(150.0, account.balance)
        assertTrue(account.transactionHistory.isEmpty())
    }

    @Test
    @DisplayName("Debe permitir inicializar la cuenta con saldo cero por defecto o explícito")
    fun testInitializationWithZeroBalance() {
        val accountDefault = BankAccount("Carlos López")
        assertEquals(0.0, accountDefault.balance)

        val accountExplicit = BankAccount("Carlos López", 0.0)
        assertEquals(0.0, accountExplicit.balance)
    }

    @Test
    @DisplayName("Debe lanzar IllegalArgumentException con el mensaje requerido si el saldo inicial es negativo")
    fun testInitializationNegativeBalanceThrows() {
        val exception = assertThrows<IllegalArgumentException> {
            BankAccount("Marta Ruiz", -50.0)
        }
        assertEquals("Balance must be non-negative", exception.message)
    }

    @Test
    @DisplayName("El setter de balance debe permitir valores mayores o iguales a cero")
    fun testBalanceSetterValidValues() {
        val account = BankAccount("Luis Torres", 100.0)

        account.balance = 250.5
        assertEquals(250.5, account.balance)

        account.balance = 0.0
        assertEquals(0.0, account.balance)
    }

    @Test
    @DisplayName("El setter de balance debe lanzar IllegalArgumentException con mensaje 'Balance must be non-negative' ante valores negativos")
    fun testBalanceSetterNegativeValueThrows() {
        val account = BankAccount("Luis Torres", 100.0)

        val exception = assertThrows<IllegalArgumentException> {
            account.balance = -0.01
        }
        assertEquals("Balance must be non-negative", exception.message)

        val exception2 = assertThrows<IllegalArgumentException> {
            account.balance = -100.0
        }
        assertEquals("Balance must be non-negative", exception2.message)

        // El saldo anterior no debió modificarse
        assertEquals(100.0, account.balance)
    }

    @Test
    @DisplayName("deposit() debe validar que el monto sea estrictamente positivo")
    fun testDepositInvalidAmountThrows() {
        val account = BankAccount("Pedro Gómez", 50.0)

        assertThrows<IllegalArgumentException> {
            account.deposit(0.0)
        }

        assertThrows<IllegalArgumentException> {
            account.deposit(-25.0)
        }

        assertEquals(50.0, account.balance)
        assertTrue(account.transactionHistory.isEmpty())
    }

    @Test
    @DisplayName("deposit() con monto positivo debe actualizar saldo y registrar en transactionHistory")
    fun testDepositValidUpdatesBalanceAndHistory() {
        val account = BankAccount("Pedro Gómez", 50.0)

        account.deposit(100.0)
        assertEquals(150.0, account.balance)
        assertEquals(1, account.transactionHistory.size)
        assertTrue(account.transactionHistory[0].contains("100"))

        account.deposit(50.5)
        assertEquals(200.5, account.balance)
        assertEquals(2, account.transactionHistory.size)
        assertTrue(account.transactionHistory[1].contains("50.5"))
    }

    @Test
    @DisplayName("withdraw() debe validar que el monto a retirar sea estrictamente positivo")
    fun testWithdrawInvalidAmountThrows() {
        val account = BankAccount("Elena Blanco", 200.0)

        assertThrows<IllegalArgumentException> {
            account.withdraw(0.0)
        }

        assertThrows<IllegalArgumentException> {
            account.withdraw(-30.0)
        }

        assertEquals(200.0, account.balance)
        assertTrue(account.transactionHistory.isEmpty())
    }

    @Test
    @DisplayName("withdraw() debe validar que el saldo sea suficiente, lanzando IllegalArgumentException si se supera")
    fun testWithdrawInsufficientBalanceThrows() {
        val account = BankAccount("Elena Blanco", 100.0)

        assertThrows<IllegalArgumentException> {
            account.withdraw(100.01)
        }

        assertThrows<IllegalArgumentException> {
            account.withdraw(500.0)
        }

        assertEquals(100.0, account.balance)
        assertTrue(account.transactionHistory.isEmpty())
    }

    @Test
    @DisplayName("withdraw() con saldo suficiente debe actualizar saldo y registrar en transactionHistory")
    fun testWithdrawValidUpdatesBalanceAndHistory() {
        val account = BankAccount("Elena Blanco", 200.0)

        account.withdraw(50.0)
        assertEquals(150.0, account.balance)
        assertEquals(1, account.transactionHistory.size)
        assertTrue(account.transactionHistory[0].contains("50"))

        // Retiro del saldo exacto restante
        account.withdraw(150.0)
        assertEquals(0.0, account.balance)
        assertEquals(2, account.transactionHistory.size)
    }

    @Test
    @DisplayName("transactionHistory debe ser de tipo List<String> para consulta externa")
    fun testTransactionHistoryType() {
        val account = BankAccount("Clara Soto", 100.0)
        account.deposit(50.0)

        val history: List<String> = account.transactionHistory
        assertEquals(1, history.size)
        // La lista no expone métodos de mutación a través de su interfaz List
        assertEquals("Deposit: +50.0", history[0])
    }

    @Test
    @DisplayName("Flujo completo de transacciones múltiples consecutivas")
    fun testCompleteTransactionFlow() {
        val account = BankAccount("Mario Vargas", 1000.0)

        account.deposit(200.0)
        account.withdraw(150.0)
        account.deposit(50.0)
        account.withdraw(100.0)

        assertEquals(1000.0 + 200.0 - 150.0 + 50.0 - 100.0, account.balance)
        assertEquals(4, account.transactionHistory.size)
    }
}
