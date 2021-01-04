package com.example.madlevel5task2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var gameRepository: GameRepository
    private val gameList = arrayListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navController = findNavController(R.id.nav_host_fragment)

        // Setting action bar title
        val actionbar = supportActionBar
        actionbar!!.title = "Game Backlog"

        gameRepository = GameRepository(this)

        // Navigates to AddGameFragment
        fabAddGame.setOnClickListener {
            navController.navigate(R.id.action_gameBacklogFragment_to_addGameFragment)

            //set actionbar title
            actionbar.title = "Add Game"
            //set back button
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        toolbarToggle()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete -> {
                deleteGame()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // This method is triggered when you go back from the addGameFragment
    override fun onSupportNavigateUp(): Boolean {
        val actionbar = supportActionBar
        actionbar!!.title = "Game Backlog"
        actionbar.setDisplayHomeAsUpEnabled(false)

        toolbar.menu.findItem(R.id.action_delete).isVisible = true
        fabAddGame.show()

        onBackPressed()
        return true
    }

    /**
     * Deletes the first game in the list. If the user want to undo the action the user
     * can press the "undo" on the snackbar to retrieve the game back
     */
    private fun deleteGame() {
        // Retrieve all the game from the database
        val gameBacklog = gameRepository.getAllGames()

        // Check if the gameList list is not empty. Clear the old data from list if the if-statement is true
        // And add the new data from the database into the gameList list
        if (gameList.isNotEmpty()) {
            gameList.clear()
            gameList.addAll(gameBacklog)
        } else {
            gameList.addAll(gameBacklog)
        }

        // Retrieve the first game from the gameList list
        val game = gameList[0]

        // Delete the game from the database
        gameRepository.deleteGame(game)

        // Shows an snackbar where the user can undo the deletion of a game
        val snack = Snackbar.make(rootLayout, "Successfully deleted game", Snackbar.LENGTH_LONG)
        snack.setAction("Undo") {
            gameRepository.insertGame(game)
        }
        snack.show()
    }

    /**
     * Changes the items in the toolbar and the Floating Action Button
     */
    private fun toolbarToggle() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when {
                destination.id in arrayOf(R.id.addGameFragment) -> {
                    toolbar.menu.findItem(R.id.action_delete).isVisible = false
                    fabAddGame.hide()
                }
                toolbar.menu.findItem(R.id.action_delete) != null -> {
                    val actionbar = supportActionBar
                    actionbar!!.title = "Game Backlog"
                    actionbar.setDisplayHomeAsUpEnabled(false)

                    toolbar.menu.findItem(R.id.action_delete).isVisible = true
                    fabAddGame.show()
                }
                else -> {
                    println("Application just started so this menu item is null")
                }
            }
        }
    }
}
