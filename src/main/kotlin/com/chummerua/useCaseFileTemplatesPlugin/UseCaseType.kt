package com.chummerua.useCaseFileTemplatesPlugin

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule.DB
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule.DOMAIN
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule.NETWORK
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule.PREFERENCES

enum class UseCaseType(
    val title: String,
    val supportedModule: UseCaseModule
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

enum class UseCaseModule(val title: String) {
    DOMAIN("domain"),
    DB("db"),
    NETWORK("network"),
    PREFERENCES("preferences")
}
