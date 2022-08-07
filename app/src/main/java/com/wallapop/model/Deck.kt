package com.wallapop.model

import com.wallapop.util.shuffle

class Deck {
    val cards: MutableList<Card>

    fun shuffleMyList() {
        shuffle(cards)
    }

    init {
        cards = ArrayList()
        for (s in Suit.values()) {
            for (r in Rank.values()) {
                cards.add(Card(r, s))
            }
        }
    }
}

