package com.chanhee.hackingspringbootch2reactive.controller

import com.chanhee.hackingspringbootch2reactive.model.Cart
import com.chanhee.hackingspringbootch2reactive.model.CartItem
import com.chanhee.hackingspringbootch2reactive.model.Item
import com.chanhee.hackingspringbootch2reactive.repository.CartRepository
import com.chanhee.hackingspringbootch2reactive.repository.ItemRepository
import com.chanhee.hackingspringbootch2reactive.service.CartService
import com.chanhee.hackingspringbootch2reactive.service.InventoryService
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.reactive.result.view.Rendering
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Controller
class HomeController(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository,
    private val cartService: CartService,
    private val inventoryService: InventoryService
) {

    @GetMapping
    suspend fun home(): Rendering {
        // 만약 조회 값이 없을 때 쓸 Item들
        val defaultItem1 = Item(null, "Alf alarm clock", "default",19.99)
        val defaultItem2 = Item(null, "Smurf TV tray", "default",24.99)

        // 아이템 가져오기 없다면 Default 값으로 대체
        val items = itemRepository.findAll().doOnNext(::println)
            .collectList()
            .flatMap { itemList ->
                if (itemList.isEmpty()) {
                    Flux.just(defaultItem1, defaultItem2).collectList()
                } else {
                    Mono.just(itemList)
                }
            }.awaitFirstOrElse { null } // Mono<List<Item!>>

        val defaultCart = Cart("My Cart")

        // 카트조회하고 없으면 대체
        val cart = this.cartRepository.findById("My Cart")
            .switchIfEmpty { this.cartRepository.save(defaultCart) }
            .awaitFirst()

        // 렌더링
        return Rendering.view("home.html")
            .modelAttribute("items", items!!)
            .modelAttribute("cart", cart)
            .build()
    }

    @PostMapping("/add/{id}")
    suspend fun addToCart(@PathVariable id: String): String {
        return cartService.addToCart(id)
    }

    @GetMapping("/search")
    suspend fun search(
        @RequestParam name: String,
        @RequestParam(required = false) description: String?,
        @RequestParam useAnd: Boolean
    ): Rendering {
        println("name: ${name}, description: ${description}, useAnd: ${useAnd}")
        val items = this.itemRepository.findByNameContainingIgnoreCase(name)
            .collectList()
            .doOnNext { println(it) }
            .awaitFirstOrNull()

        val cart = this.cartRepository.findById("My Cart")
            .switchIfEmpty { this.cartRepository.save(Cart("My Cart")) }
            .awaitSingle()

        return Rendering.view("home.html")
            .modelAttribute("items", items?: mutableListOf(""))
            .modelAttribute("cart", cart)
            .build()
    }

}