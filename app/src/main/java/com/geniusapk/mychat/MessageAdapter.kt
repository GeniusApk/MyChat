package com.geniusapk.mychat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geniusapk.mychat.databinding.ReceiveBinding
import com.geniusapk.mychat.databinding.SentBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        if (viewType == 1 ){
            val binding = ReceiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ReceiveViewHolder(binding.root)

        }else{
            val binding = SentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SentViewHolder(binding.root)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]  // current message

        if (holder.javaClass == SentViewHolder::class.java) {   // if the view holder is of type sent view holder
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        } else {
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }


    }

    override fun getItemViewType(position: Int): Int {
        val CurrentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(CurrentMessage.senderId)) {
            return ITEM_SENT
        } else {
            return ITEM_RECEIVE
        }
    }



    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sentMessage = itemView.findViewById<TextView>(R.id.text_sent_message)

    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val receiveMessage = itemView.findViewById<TextView>(R.id.text_receive_message)


    }

}