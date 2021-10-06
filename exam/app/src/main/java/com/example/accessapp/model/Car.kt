package com.example.accessapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Car(
        @PrimaryKey
        var id: Int? = null,

        var license: String? = null,
        var status: String? = null,
        var seats: Int? = null,
        var driver: String? = null,
        var color: String? = null,
        var cargo: Int? = null
) : RealmObject(){}