package hu.urban.david.sprintformtest.ui.fragment

import hu.urban.david.brownie.UIModel
import hu.urban.david.sprintformtest.ui.fragment.adapter.BoardGameItem

data class BoardGamesListUIModel(
    var boardGamesList: List<BoardGameItem>? = null
) : UIModel
