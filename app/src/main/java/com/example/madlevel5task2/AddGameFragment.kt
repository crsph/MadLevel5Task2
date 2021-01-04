package com.example.madlevel5task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_game.*
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private var monthHashMap: HashMap<Int, String> = HashMap()
    private var monthArray = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populate HashMap with keys and months
        for (i in 1..12) {
            monthHashMap[i] = monthArray[i - 1]
        }

        gameRepository = GameRepository(requireContext())

        fabSaveGame.setOnClickListener {
            addGame()
        }
    }

    private fun addGame() {
        val gameTitle = etTitle.text.toString()
        val gamePlatform = etPlatform.text.toString()
        val day = etDay.text.toString()
        var month = etMonth.text.toString()
        val year = etYear.text.toString()

        when {
            gameTitle.isBlank() -> {
                Toast.makeText(context, R.string.empty_title, Toast.LENGTH_LONG).show()
            }
            gamePlatform.isBlank() -> {
                Toast.makeText(context, R.string.empty_platform, Toast.LENGTH_LONG).show()
            }
            day.isBlank() -> {
                Toast.makeText(context, R.string.empty_day, Toast.LENGTH_LONG).show()
            }
            month.isBlank() -> {
                Toast.makeText(context, R.string.empty_month, Toast.LENGTH_LONG).show()
            }
            year.isBlank() -> {
                Toast.makeText(context, R.string.empty_year, Toast.LENGTH_LONG).show()
            }
            else -> {
                if (day.toInt() in 1..31 && month.toInt() in 1..12 && year.toInt() in 1900..2024) {

                    // Retrieves the month from the HashMap
                    month = monthHashMap[month.toInt()].toString()

                    // Combine the three variables as String
                    val releaseDate = "Release: $day $month $year"

                    // Create new Game object
                    val game = Game(gameTitle, gamePlatform, releaseDate)

                    // Insert Game object into the database
                    gameRepository.insertGame(game)

                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Please fill in a valid date.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}