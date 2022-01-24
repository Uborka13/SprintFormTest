package hu.urban.david.sprintformtest.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.urban.david.sprintformtest.R
import hu.urban.david.sprintformtest.databinding.ItemBoardGameBinding

class BoardGamesListAdapter(val onBoardGameItemClickListener: (BoardGameItem, Int) -> Unit) :
    ListAdapter<BoardGameItem, BoardGamesListAdapter.ViewHolder>(BoardGameItemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBoardGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun itemLoading(position: Int) {
        val item = getItem(position)
        item.itemState = ItemState.Loading
        notifyItemChanged(position)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unBind()
    }

    inner class ViewHolder(private val binding: ItemBoardGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(boardGameItem: BoardGameItem) {
            itemView.setOnClickListener {
                onBoardGameItemClickListener(boardGameItem, layoutPosition)
            }
            binding.tvBoardGameName.text = boardGameItem.name
            Glide.with(itemView).load(boardGameItem.img).into(binding.ivBoardGameCover)
            when (boardGameItem.itemState) {
                ItemState.Favorite -> loadFavoriteIcon()
                ItemState.NotFavorite -> loadNotFavoriteIcon()
                ItemState.Loading -> setLoadingUI()
            }
        }

        private fun loadFavoriteIcon() {
            binding.ivBoardGameIsFavorite.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_star,
                    null
                )
            )
            setNormalUI()
        }

        private fun loadNotFavoriteIcon() {
            binding.ivBoardGameIsFavorite.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.ic_star_outline,
                    null
                )
            )
            setNormalUI()
        }

        private fun setLoadingUI() {
            binding.pbProgressBar.visibility = VISIBLE
            binding.ivBoardGameIsFavorite.visibility = GONE
        }

        private fun setNormalUI() {
            binding.pbProgressBar.visibility = GONE
            binding.ivBoardGameIsFavorite.visibility = VISIBLE
        }

        fun unBind() {
            binding.tvBoardGameName.text = null
            binding.ivBoardGameIsFavorite.setImageDrawable(null)
            binding.ivBoardGameCover.setImageDrawable(null)
        }
    }

    companion object BoardGameItemDiffUtil : DiffUtil.ItemCallback<BoardGameItem>() {
        override fun areItemsTheSame(oldItem: BoardGameItem, newItem: BoardGameItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BoardGameItem, newItem: BoardGameItem): Boolean {
            return oldItem.id == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.img == newItem.img
        }
    }
}
