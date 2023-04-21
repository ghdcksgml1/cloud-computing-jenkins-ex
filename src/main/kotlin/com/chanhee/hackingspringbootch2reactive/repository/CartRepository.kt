package com.chanhee.hackingspringbootch2reactive.repository

import com.chanhee.hackingspringbootch2reactive.model.Cart
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CartRepository : ReactiveCrudRepository<Cart, String> {
}