package br.com.fabfdev.feature.aichat.adapter

import androidx.recyclerview.widget.DiffUtil
import br.com.fabfdev.core.domain.model.AIChatText

class AIChatTextDiffCallback: DiffUtil.ItemCallback<AIChatText>() {
    override fun areItemsTheSame(
        oldItem: AIChatText,
        newItem: AIChatText
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: AIChatText,
        newItem: AIChatText
    ): Boolean {
        return oldItem == newItem
    }

}