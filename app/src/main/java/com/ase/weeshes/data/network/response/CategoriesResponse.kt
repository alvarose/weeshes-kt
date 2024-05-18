package com.ase.weeshes.data.network.response

import com.ase.weeshes.domain.model.Category

data class CategoriesResponse(
    val id: String = "",
    val name: String = "",
    val icon: String = "",
) {
    fun toDomain(): Category = Category(
        id = id,
        name = name,
        icon = icon
    )
}
