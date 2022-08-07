package com.wallapop.repository

import android.util.Log
import com.wallapop.model.Card
import com.wallapop.model.Deck
import com.wallapop.model.Player
import com.wallapop.util.Constants
import com.wallapop.util.shuffle
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * A group of *members*.
 *
 * This class acts as a Game class for the entire business logic
 * It combines the different properties of classes to bring to life the
 * gaming & dealer aspect of pocker
 *
 * @param deck accepts a Deck class.
 * @param players  accepts a list of players
 */
class PlayerRepository(private val deck: Deck, private val players: ArrayList<Player>) {

    fun getCards() = deck.cards

    fun shuffleDeck() = shuffle(getCards())

    /**
     * Gives each [Player] cards equally and randomly
     */
    fun shuffleCardsToPlayers() {
        players[0].deckPile.addAll(deck.cards.subList(0, deck.cards.size / 2))
        players[1].deckPile.addAll(deck.cards.subList(deck.cards.size / 2, deck.cards.size))
    }

    /**
     * Receives [Card]s , to play a single round.
     * @return an [ArrayList<Pair<String, Boolean?>>] for consumption by the UI.
     */
    private fun playAround(cardFromPlayer1: Card, cardFromPlayer2: Card) : ArrayList<Pair<String, Boolean?>> {
        val items = arrayListOf<Pair<String, Boolean?>>()

        //remove the cards from the round
        players[0].deckPile.removeAll { it == cardFromPlayer1 }
        players[1].deckPile.removeAll { it == cardFromPlayer2 }

        //add cards to the winner
        if (cardFromPlayer1.compareTo(cardFromPlayer2) == 1) {
            players[0].discardPile.add(cardFromPlayer1)
            players[0].discardPile.add(cardFromPlayer2)

            Log.d(Constants.TAG, "PlayerOne wins $cardFromPlayer1 > $cardFromPlayer2")
            items.add(Pair("PlayerOne wins $cardFromPlayer1 > $cardFromPlayer2", true))
        } else {
            players[1].discardPile.add(cardFromPlayer1)
            players[1].discardPile.add(cardFromPlayer2)

            Log.d(Constants.TAG, "PlayerTwo wins $cardFromPlayer2 > $cardFromPlayer1")
            items.add(Pair("PlayerTwo wins $cardFromPlayer2 > $cardFromPlayer1", false))
        }

        return items
    }

    /**
     * @return an [ArrayList<Pair<String, Boolean?>>] for consumption by the UI.
     */
    fun playGame() : ArrayList<Pair<String, Boolean?>> {
        val items = arrayListOf<Pair<String, Boolean?>>()

        for (i in 1..(deck.cards.size / 2)) {
            if (players[0].deckPile.size > i && players[1].deckPile.size > 1) {
                items.addAll(playAround(players[0].deckPile.elementAt(i), players[1].deckPile.elementAt(i)))
            }
        }

        Log.d(Constants.TAG, "Player ${players[0].name} deck size ${players[0].deckPile.size} discard size ${players[0].discardPile.size}")
        Log.d(Constants.TAG, "Player ${players[1].name} deck size ${players[1].deckPile.size} discard size ${players[1].discardPile.size}")

        items.add(Pair("Player ${players[0].name} deck size ${players[0].deckPile.size} discard size ${players[0].discardPile.size}", null))
        items.add(Pair("Player ${players[1].name} deck size ${players[1].deckPile.size} discard size ${players[1].discardPile.size}", null))

        if (players[0].discardPile.size > players[1].discardPile.size) {
            Log.d(Constants.TAG, "WON by $${players[0]}")

            items.add(Pair("WON by Player ${players[0].name} discard size ${players[0].discardPile.size}", false))
        } else {
            Log.d(Constants.TAG, "WON by $${players[1]}")

            items.add(Pair("WON by Player ${players[1].name} discard size ${players[1].discardPile.size}", false))
        }

        return items
    }


}