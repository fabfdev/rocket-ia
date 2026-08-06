package br.com.fabfdev.rocketia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.fabfdev.rocketia.R
import br.com.fabfdev.rocketia.databinding.ItemAiChatBalloonBinding
import br.com.fabfdev.rocketia.databinding.ItemUserChatBalloonBinding
import br.com.fabfdev.rocketia.domain.model.AIChatText

class AIChatAdapter : ListAdapter<AIChatText, AIChatAdapter.AIChatViewHolder>(
    AIChatTextDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AIChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            R.layout.item_user_chat_balloon -> {
                val binding = ItemUserChatBalloonBinding.inflate(inflater, parent, false)
                AIChatViewHolder(binding)
            }
            R.layout.item_ai_chat_balloon -> {
                val binding = ItemAiChatBalloonBinding.inflate(inflater, parent, false)
                AIChatViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: AIChatViewHolder,
        position: Int
    ) {
        when (val item = getItem(position)) {
            is AIChatText.AIAnswer -> holder.bindAnswer(item.answer)
            is AIChatText.UserQuestion -> holder.bindQuestion(item.question)
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is AIChatText.AIAnswer -> R.layout.item_ai_chat_balloon
            is AIChatText.UserQuestion -> R.layout.item_user_chat_balloon
        }
    }

    class AIChatViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindQuestion(question: String) {
            with(binding as ItemUserChatBalloonBinding) {
                tvUserQuestion.text = question
            }
        }

        fun bindAnswer(answer: String) {
            with(binding as ItemAiChatBalloonBinding) {
                tvAIAnswer.text = answer
            }
        }
    }

}