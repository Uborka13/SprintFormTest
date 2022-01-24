package hu.urban.david.sprintformtest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.urban.david.sprintformtest.local.dao.FavoriteBoardGameDao
import hu.urban.david.sprintformtest.local.database.BoardGamesRoomDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesBoardGamesDatabase(@ApplicationContext context: Context): BoardGamesRoomDatabase {
        return BoardGamesRoomDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesFavoriteBoardGameDao(database: BoardGamesRoomDatabase): FavoriteBoardGameDao {
        return database.getFavoriteBoardGameDao()
    }
}
