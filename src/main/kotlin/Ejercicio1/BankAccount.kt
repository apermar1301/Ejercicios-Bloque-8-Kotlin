package com.github.apermar1301.Ejercicio1

class BankAccount(
    val accountHolder: String,
    balance: Double = 0.0
) {
    var balance: Double = balance
        set(value) {
            require(value >= 0.0) { "Balance must be non-negative" }
            field = value
        }

    init {
        require(this.balance >= 0.0) { "Balance must be non-negative" }
    }

    private val _transactionHistory: MutableList<String> = mutableListOf()
    val transactionHistory: List<String>
        get() = _transactionHistory

    fun getTheTransactionHistory(): List<String> = transactionHistory

    fun deposit(amount: Double) {
        require(amount > 0.0) { "Deposit amount must be positive" }
        balance += amount
        _transactionHistory.add("Deposit: +$amount")
    }

    fun withdraw(amount: Double) {
        require(amount > 0.0) { "Withdrawal amount must be positive" }
        require(amount <= balance) { "Insufficient balance" }
        balance -= amount
        _transactionHistory.add("Withdrawal: -$amount")
    }
}
