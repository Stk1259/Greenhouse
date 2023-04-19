package com.example.greenhouse.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greenhouse.R
import com.example.greenhouse.data.local.chat.Message

class MessagesAdapter(private var messages: List<Message>) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(message: Message) {
        messages += message
        notifyItemInserted(itemCount - 1)
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.message_textview)
        private val bubbleLayout: LinearLayout = itemView.findViewById(R.id.bubble_layout)

        fun bind(message: Message) {
            messageTextView.text = message.text
            bubbleLayout.setBackgroundResource(
                if (message.isSent) R.drawable.outgoing_bubble
                else R.drawable.incoming_bubble
            )
        }
    }
}
