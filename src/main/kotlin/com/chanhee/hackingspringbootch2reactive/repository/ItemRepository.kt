package com.chanhee.hackingspringbootch2reactive.repository

import com.chanhee.hackingspringbootch2reactive.model.Item
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ItemRepository : ReactiveCrudRepository<Item, String>,
                            ReactiveQueryByExampleExecutor<Item> {
    fun findByNameContainingIgnoreCase(partialName: String): Flux<Item?>
}