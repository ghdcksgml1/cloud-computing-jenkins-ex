package com.chanhee.hackingspringbootch2reactive.service

import com.chanhee.hackingspringbootch2reactive.model.Cart
import com.chanhee.hackingspringbootch2reactive.model.CartItem
import com.chanhee.hackingspringbootch2reactive.repository.CartRepository
import com.chanhee.hackingspringbootch2reactive.repository.ItemRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class CartService(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository
) {
    suspend fun addToCart(@PathVariable id: String): String {
        val cart = this.cartRepository.findById("My Cart")
            .log("foundCart")
            .switchIfEmpty { this.cartRepository.save(Cart("My Cart")) }
            .log("emptyCart")
            .awaitFirstOrNull()

        val cartItem = cart!!.cartItems
            .find { it.item.id == id }

        return if (cartItem == null) {
            val item = this.itemRepository.findById(id)
                .log("fetchedItem")
                ?.awaitSingle() ?: throw IllegalArgumentException()
            cart.cartItems.add(CartItem(item))
            saveCartAndRedirect(cart)
        } else {
            cartItem.increment()
            saveCartAndRedirect(cart)
        }

    }

    private suspend fun saveCartAndRedirect(cart: Cart): String {
        this.cartRepository.save(cart).log("addedCartItem").awaitSingle()
        return "redirect:/"
    }
}