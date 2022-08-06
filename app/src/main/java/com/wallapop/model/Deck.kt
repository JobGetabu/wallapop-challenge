package com.wallapop.model

class Deck {
    val cards: MutableList<Card>

    fun shuffle() = cards.shuffle()

    init {
        cards = ArrayList()
        for (s in Suit.values()) {
            for (r in Rank.values()) {
                cards.add(Card(r, s))
            }
        }
    }
}

