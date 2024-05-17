package com.ase.weeshes.domain.model

data class Wishlist(
    val id: String = "",
    val name: String,
    val description: String? = null,
    val icon: String,
    val products: List<Product> = emptyList(),
) {
    fun toRequest() = hashMapOf(
        "name" to name,
        "icon" to icon
    )
}