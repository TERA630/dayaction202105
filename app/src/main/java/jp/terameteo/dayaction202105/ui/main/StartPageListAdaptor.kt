package jp.terameteo.dayaction202105.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.terameteo.dayaction202105.R

class StartPageListAdaptor (private val viewModel: PageViewModel):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int = viewModel.currentItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_test,parent,false)
        return ViewHolderOfCell(itemView)
    }
    class ViewHolderOfCell(_cellView: View) : RecyclerView.ViewHolder(_cellView){
        val textView: TextView = _cellView.findViewById(R.id.cellText)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val thisPositionViewHolder = holder as ViewHolderOfCell
        val thisPositionView = thisPositionViewHolder.textView

        thisPositionView.text = viewModel.currentItems[position].title
    }
}
