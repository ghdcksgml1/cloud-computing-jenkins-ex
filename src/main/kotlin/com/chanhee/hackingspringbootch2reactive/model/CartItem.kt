package com.chanhee.hackingspringbootch2reactive.model

class CartItem(
    val item: Item,
    var quantity: Int = 1
) {
    fun increment() {
        this.quantity++
    }
}