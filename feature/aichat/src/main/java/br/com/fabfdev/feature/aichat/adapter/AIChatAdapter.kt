package br.com.fabfdev.feature.aichat.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.fabfdev.core.domain.model.AIChatText
import br.com.fabfdev.feature.aichat.R
import br.com.fabfdev.feature.aichat.databinding.ItemAiChatBalloonBinding
import br.com.fabfdev.feature.aichat.databinding.ItemUserChatBalloonBinding
import io.noties.markwon.Markwon

private const val AI_ANSWER_CLIP_DATA_LABEL = "Resposta da IA copiada!"

class AIChatAdapter : ListAdapter<AIChatText, AIChatAdapter.AIChatViewHolder>(
    AIChatTextDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AIChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
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
        return when (getItem(position)) {
            is AIChatText.AIAnswer -> R.layout.item_ai_chat_balloon
            is AIChatText.UserQuestion -> R.layout.item_user_chat_balloon
        }
    }

    class AIChatViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val clipboardManager =
            binding.root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        fun bindQuestion(question: String) {
            with(binding as ItemUserChatBalloonBinding) {
                tvUserQuestion.text = question
            }
        }

        fun bindAnswer(answer: String) {
            with(binding as ItemAiChatBalloonBinding) {
                val markwon = Markwon.create(binding.root.context)
                markwon.setMarkdown(tvAIAnswer, answer)
                tvAIAnswer.setOnLongClickListener {
                    val clipData = ClipData.newPlainText(AI_ANSWER_CLIP_DATA_LABEL, answer)
                    clipboardManager.setPrimaryClip(clipData)
                    true
                }
            }
        }
    }

}