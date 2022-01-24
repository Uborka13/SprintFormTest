package hu.urban.david.sprintformtest.network.service

import hu.urban.david.sprintformtest.network.model.BoardGamesListResponseItem
import retrofit2.http.GET

interface BoardGamesService {

    @GET("/61980fb98326045a5690d1df/61dc0971fb94e004fa03b0af_board_games.txt")
    suspend fun getBoardGamesList(): List<BoardGamesListResponseItem>
}
