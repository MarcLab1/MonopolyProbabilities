package com.monopolyprobability

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_square.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class HomeFragment : Fragment() {

    private lateinit var textTop: TextView
    private lateinit var textMiddle: TextView
    private lateinit var textBottom: TextView
    private var playerCurrentSquare: Int = 0
    private lateinit var squares: ArrayList<Square>
    private lateinit var playerRollsHistory: ArrayList<Int>
    private lateinit var diceRollCombinations: ArrayList<Int>
    private lateinit var communityChestCards: ArrayList<Int>
    private lateinit var chanceCards: ArrayList<Int>
    private var numberOfRolls: Int = 0
    private var playerWentToJail: Int = 0
    private var playerRolledDoubles: Int = 0
    private var playerPassedGo: Int = 0
    private lateinit var adapter: CustomAdapter
    private lateinit var recyclerView: RecyclerView
    private var die1: Int = 0
    private var die2: Int = 0
    private var dice: Int = 0
    private var doublesCount = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val brown: Int = (context?.let { ContextCompat.getColor(it, R.color.brown) }!!)
        val lightBlue: Int = (context?.let { ContextCompat.getColor(it, R.color.lightblue) }!!)
        val purple: Int = (context?.let { ContextCompat.getColor(it, R.color.purple) }!!)
        val orange: Int = (context?.let { ContextCompat.getColor(it, R.color.orange) }!!)
        val red: Int = (context?.let { ContextCompat.getColor(it, R.color.red) }!!)
        val yellow: Int = (context?.let { ContextCompat.getColor(it, R.color.yellow) }!!)
        val green: Int = (context?.let { ContextCompat.getColor(it, R.color.green) }!!)
        val blue: Int = (context?.let { ContextCompat.getColor(it, R.color.blue) }!!)
        val black: Int = (context?.let { ContextCompat.getColor(it, R.color.black) }!!)
        val white: Int = (context?.let { ContextCompat.getColor(it, R.color.white) }!!)

        squares = ArrayList();
        squares.add(Square("Go", 0, white, black))
        squares.add(Square("Mediterranean Avenue", 1, brown, white))
        squares.add(Square("Community Chest", 2, white, black))
        squares.add(Square("Baltic Avenue", 3, brown, white))
        squares.add(Square("Income Tax", 4, white, black))
        squares.add(Square("Reading Railroad", 5, white, black))
        squares.add(Square("Oriental Avenue", 6, lightBlue, white))
        squares.add(Square("Chance", 7, white, black))
        squares.add(Square("Vermont Avenue", 8, lightBlue, white))
        squares.add(Square("Connecticut Avenue", 9, lightBlue, white))
        squares.add(Square("Visiting Jail", 10, white, black))
        squares.add(Square("St. Charles Place", 11, purple, white))
        squares.add(Square("Electric Company", 12, white, black))
        squares.add(Square("States Avenue", 13, purple, white))
        squares.add(Square("Virginia Avenue", 14, purple, white))
        squares.add(Square("Pennsylvania Railroad", 15, white, black))
        squares.add(Square("St. James Place", 16, orange, white))
        squares.add(Square("Community Chest", 17, white, black))
        squares.add(Square("Tennessee Avenue", 18, orange, white))
        squares.add(Square("NewYork Avenue", 19, orange, white))
        squares.add(Square("Free Parking", 20, white, black))
        squares.add(Square("Kentucky Avenue", 21, red, white))
        squares.add(Square("Chance", 22, white, black))
        squares.add(Square("Indiana Avenue", 23, red, white))
        squares.add(Square("Illinois Avenue", 24, red, white))
        squares.add(Square("B&O Railroad", 25, white, black))
        squares.add(Square("Atlantic Avenue", 26, yellow, black))
        squares.add(Square("Ventnor Avenue", 27, yellow, black))
        squares.add(Square("Water Works", 28, white, black))
        squares.add(Square("Marvin Gardens", 29, yellow, black))
        squares.add(Square("Go To Jail", 30, white, black))
        squares.add(Square("Pacific Avenue", 31, green, white))
        squares.add(Square("NorthCarolina Avenue", 32, green, white))
        squares.add(Square("Community Chest", 33, white, black))
        squares.add(Square("Pennsylvania Avenue", 34, green, white))
        squares.add(Square("Short Line", 35, white, black))
        squares.add(Square("Chance", 36, white, black))
        squares.add(Square("ParkPlace", 37, blue, white))
        squares.add(Square("Luxury Tax", 38, white, black))
        squares.add(Square("Boardwalk", 39, blue, white))
        squares.add(Square("In Jail", 40, white, black))

        textTop = root.findViewById(R.id.texttop)
        textMiddle = root.findViewById(R.id.textmiddle)
        textBottom = root.findViewById(R.id.textbottom)

        diceRollCombinations = arrayListOf<Int>(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        communityChestCards =
                arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
        chanceCards = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)

        var spinner: Spinner = root.findViewById(R.id.spinner)
        var spinnerOptions = arrayOf(50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000, 1000000)
        if (spinner != null) {
            var adapter = context?.let {
                ArrayAdapter(
                        it,
                        android.R.layout.simple_spinner_item,
                        spinnerOptions
                )
            }
            spinner.adapter = adapter
        }

        var playGameButton: Button = root.findViewById(R.id.buttonPlayGame)
        playGameButton.setOnClickListener { playGame(spinner.selectedItem as Int) }

        recyclerView = root.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        //playerSquaresHistory = ArrayList(numberOfRollsThisGame)
        playerRollsHistory = ArrayList(numberOfRolls)

        return root
    }

    private fun rollDie(): Int {
        return Random.nextInt(1, 7)
    }

    private fun playGame(numberOfRolls: Int) {
        this.numberOfRolls = numberOfRolls
        Square.numberOfRolls = numberOfRolls //set
        playerPassedGo = 0
        playerRolledDoubles = 0
        playerWentToJail = 0
        for (sq in squares)
            sq.resetHits()
        communityChestCards.shuffle()
        chanceCards.shuffle()
        playerCurrentSquare = 0 //start on go
        playerRollsHistory.clear()
        textBottom.text = "Player landed on:"

        for (i in 1..numberOfRolls) {
            die1 = rollDie()
            die2 = rollDie()

            if (die1 == die2) {
                doublesCount++
                playerRolledDoubles++
            } else
                doublesCount = 0
            dice = die1 + die2
            playerRollsHistory.add(dice)

            //rolled doubles 3 times? go directly to jail
            if (doublesCount == 3) {
                playerWentToJail++
                playerCurrentSquare = 40
                doublesCount = 0
            } else {
                playerCurrentSquare += dice
                if (playerCurrentSquare >= 40) {
                    playerCurrentSquare = playerCurrentSquare.rem(40)
                    playerPassedGo++
                }
                //land on go to jail?
                if (playerCurrentSquare == 30) {
                    playerWentToJail++
                    playerCurrentSquare = 40
                }
                //chance -- have to do this first, there is card that takes you to a community chest
                if (playerCurrentSquare == 7 || playerCurrentSquare == 22 || playerCurrentSquare == 36) {
                    playerCurrentSquare = drawChanceCard(playerCurrentSquare)
                }

                //community chest
                if (playerCurrentSquare == 2 || playerCurrentSquare == 17 || playerCurrentSquare == 33) {
                    playerCurrentSquare = drawCommunityChestCard(playerCurrentSquare)
                }
            }
            //playerSquaresHistory.add(playerCurrentSquare)
            squares[playerCurrentSquare].increaseHits()

            //in jail?  Next turn player is just visting
            if (playerCurrentSquare == 40)
                playerCurrentSquare = 10

        }
        textTop.text = "Player rolled the dice " + numberOfRolls + " times." + "\n" +
                "Rolled doubles " + playerRolledDoubles + " times." + "\n" +
                "Went to jail " + playerWentToJail + " times." + "\n" +
                "Passed Go " + playerPassedGo + " times." + "\n"

        textMiddle.text = "Player rolled two " + Collections.frequency(playerRollsHistory, 2) + " times; " + " Rolled three " + Collections.frequency(playerRollsHistory, 3) + " times." + "\n" +
                "Rolled four " + Collections.frequency(playerRollsHistory, 4) + " times;" + " Rolled five " + Collections.frequency(playerRollsHistory, 5) + " times." + "\n" +
                "Rolled six " + Collections.frequency(playerRollsHistory, 6) + " times;" + " Rolled seven " + Collections.frequency(playerRollsHistory, 7) + " times." + "\n" +
                "Rolled eight " + Collections.frequency(playerRollsHistory, 8) + " times;" + " Rolled nine " + Collections.frequency(playerRollsHistory, 9) + " times." + "\n" +
                "Rolled ten " + Collections.frequency(playerRollsHistory, 10) + " times;" + " Rolled eleven " + Collections.frequency(playerRollsHistory, 11) + " times;" + "\n" +
                "Rolled twelve " + Collections.frequency(playerRollsHistory, 12) + " times."

        var sortedList = squares.sortedWith(compareBy({ it.hits })).reversed()
        adapter = CustomAdapter(sortedList as ArrayList<Square>)
        adapter!!.notifyDataSetChanged()
        recyclerView.adapter = adapter
    }

    private fun drawCommunityChestCard(squareNumber: Int): Int {
        var card: Int = communityChestCards.removeAt(0)
        communityChestCards.add(card) //put the card on the bottom

        when (card) {
            /* Go to Go. */
            1 -> {
                playerPassedGo++
                return 0
            }

            /* Go to Jail. */
            2 -> {
                playerWentToJail++
                return 40
            }

            /* Get $10 in Beauty Contest */
            /* Get $45 for sale of stock. */
            /* Inherit $100 */
            /* Receive $25 for services. */
            /* Pay doctor's fee of $50. */
            /* Bank error in your favor of $200. */
            /* Pay school tax of $150. */
            /* Income tax refund of $20. */
            /* Pay school tax of $150. */
            /* Pay hospital bill of $100. */
            /* Life insurance matures for $100. */
            /* Xmas fund matures for $100. */
            else -> return squareNumber
        }
    }


    private fun drawChanceCard(squareNumber: Int): Int {
        var card: Int = chanceCards.removeAt(0)
        chanceCards.add(card) //put the card on the bottom

        when (card) {
            /* Go to Go. */
            1 -> {
                playerPassedGo++
                return 0
            }

            /* Go to Jail. */
            2 -> {
                playerWentToJail++
                return 40
            }
            //Go Back 3 Spaces
            3 -> {
                var newSquareNumber: Int = squareNumber - 3
                if (newSquareNumber < 0)
                    newSquareNumber += 40
                return newSquareNumber
            }

            /* Go to Illinois */
            4 -> {
                if (squareNumber >= 24)
                    playerPassedGo++
                return 24
            }

            /* Go to St. Charles. */
            5 -> {
                if (squareNumber >= 11)
                    playerPassedGo++
                return 11
            }
            //Utility, pass go
            6 -> {
                if (squareNumber >= 12)
                    return 28
                playerPassedGo++
                return 12
            }
            //Nearest Railroad
            7 -> {
                if (squareNumber == 7)
                    return 15
                else if (squareNumber == 22)
                    return 25
                else //== 36
                {
                    playerPassedGo++
                    return 5
                }

            }
            //Reading railroad, pass go yo
            8 -> {
                if (squareNumber >= 5)
                    playerPassedGo++
                return 5
            }
            //Boardwalk, (cannot pass go dummy)
            9 -> {
                return 39
            }
            else -> return squareNumber
        }
    }

    class CustomAdapter(var squares: ArrayList<Square>) :
            RecyclerView.Adapter<CustomAdapter.UserViewHolder>() {

        fun updateSquares(newSquares: List<Square>) {
            squares.clear()
            squares.addAll(newSquares)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = UserViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_square, parent, false)
        )

        override fun getItemCount() = squares.size
        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            holder.bind(squares[position])
        }

        class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            private val name = view.textViewName
            private val hits = view.textViewHits
            private val hitsPercent = view.textViewHitsPercent
            private val squareLayout = view.square_layout

            fun bind(sq: Square) {
                name.text = sq.name + " - "
                hits.text = sq.hits.toString() + " times "
                hitsPercent.text = "(" + sq.hitsPercent + ")"
                squareLayout.setBackgroundColor(sq.backgroundColor)
                name.setTextColor(sq.foregroundColor)
                //number.setTextColor(sq.foregroundColor)
                hits.setTextColor(sq.foregroundColor)
                hitsPercent.setTextColor(sq.foregroundColor)
            }
        }
    }
}