package com.example.madlevel5task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_game_backlog.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val gameList = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(gameList)

//    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_backlog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        gameRepository = GameRepository(requireContext())

        getGamesFromDatabase()
    }

    override fun onResume() {
        super.onResume()

        gameAdapter.notifyDataSetChanged()
    }

    private fun initViews() {
        rvGameBacklog.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGameBacklog.adapter = gameAdapter
    }

    private fun getGamesFromDatabase() {
        val game = gameRepository.getAllGames()
        this@GameBacklogFragment.gameList.clear()
        this@GameBacklogFragment.gameList.addAll(game)
        gameAdapter.notifyDataSetChanged()
    }

}