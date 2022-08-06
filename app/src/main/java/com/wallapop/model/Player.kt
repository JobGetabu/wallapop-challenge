package com.wallapop.model

import java.util.concurrent.ConcurrentLinkedQueue

data class Player(
    val name: String,
    var discardPile: ConcurrentLinkedQueue<Card>,
    var deckPile: ConcurrentLinkedQueue<Card>
){
    override fun toString(): String {
        return "Player(name='$name', discardPile=$discardPile, deckPile=$deckPile)"
    }

}