package ru.justmc.api.auth.methods.vk

import kotlin.random.Random

data class VkAuthData(
    val name: String,
    val vkId: Int = -1,
    val code: Int = Random.nextInt(1000, 9999),
    val ip: String = ""
)