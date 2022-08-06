package com.wallapop.repository

import android.util.Log
import com.wallapop.model.Card
import com.wallapop.model.Deck
import com.wallapop.model.Player
import com.wallapop.util.Constants
import java.util.concurrent.ConcurrentLinkedQueue

class PlayerRepository(private val deck: Deck, private val players: ArrayList<Player>) {

    fun shuffleDeck() = deck.shuffle()

    fun shuffleCardsToPlayers() {
        players[0].deckPile.addAll(deck.cards.subList(0, deck.cards.size / 2))
        players[1].deckPile.addAll(deck.cards.subList(deck.cards.size / 2, deck.cards.size))
    }

    fun playAround(cardFromPlayer1: Card, cardFromPlayer2: Card) {

        val a = ConcurrentLinkedQueue<Card>()


        //remove the cards from the round
        players[0].deckPile.removeAll { it == cardFromPlayer1 }
        players[1].deckPile.removeAll { it == cardFromPlayer2 }

        //add cards to the winner
        if (cardFromPlayer1.compareTo(cardFromPlayer2) == 1) {
            players[0].discardPile.add(cardFromPlayer1)
            players[0].discardPile.add(cardFromPlayer2)

            Log.d(Constants.TAG, "PlayerOne wins $cardFromPlayer1 > $cardFromPlayer2 ")
        } else {
            players[1].discardPile.add(cardFromPlayer1)
            players[1].discardPile.add(cardFromPlayer2)

            Log.d(Constants.TAG, "PlayerTwo wins $cardFromPlayer2 > $cardFromPlayer1 ")
        }
    }

    fun playGame() {
        for (i in 1..(deck.cards.size / 2)) {
            if (players[0].deckPile.size > i && players[1].deckPile.size > 1) {
                playAround(players[0].deckPile.elementAt(i), players[1].deckPile.elementAt(i))
            }
        }

        Log.d(Constants.TAG, "Player0 deck ${players[0].deckPile.size} discard ${players[0].discardPile.size}")
        Log.d(Constants.TAG, "Player1 deck ${players[1].deckPile.size} discard ${players[1].discardPile.size}")

        if (players[0].discardPile.size > players[1].discardPile.size) {
            Log.d(Constants.TAG, "WON by $${players[0]}")
        } else {
            Log.d(Constants.TAG, "WON by $${players[1]}")
        }

    }


}