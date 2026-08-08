package br.com.fabfdev.core.data.mapper

import br.com.fabfdev.core.data.local.database.AIChatTextEntity
import br.com.fabfdev.core.domain.model.AIChatText
import br.com.fabfdev.core.domain.model.AIChatTextType

fun AIChatTextEntity.toDomain(): AIChatText =
    when(this.from) {
        AIChatTextType.USER_QUESTION.name -> AIChatText.UserQuestion(question = this.text)
        AIChatTextType.AI_ANSWER.name -> AIChatText.AIAnswer(answer = this.text)
        else -> throw IllegalArgumentException("Invalid from value: ${this.from}")
    }

fun List<AIChatTextEntity>.toDomain(): List<AIChatText> =
    this.map { entity -> entity.toDomain() }