package com.sina.testchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.testchat.db.Message

class MainAdapter : ListAdapter<Message, MainAdapter.MessageViewHolder>(object : DiffUtil.ItemCallback<Message?>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.text == newItem.text && oldItem.timestamp == newItem.timestamp
    }
}) {

    private var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun submitList(list: List<Message>?) {
        super.submitList(list)
        recyclerView?.let {
            val itemCount = list?.size ?: 0
            it.scrollToPosition(itemCount - 1)
        }
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMessageText: TextView = itemView.findViewById(R.id.tvMessageText)

        fun bind(message: Message) {
            tvMessageText.text = message.text
        }
    }
}