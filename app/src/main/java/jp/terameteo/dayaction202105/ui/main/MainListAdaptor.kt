package jp.terameteo.dayaction202105.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.terameteo.dayaction202105.MainViewModel
import jp.terameteo.dayaction202105.R
import jp.terameteo.dayaction202105.databinding.ItemTestBinding
import jp.terameteo.dayaction202105.model.TodayItemEntity
import jp.terameteo.dayaction202105.valueOrZero

class MainListAdaptor(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: MainViewModel,
    private val page:Int
) :
    androidx.recyclerview.widget.ListAdapter<TodayItemEntity, MainListAdaptor.ViewHolderOfCell>(DiffCallback) {
    override fun getItemCount(): Int = viewModel.currentItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOfCell {
        // リストの表示要求があったとき､viewTypeに応じて必要なViewHolderを確保する｡
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderOfCell(ItemTestBinding.inflate(layoutInflater, parent, false))
    }
    class ViewHolderOfCell( val binding: ItemTestBinding) :
        RecyclerView.ViewHolder(binding.root)
    // Viewへの参照を保持｡以前は個々の子要素の参照を保存していたが､
    // ViewBindingが使用可能となったので､Bindingのみ保持するようになった｡

    override fun onBindViewHolder(holder: ViewHolderOfCell, position: Int) {
        // リストのPositionの部位の表示要求があったときに､データをViewに設定する｡
        val item = viewModel.currentItems[position]
        val thisCellView = holder.binding.cellText
        thisCellView.text = item.title
        val currentStyle = if (viewModel.isItemDone(item,viewModel.dateEnList[page])) {
            R.drawable.square_gold_gradient
        } else {
            R.drawable.square_silver_gradient
        }
        thisCellView.background = ResourcesCompat.getDrawable(thisCellView.resources, currentStyle, thisCellView.context.theme)

        thisCellView.setOnClickListener {
            val currentValue =  viewModel.currentReward.valueOrZero()
            if ( viewModel.isItemDone(item,viewModel.dateEnList[page])) {
                // アイテムがチェック済み チェックをはずす
                viewModel.removeDateFrom(item,viewModel.dateEnList[page])
                val newValue = currentValue - item.reward
                viewModel.currentReward.postValue(newValue)
            } else {
                viewModel.appendDateTo(item,viewModel.dateEnList[page])
                val newValue = currentValue + item.reward
                viewModel.currentReward.postValue(newValue)
            }
            notifyItemChanged(position)
        }

    }
}

private object DiffCallback : DiffUtil.ItemCallback<TodayItemEntity>() {
    override fun areItemsTheSame(
        oldStoredItem: TodayItemEntity,
        newStoredItem: TodayItemEntity
    ): Boolean {
        return oldStoredItem.id == newStoredItem.id
    }

    override fun areContentsTheSame(
        oldItem: TodayItemEntity,
        newItem: TodayItemEntity
    ): Boolean {
        return oldItem == newItem
    }

}
// adapter 　Fragmentから呼び出され､ViewModelのデータを使ってListを表示する｡
// ViewHolder→ViewからContextを得る｡保持はしない
// Modelには直接アクセスせず､ViewModelに送る｡

// SpannedString ： テキスト･マークアップ共に作成後変更しない｡
// SpannableString ： テキストは変更する｡ 後からスパンをアタッチすることができる｡
// SpannableStringBuilder ： テキストやスパンを作成後に変更する｡ あるいは多数のスパンをアタッチするとき｡
// Span Bold Italic ､ フォント(Monospace､Serif,sans-serif) 文字色､バックグラウンドカラー 下線 取り消し線  テキストサイズの変更