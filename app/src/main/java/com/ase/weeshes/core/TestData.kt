package com.ase.weeshes.core

import com.ase.weeshes.domain.model.Category
import com.ase.weeshes.domain.model.Product
import com.ase.weeshes.domain.model.Wishlist


val wishlistData = listOf(
    Wishlist(
        "1", "Lista de Navidad", "", "\uD83C\uDF84", listOf(
            Product("1", "Producto", "", "1"),
            Product("2", "Producto", "", "3"),
            Product("3", "Producto", "", "2"),
            Product("4", "Producto", "", "4"),
            Product("5", "Producto", "", "4"),
            Product("6", "Producto", "", "3"),
            Product("7", "Producto", "", "1"),
            Product("8", "Producto", "", "2"),
            Product("9", "Producto", "", "2"),
            Product("10", "Producto", "", "2"),
            Product("11", "Producto", "", "2"),
            Product("12", "Producto", "", "2"),
            Product("13", "Producto", "", "2"),
        )
    ),
    Wishlist(
        "2", "Mi Cumpleaños", "", "\uD83C\uDF82", listOf(
            Product("1", "Producto", "", "1"),
            Product("2", "Producto", "", "3"),
            Product("3", "Producto", "", "2"),
        )
    )
)

val categories = listOf(
    Category("1", "Funkos", "\uD83E\uDDF8"),
    Category("2", "Cómics", "\uD83D\uDCD5"),
    Category("3", "Juegos", "\uD83C\uDFB2"),
    Category("4", "Ropa", "\uD83D\uDC55")
).associateBy { it.id }