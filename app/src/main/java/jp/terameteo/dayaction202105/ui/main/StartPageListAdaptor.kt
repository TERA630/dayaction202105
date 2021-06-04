package jp.terameteo.dayaction202105.ui.main

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.terameteo.dayaction202105.ItemEntity
import jp.terameteo.dayaction202105.databinding.ItemTestBinding

class StartPageListAdaptor (private val viewLifecycleOwner: LifecycleOwner, private val viewModel: PageViewModel):
    androidx.recyclerview.widget.ListAdapter<ItemEntity, StartPageListAdaptor.ViewHolderOfCell>(DiffCallback) {
    override fun getItemCount(): Int = viewModel.currentItems. size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOfCell {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderOfCell(ItemTestBinding.inflate(layoutInflater,parent,false))
    }
    class ViewHolderOfCell(private val binding:ItemTestBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.cellText
    }
    override fun onBindViewHolder(holder: ViewHolderOfCell, position: Int) {
        val thisPositionViewHolder = holder
        val thisPositionView = thisPositionViewHolder.textView
        thisPositionView.text =  viewModel.currentItems[position].title
    }
}
    private object DiffCallback : DiffUtil.ItemCallback<ItemEntity>() {
    override fun areItemsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
        return oldItem == newItem
    }

}