package com.jaehyeon.intinbletestapp.ui.scan

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaehyeon.intinbletestapp.databinding.ItemMainBinding

/**
 * Created by Jaehyeon on 2022/08/29.
 */
@SuppressLint("NotifyDataSetChanged")
class ScanAdapter(
    val onClick: (ScanResult) -> Unit
): RecyclerView.Adapter<ScanAdapter.ScanViewHolder>() {

    private val list = arrayListOf<ScanResult>()

    inner class ScanViewHolder(private val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(result: ScanResult) {
            binding.scanResult = result
            binding.item.setOnClickListener {
                onClick(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder =
        ScanViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateResult(results: List<ScanResult>) {
        list.clear()
        list.addAll(results)
        notifyDataSetChanged()
    }

    fun addResult(result: ScanResult) {

        if (list.isNotEmpty()) {
            (0 until list.size).forEach { i ->
                if (result.device?.toString()?.equals(list[i].device?.toString()!!)!!) return
            }
        }
        list.add(result)
        notifyDataSetChanged()
    }
}