package com.wallapop.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wallapop.R
import com.wallapop.model.Deck
import com.wallapop.model.Player
import com.wallapop.repository.PlayerRepository
import com.wallapop.util.Constants
import java.util.concurrent.ConcurrentLinkedQueue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //create a random suit priority

        //create a deck
        val deck = Deck()

        Log.d(Constants.TAG, " $${deck.cards}")
        Log.d(Constants.TAG, "DECK SIZE \$${deck.cards.size}")
        Log.d(Constants.TAG, "DECK HALF SIZE \$${deck.cards.size / 2}")

        //create players
        val players = arrayListOf<Player>()
        players.add(Player("one", ConcurrentLinkedQueue(), ConcurrentLinkedQueue()))
        players.add(Player("two", ConcurrentLinkedQueue(), ConcurrentLinkedQueue()))

        Log.d(Constants.TAG, " $$players")

        //create a Game
        val playerRepository = PlayerRepository(deck, players)

        Log.d(Constants.TAG, "DECK SHUFFLED")
        Log.d(Constants.TAG, " $${deck.cards}")



        //play a round
        playerRepository.shuffleDeck()
        playerRepository.shuffleCardsToPlayers()

        Log.d(Constants.TAG, "PLAYERS DEALT")
        Log.d(Constants.TAG, " $$players")


        //play & get the winner
        playerRepository.playGame()

    }
}