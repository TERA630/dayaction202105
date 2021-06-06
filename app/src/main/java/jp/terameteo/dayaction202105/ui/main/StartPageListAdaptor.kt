package jp.terameteo.dayaction202105.ui.main

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.terameteo.dayaction202105.StoredItemEntity
import jp.terameteo.dayaction202105.databinding.ItemTestBinding

class StartPageListAdaptor(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: MainViewModel
) :
    androidx.recyclerview.widget.ListAdapter<StoredItemEntity, StartPageListAdaptor.ViewHolderOfCell>(
        DiffCallback
    ) {
    override fun getItemCount(): Int = viewModel.currentItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOfCell {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderOfCell(ItemTestBinding.inflate(layoutInflater, parent, false))
    }

    class ViewHolderOfCell(private val binding: ItemTestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.cellText
    }

    override fun onBindViewHolder(holder: ViewHolderOfCell, position: Int) {
        val thisPositionView = holder.textView
        thisPositionView.text = viewModel.currentItems[position].title
    }
}

private object DiffCallback : DiffUtil.ItemCallback<StoredItemEntity>() {
    override fun areItemsTheSame(
        oldStoredItem: StoredItemEntity,
        newStoredItem: StoredItemEntity
    ): Boolean {
        return oldStoredItem.id == newStoredItem.id
    }

    override fun areContentsTheSame(
        oldStoredItem: StoredItemEntity,
        newStoredItem: StoredItemEntity
    ): Boolean {
        return oldStoredItem == newStoredItem
    }

}

// SpannedString ： テキスト･マークアップ共に作成後変更しない｡
// SpannableString ： テキストは変更する｡ 後からスパンをアタッチすることができる｡
// SpannableStringBuilder ： テキストやスパンを作成後に変更する｡ あるいは多数のスパンをアタッチするとき｡
// Span Bold Italic ､ フォント(Monospace､Serif,sans-serif) 文字色､バックグラウンドカラー 下線 取り消し線  テキストサイズの変更｡
//