package com.ase.weeshes.domain.model

data class Category(
    val id: String,
    val name: String,
    val icon: String,
) {
    fun toRequest() = hashMapOf(
        "name" to name,
        "icon" to icon
    )
}
