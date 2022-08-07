package com.wallapop.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.wallapop.R
import com.wallapop.adapter.GameRoundListAdapter
import com.wallapop.databinding.ActivityMainBinding
import com.wallapop.model.Deck
import com.wallapop.model.Player
import com.wallapop.repository.PlayerRepository
import com.wallapop.util.Constants
import com.wallapop.util.hideView
import com.wallapop.util.showView
import java.util.concurrent.ConcurrentLinkedQueue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GameRoundListAdapter

    private var items = arrayListOf<Pair<String, Boolean?>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()

        binding.startResetGameBtn.setOnClickListener {
            adapter.clear()
            playNewGame()
        }


    }

    private fun setupList(){
        adapter = GameRoundListAdapter()
        //binding.playList.layoutManager = LinearLayoutManager(this)
        adapter.setItems(items)
        //binding.playList.setHasFixedSize(true)
        binding.playList.adapter = adapter

    }

    private fun playNewGame(){

        //setup UI
        playGameUI()

        //create a random suit priority

        //create a deck
        val deck = Deck()

        items.add(Pair("GAME STARTED", null))

        Log.d(Constants.TAG, " $${deck.cards}")
        Log.d(Constants.TAG, "DECK SIZE \$${deck.cards.size}")
        Log.d(Constants.TAG, "DECK HALF SIZE \$${deck.cards.size / 2}")

        items.add(Pair("DECK SIZE ${deck.cards.size}", null))
        adapter.refresh()

        //create players
        val players = arrayListOf<Player>()
        players.add(Player("Professor X", ConcurrentLinkedQueue(), ConcurrentLinkedQueue()))
        players.add(Player("Magneto", ConcurrentLinkedQueue(), ConcurrentLinkedQueue()))

        Log.d(Constants.TAG, " $$players")

        items.add(Pair("Player ${players[0].name} joined", null))
        items.add(Pair("Player ${players[1].name} joined", null))
        adapter.refresh()

        //create a Game
        val playerRepository = PlayerRepository(deck, players)


        items.add(Pair("DECK SHUFFLED", null))

        //play a round
        playerRepository.shuffleDeck()
        Log.d(Constants.TAG, "DECK SHUFFLED")
        Log.d(Constants.TAG, " $${playerRepository.getCards()}")

        playerRepository.shuffleCardsToPlayers()

        items.add(Pair("PLAYERS DEALT", null))
        adapter.refresh()

        Log.d(Constants.TAG, " $$players")


        //play & get the winner
        items.addAll(playerRepository.playGame())
        adapter.refresh()

        binding.playList.smoothScrollToPosition(adapter.itemCount)

    }

    private fun playGameUI(){
        binding.playList.showView()
        binding.welcomeTitle.hideView()
        binding.startResetGameBtn.text = "Play Again"

        binding.startResetGameBtn.setOnClickListener {
            adapter.clear()
            playNewGame()
        }

    }

}