package com.ase.weeshes.data.network.requests

fun newCategoryRequest(
    id: String,
    name: String,
    icon: String,
) = hashMapOf(
    "id" to id,
    "name" to name,
    "icon" to icon
)

