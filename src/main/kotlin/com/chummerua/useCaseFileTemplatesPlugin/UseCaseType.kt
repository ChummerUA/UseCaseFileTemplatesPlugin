package com.chummerua.useCaseFileTemplatesPlugin

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModules.DB
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModules.DOMAIN
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModules.NETWORK
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModules.PREFERENCES

enum class UseCaseType(
    val title: String,
    val supportedModule: String? = null
) {
    EXECUTABLE("Executable", DOMAIN),
    HTTP("Http", NETWORK),
    DB_FLOW("Flow", DB),
    DB_INSERT("Insert", DB),
    DB_ITEM("Item", DB),
    DB_TRANSACTION("Transaction", DB),
    PREFERENCES_FLOW("Flow", PREFERENCES),
    PREFERENCES_ITEM("Item", PREFERENCES),
    PREFERENCES_REMOVE("Remove", PREFERENCES)
}

object UseCaseModules {
    const val DOMAIN = "domain"
    const val DB = "db"
    const val NETWORK = "network"
    const val PREFERENCES = "preferences"
}
