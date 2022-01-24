package hu.urban.david.sprintformtest.repository

import hu.urban.david.sprintformtest.local.dao.FavoriteBoardGameDao
import hu.urban.david.sprintformtest.local.entity.BoardGameEntity
import hu.urban.david.sprintformtest.network.model.BoardGamesListResponseItem
import hu.urban.david.sprintformtest.network.service.BoardGamesService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BoardGameRepository @Inject constructor(
    private val favoriteBoardGameDao: FavoriteBoardGameDao,
    private val service: BoardGamesService
) {

    fun getBoardGameListFromServer(): Flow<List<BoardGamesListResponseItem>> {
        return flow {
            val result = service.getBoardGamesList()
            emit(result)
        }
    }

    fun getFavoriteBoardGamesList() = favoriteBoardGameDao.getFavoriteBoardGamesId()

    suspend fun addToFavorite(id: String) {
        delay(2000L)
        favoriteBoardGameDao.insertFavoriteBoardGame(BoardGameEntity(id))
    }

    suspend fun removeFromFavorite(id: String) {
        delay(2000L)
        favoriteBoardGameDao.deleteFavoriteBoardGame(id)
    }
}
