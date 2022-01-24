package hu.urban.david.sprintformtest.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import hu.urban.david.sprintformtest.local.entity.BoardGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBoardGameDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteBoardGame(boardGameEntity: BoardGameEntity)

    @Query("DELETE FROM favorite_board_game_table WHERE boardGameId = :boardGameId")
    suspend fun deleteFavoriteBoardGame(boardGameId: String)

    @Query("SELECT * FROM favorite_board_game_table")
    fun getFavoriteBoardGamesId(): Flow<List<BoardGameEntity>>
}
