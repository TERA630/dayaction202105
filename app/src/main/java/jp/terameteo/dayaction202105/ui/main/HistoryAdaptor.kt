package jp.terameteo.dayaction202105.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import jp.terameteo.dayaction202105.MainViewModel
import jp.terameteo.dayaction202105.R
import jp.terameteo.dayaction202105.databinding.GridPlainBinding

class HistoryAdaptor(val viewModel:MainViewModel):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return 40
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GridPlainBinding.inflate(layoutInflater, parent, false)
        return  GridViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val gridView = (holder as GridViewHolder).grid as TextView

        val row = position / 8
        val column = position % 8
        when (row){
            0 -> {
                gridView.setBackgroundColor(ResourcesCompat.getColor(gridView.resources,R.color.colorForestGreen,null))
                bindHeaderDate(column,gridView)
            }
            in 1..5 -> {
                if(column == 0) bindItemTitle(row,gridView) else  bindItemLog(column,gridView)
            }

        }
    }
    class GridViewHolder( binding: GridPlainBinding) :RecyclerView.ViewHolder(binding.root){
        val grid = binding.grid
    }

    private fun bindHeaderDate(column:Int,view: TextView){
        if (column == 0 ) view.text = "＼"
        else view.text = viewModel.dateShortList[column - 1]
    }
    private fun bindItemLog(column: Int,view: TextView){
        view.text = column.toString()
    }
    private fun bindItemTitle(row:Int, view: TextView){
        view.text = "Item$row"
    }

}

//  除算　/　　%
