package jp.terameteo.dayaction202105.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.terameteo.dayaction202105.R
import jp.terameteo.dayaction202105.TodayItemEntity
import jp.terameteo.dayaction202105.databinding.ItemTestBinding

class MainPageListAdaptor(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: MainViewModel
) :
    androidx.recyclerview.widget.ListAdapter<TodayItemEntity, MainPageListAdaptor.ViewHolderOfCell>(
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
        val thisCellView = holder.textView
        val theme = thisCellView.context.theme
        val resource = thisCellView.resources

        thisCellView.text = viewModel.currentItems[position].title
        thisCellView.background = if (viewModel.currentItems[position].isChecked) {
            ResourcesCompat.getDrawable(resource, R.drawable.square_gold_gradient, theme)
        } else {
            ResourcesCompat.getDrawable(resource, R.drawable.square_silver_gradient, theme)
        }
        thisCellView.setOnClickListener {
            viewModel.currentItems[position].isChecked = !viewModel.currentItems[position].isChecked
            notifyItemChanged(position)
        }

    }
}

private object DiffCallback : DiffUtil.ItemCallback<TodayItemEntity>() {
    override fun areItemsTheSame(
        oldStoredItem: TodayItemEntity,
        newStoredItem: TodayItemEntity
    ): Boolean {
        return oldStoredItem.title == newStoredItem.title
    }

    override fun areContentsTheSame(
        oldItem: TodayItemEntity,
        newItem: TodayItemEntity
    ): Boolean {
        return oldItem == newItem
    }

}

// SpannedString ： テキスト･マークアップ共に作成後変更しない｡
// SpannableString ： テキストは変更する｡ 後からスパンをアタッチすることができる｡
// SpannableStringBuilder ： テキストやスパンを作成後に変更する｡ あるいは多数のスパンをアタッチするとき｡
// Span Bold Italic ､ フォント(Monospace､Serif,sans-serif) 文字色､バックグラウンドカラー 下線 取り消し線  テキストサイズの変更｡
//