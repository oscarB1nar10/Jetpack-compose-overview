package com.recepie.compose_from_panel_side.domain.util

/**
 * This mapper will allows to map from entity to domain model and also
 * from DTO to domain model and vice versa.
 */
interface DomainMapper <T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T
}