package hu.urban.david.sprintformtest.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_board_game_table")
data class BoardGameEntity(
    @PrimaryKey
    @ColumnInfo(name = "boardGameId")
    val boardGameId: String
)
