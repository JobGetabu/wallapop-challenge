package com.wallapop.repository

import com.wallapop.model.*
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.ConcurrentLinkedQueue

class PlayerRepositoryTest {

    @RelaxedMockK
    lateinit var deck: Deck

    @RelaxedMockK
    lateinit var card: Card

    lateinit var playerRepository: PlayerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val players = arrayListOf<Player>()
        players.add(Player("Professor X", ConcurrentLinkedQueue(), ConcurrentLinkedQueue()))
        players.add(Player("Magneto", ConcurrentLinkedQueue(), ConcurrentLinkedQueue()))

        deck = Deck()
        playerRepository = PlayerRepository(deck, players)
    }

    @Test
    fun `verify getCards() returns deck cards`() {
        val cards = playerRepository.getCards()

        Assert.assertNotNull(cards.firstOrNull())
    }

    @Test
    fun `verify shuffleDeck() returns a shuffled deck of cards`() {
        val cards = playerRepository.getCards()

        playerRepository.shuffleDeck()

        Assert.assertNotEquals(cards.firstOrNull(), Card(Rank.TWO, Suit.HEART))
    }

    @Test
    fun `verify shuffleCardsToPlayers() give equal pile to each player`() {
        playerRepository.shuffleCardsToPlayers()

        val deckSizePlayerOne = playerRepository.getPlayers()[0].deckPile.size
        val deckSizePlayerTwo = playerRepository.getPlayers()[1].deckPile.size

        Assert.assertTrue(deckSizePlayerOne > 0)
        Assert.assertTrue(deckSizePlayerTwo > 0)

        Assert.assertEquals(deckSizePlayerOne, deckSizePlayerTwo)
    }

    @Test
    fun `verify compareTo() returns correct card priority`() {
        val card1 = Card(Rank.TWO, Suit.HEART)
        val card2 = Card(Rank.TWO, Suit.DIAMONDS)
        val card3 = Card(Rank.THREE, Suit.SPADES)
        val card4 = Card(Rank.ACE, Suit.CLUBS)
        val card5 = Card(Rank.THREE, Suit.SPADES)

        Assert.assertTrue(card1.compareTo(card2) == -1)
        Assert.assertTrue(card2.compareTo(card1) == 1)
        Assert.assertTrue(card3.compareTo(card4) == -1)
        Assert.assertTrue(card4.compareTo(card5) == 1)
        Assert.assertTrue(card5.compareTo(card1) == 1)
        Assert.assertTrue(card3.compareTo(card5) == 0)
    }

    @Test
    fun `verify playAround() when playerTwo has higher card wins the round`() {
        val result =
            playerRepository.playAround(Card(Rank.TWO, Suit.HEART), Card(Rank.ACE, Suit.CLUBS))

        Assert.assertEquals(
            result.lastOrNull(),
            Pair(
                "PlayerTwo wins $${Card(Rank.ACE, Suit.CLUBS)} > $${Card(Rank.TWO, Suit.HEART)}",
                false
            )
        )
    }

    @Test
    fun `verify playAround() when playerOne has higher card wins the round`() {
        val result =
            playerRepository.playAround(Card(Rank.ACE, Suit.CLUBS), Card(Rank.TWO, Suit.HEART))

        Assert.assertEquals(
            result.lastOrNull(),
            Pair(
                "PlayerOne wins $${Card(Rank.ACE, Suit.CLUBS)} > $${Card(Rank.TWO, Suit.HEART)}",
                false
            )
        )
    }

    @Test
    fun `verify playGame() is completely random`() {
        val discardPileOne = playerRepository.getPlayers()[0].discardPile.size
        val discardPileTwo = playerRepository.getPlayers()[1].discardPile.size

        Assert.assertNotEquals(discardPileOne, discardPileTwo)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }


}