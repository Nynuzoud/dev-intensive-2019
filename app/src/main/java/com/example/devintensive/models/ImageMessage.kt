package com.example.devintensive.models

import com.example.devintensive.extensions.humanizeDiff
import java.util.*

class ImageMessage(id: String,
                   from: User?,
                   chat: Chat,
                   isIncoming: Boolean = false,
                   date: Date = Date(),
                   var image: String?
) : BaseMessage(id, from, chat, isIncoming) {
    override fun formatMessage(): String =
        "id:$id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"} " +
                "изображение \"$image\" ${date.humanizeDiff()}"
}