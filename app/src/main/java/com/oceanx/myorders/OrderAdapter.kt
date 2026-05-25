package com.oceanx.myorders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.myorders.databinding.ItemOrderBinding

class OrderAdapter(
    private val onInvoiceClick: (OrderItem) -> Unit,
    private val onBookAgainClick: (OrderItem) -> Unit,
    private val onMoreClick: (OrderItem) -> Unit
) : ListAdapter<OrderItem, OrderAdapter.OrderViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderItem) {
            binding.txtOrderTitle.text = item.title
            binding.txtOrderMeta.text = "${item.meta} | Order ID: ${item.orderId}"
            binding.txtPickup.text = item.pickup
            binding.txtDrop.text = item.drop
            binding.txtPrice.text = item.priceText
            binding.txtStatus.text = item.status.label
            val statusBackground = when (item.status) {
                OrderStatus.CANCELLED -> R.drawable.bg_status_cancelled
                OrderStatus.COMPLETED -> R.drawable.bg_status_completed
                OrderStatus.BOOKED_AGAIN -> R.drawable.bg_status_booked_again
            }
            val statusColor = when (item.status) {
                OrderStatus.CANCELLED -> R.color.status_cancelled_text
                OrderStatus.COMPLETED -> R.color.status_success_text
                OrderStatus.BOOKED_AGAIN -> R.color.status_booked_text
            }
            binding.txtStatus.setBackgroundResource(statusBackground)
            binding.txtStatus.setTextColor(ContextCompat.getColor(binding.root.context, statusColor))

            binding.btnInvoice.setOnClickListener { onInvoiceClick(item) }
            binding.btnBookAgain.setOnClickListener { onBookAgainClick(item) }
            binding.btnMore.setOnClickListener { onMoreClick(item) }
            binding.root.setOnClickListener { onMoreClick(item) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<OrderItem>() {
        override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem == newItem
        }
    }
}
