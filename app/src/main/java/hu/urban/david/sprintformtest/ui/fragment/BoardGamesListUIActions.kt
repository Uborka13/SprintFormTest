package hu.urban.david.sprintformtest.ui.fragment

import hu.urban.david.brownie.UIActions

sealed class BoardGamesListUIActions : UIActions {
    data class Favorite(val id: String, val position: Int) : BoardGamesListUIActions()
    data class UnFavorite(val id: String, val position: Int) : BoardGamesListUIActions()
    object GetBoardGameList : BoardGamesListUIActions()
}
