package com.example.grocerystore.data.mappers

import com.example.grocerystore.data.model.ProductsFeatureDTO
import com.example.grocerystore.domain.models.ProductsFeature

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