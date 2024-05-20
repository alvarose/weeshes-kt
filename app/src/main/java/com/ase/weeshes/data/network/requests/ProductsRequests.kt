package com.ase.weeshes.data.network.requests

fun newProductRequest(
    id: String,
    name: String,
    link: String,
    category: String,
) = hashMapOf(
    "id" to id,
    "name" to name,
    "link" to link,
    "category" to category
)

