package com.wallapop.model

enum class Suit {
    HEART, DIAMONDS, SPADES, CLUBS
}

enum class Rank {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
}

class Card(private var rank: Rank, private var suit: Suit) : Comparable<Card> {
    override fun toString(): String {
        return "$rank of $suit"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        return this.rank == other.rank
                && this.suit == other.suit
    }

    override fun hashCode(): Int {
        var result = rank.hashCode()
        result = 31 * result + suit.hashCode()
        return result
    }

    override fun compareTo(other: Card): Int {
        if (this.rank < other.rank) {
            return -1
        }
        if (this.rank > other.rank) {
            return 1
        }

        if (this.suit < other.suit) {
            return -1
        }
        if (this.suit > other.suit) {
            return 1
        }
        return 0
    }


}