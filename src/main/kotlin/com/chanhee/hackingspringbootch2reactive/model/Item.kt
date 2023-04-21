package com.chanhee.hackingspringbootch2reactive.model

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.awt.Point
import java.time.LocalDateTime
import java.util.*

@Document("item")
class Item(
    @Id
    val id: String? = null,
    val name: String,
    val description: String? = null,
    val price: Double,
    val distributorRegion: String? = null,
    val releaseDate: LocalDateTime? = null,
    val availableUnits: Int? = null,
    val location: Point? = null,
    val active: Boolean? = null
) {
    override fun toString(): String {
        return "Item(id=$id, name='$name', description=$description, price=$price, " +
                "distributorRegion=$distributorRegion, releaseDate=$releaseDate, " +
                "availableUnits=$availableUnits, location=$location, active=$active)"
    }
}
