package com.ase.weeshes.domain.model

data class Wishlist (
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val products: List<Product>,
)