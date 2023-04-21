package com.chanhee.hackingspringbootch2reactive.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("Cart")
data class Cart(
    @Id
    val id: String,
    val cartItems: MutableList<CartItem> = mutableListOf<CartItem>()
) {
}