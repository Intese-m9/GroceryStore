package com.example.grocery_store.data.mappers

import com.example.grocery_store.data.model.ProductsFeatureDTO
import com.example.grocery_store.domain.models.ProductsFeature

class ProductsMapper {
    fun mapToDomain(dto: ProductsFeatureDTO): ProductsFeature {
        return ProductsFeature(
            id = dto.id,
            name = dto.name,
            price = dto.price,
            category = dto.category,
            imageUrl = dto.image_Url
        )
    }
}