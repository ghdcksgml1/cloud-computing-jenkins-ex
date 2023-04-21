package com.chanhee.hackingspringbootch2reactive.service

import com.chanhee.hackingspringbootch2reactive.model.Item
import com.chanhee.hackingspringbootch2reactive.repository.ItemRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class InventoryService(
    private val itemRepository: ItemRepository
) {
    fun searchByExample(name: String, description: String, useAnd: Boolean): Flux<Item?> {
        val item = Item(name = name, price = 0.0)

        val matcher: ExampleMatcher = (if (useAnd) ExampleMatcher.matchingAll() else ExampleMatcher.matchingAny())
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase()
            .withIgnorePaths("price")

        val probe: Example<Item> = Example.of(item, matcher)

        return this.itemRepository.findAll(probe)
    }
}

// ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC3IPlswkRQdLHVr8fInodGFSM0T7urn9y61pKEsQ5hSu7jXqFE3eZCjXbNBNChTSFL4FdUWI8W9RRgJBDF6HbR6s1UkXUJ+BYcW1ltsBWuY/3c2fmO2Pnp/1V+nheRnZn/mMVwyvUGf61g3ulX6fC+WVqZVwr0xIsRGGNi90QbHs1YjRuGaXU4Nf3ZuibX78XOgGiiH3/CSSZlog7YPN4R8B0RPXhekzQnA3MFiNnDfi5mLB31C7nJIQ1jXQTYaHI3QMDbJFPO7n0JNZ3hMrOsDIsJMtUn4fJxyUEpvhfefcLZThQNNQHXd9WMQnY6hvfuHYqS9tfpTfqIln3a7+m8dQ61Sp0u6yzgbdwcNyZ2AMuvYHPbrrNmOpiQCCk/KQKIX/lgBbgi5HcQFQA9jMXEZcm5l9OHKaPqJnhTUYv3kgNnaQz5KsZ9WazOSecZBjVpZlz1HN1VO9RjH6LXucwb3sZ9rfqxqy0YoYJyGuSdmyXeWkkZ5hIjI+8EBNpR+x0= mykey