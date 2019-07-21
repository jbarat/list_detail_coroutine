package co.uk.jbarat.coroutinelistanddetail.feature.postlist

import androidx.recyclerview.widget.DiffUtil

class PostDiffUtilCallback(
        private val oldList: List<SimplePost>,
        private val newList: List<SimplePost>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}