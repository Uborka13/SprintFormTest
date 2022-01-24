package hu.urban.david.sprintformtest.ui.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hu.urban.david.brownie.BrownieFragment
import hu.urban.david.brownie.viewBinding
import hu.urban.david.sprintformtest.databinding.FragmentBoardGamesListBinding
import hu.urban.david.sprintformtest.ui.fragment.adapter.BoardGameItem
import hu.urban.david.sprintformtest.ui.fragment.adapter.BoardGamesListAdapter
import hu.urban.david.sprintformtest.ui.fragment.adapter.ItemState

@AndroidEntryPoint
class BoardGamesListFragment : BrownieFragment<BoardGamesListUIModel, BoardGamesListUIActions>() {

    override val viewModel: BoardGamesListViewModel by viewModels()
    override val binding by viewBinding(FragmentBoardGamesListBinding::inflate)

    private val adapter by lazy { BoardGamesListAdapter(::onBoardGameItemClicked) }

    override fun initUI() {
        binding.rvBoardGameList.adapter = adapter
        binding.rvBoardGameList.layoutManager = LinearLayoutManager(context)
        viewModel.addAction(BoardGamesListUIActions.GetBoardGameList)
    }

    private fun onBoardGameItemClicked(boardGameItem: BoardGameItem, position: Int) {
        when (boardGameItem.itemState) {
            ItemState.Favorite -> viewModel.addAction(
                BoardGamesListUIActions.UnFavorite(
                    boardGameItem.id,
                    position
                )
            )
            ItemState.NotFavorite -> viewModel.addAction(
                BoardGamesListUIActions.Favorite(
                    boardGameItem.id,
                    position
                )
            )
            else -> {
                /* no - op */
            }
        }
    }

    override fun onLoading(action: BoardGamesListUIActions) {
        when (action) {
            is BoardGamesListUIActions.Favorite -> onItemLoading(action.position)
            BoardGamesListUIActions.GetBoardGameList -> { /* no - op */
            }
            is BoardGamesListUIActions.UnFavorite -> onItemLoading(action.position)
        }
    }

    private fun onItemLoading(position: Int) {
        adapter.itemLoading(position)
    }

    override fun onSuccess(action: BoardGamesListUIActions) {
        when (action) {
            is BoardGamesListUIActions.Favorite -> {
                /*no - op*/
            }
            BoardGamesListUIActions.GetBoardGameList -> adapter.submitList(uiModel.boardGamesList)
            is BoardGamesListUIActions.UnFavorite -> {
                /*no - op*/
            }
        }
    }

    override fun onDestroyView() {
        binding.rvBoardGameList.adapter = null
        super.onDestroyView()
    }
}
