package hu.urban.david.sprintformtest.ui.fragment

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.urban.david.brownie.BrownieViewModel
import hu.urban.david.sprintformtest.local.entity.BoardGameEntity
import hu.urban.david.sprintformtest.network.model.BoardGamesListResponseItem
import hu.urban.david.sprintformtest.repository.BoardGameRepository
import hu.urban.david.sprintformtest.ui.fragment.adapter.BoardGameItem
import hu.urban.david.sprintformtest.ui.fragment.adapter.ItemState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardGamesListViewModel @Inject constructor(
    private val repository: BoardGameRepository
) :
    BrownieViewModel<BoardGamesListUIModel, BoardGamesListUIActions>(::BoardGamesListUIModel) {

    private var boardGameListJob: Job? = null
    override fun handleActions(action: BoardGamesListUIActions) {
        when (action) {
            is BoardGamesListUIActions.Favorite -> addFavoriteGame(action)
            is BoardGamesListUIActions.UnFavorite -> removeFavoriteGame(action)
            is BoardGamesListUIActions.GetBoardGameList -> onGetBoardGameList(action)
        }
    }

    private fun addFavoriteGame(action: BoardGamesListUIActions.Favorite) {
        viewModelScope.launch {
            setLoadingState(action)
            repository.addToFavorite(action.id)
            setSuccessState(action)
        }
    }

    private fun removeFavoriteGame(action: BoardGamesListUIActions.UnFavorite) {
        viewModelScope.launch {
            setLoadingState(action)
            repository.removeFromFavorite(action.id)
            setSuccessState(action)
        }
    }

    private fun onGetBoardGameList(action: BoardGamesListUIActions.GetBoardGameList) {
        boardGameListJob?.cancel()
        boardGameListJob = viewModelScope.launch {
            combine(
                repository.getBoardGameListFromServer(),
                repository.getFavoriteBoardGamesList()
            ) { boardGamesList, favBoardGameEntityList ->
                boardGamesList.map { responseItem ->
                    BoardGameItem(
                        id = responseItem.id,
                        name = responseItem.name,
                        img = responseItem.image,
                        itemState = getFavoriteState(favBoardGameEntityList, responseItem)
                    )
                }
            }.onStart {
                setLoadingState(action)
            }.catch {
                setError(it.localizedMessage, action)
            }.collect { list ->
                val favoriteSortedList =
                    list.filter { item -> item.isFavorite() }.sortedBy { item -> item.name }
                val notFavoriteSortedList =
                    list.filter { item -> item.isNotFavorite() }.sortedBy { item -> item.name }
                uiModel.boardGamesList = favoriteSortedList + notFavoriteSortedList
                setSuccessState(action)
            }
        }
    }

    private fun getFavoriteState(
        favEntityList: List<BoardGameEntity>,
        responseItem: BoardGamesListResponseItem
    ): ItemState {
        return if (favEntityList.any { entity -> entity.boardGameId == responseItem.id }) {
            ItemState.Favorite
        } else {
            ItemState.NotFavorite
        }
    }
}
