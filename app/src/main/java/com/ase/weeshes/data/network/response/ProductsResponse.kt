package com.ase.weeshes.data.network.response

import com.ase.weeshes.domain.model.Product

data class ProductsResponse(
    val id: String = "",
    val name: String = "",
    val link: String = "",
    val category: String = "",
) {
    fun toDomain(): Product = Product(
        id = id,
        name = name,
        link = link,
        category = category
    )
}
