package hu.urban.david.sprintformtest.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.urban.david.sprintformtest.local.dao.FavoriteBoardGameDao
import hu.urban.david.sprintformtest.local.entity.BoardGameEntity

@Database(
    entities = [BoardGameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BoardGamesRoomDatabase : RoomDatabase() {

    abstract fun getFavoriteBoardGameDao(): FavoriteBoardGameDao

    companion object {
        @Volatile
        private var INSTANCE: BoardGamesRoomDatabase? = null

        fun getInstance(context: Context): BoardGamesRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BoardGamesRoomDatabase::class.java,
                        "board_games_room_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
