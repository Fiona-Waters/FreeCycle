package org.wit.freecycle.models

interface FreecycleStore {
    fun findAll(): List<FreecycleModel>
    fun create(listing: FreecycleModel)
    fun update(listing: FreecycleModel)
}