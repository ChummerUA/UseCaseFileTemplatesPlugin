package com.chummerua.useCaseFileTemplatesPlugin

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule.DB
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule.DOMAIN
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule.NETWORK
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule.PREFERENCES
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

enum class UseCaseType(
    val title: String,
    val defaultCoroutineDispatcher: CoroutineDispatcher,
    val supportedModule: UseCaseModule
) {
    EXECUTABLE("Executable", Dispatchers.Default, DOMAIN),
    HTTP("Http", Dispatchers.IO, NETWORK),
    DB_FLOW("Flow", Dispatchers.IO, DB),
    DB_INSERT("Insert", Dispatchers.IO, DB),
    DB_ITEM("Item", Dispatchers.IO, DB),
    DB_TRANSACTION("Transaction", Dispatchers.IO, DB),
    PREFERENCES_FLOW("Flow", Dispatchers.IO, PREFERENCES),
    PREFERENCES_ITEM("Item", Dispatchers.IO, PREFERENCES),
    PREFERENCES_REMOVE("Remove", Dispatchers.IO, PREFERENCES)
}

enum class UseCaseModule(val title: String) {
    DOMAIN("domain"),
    DB("db"),
    NETWORK("network"),
    PREFERENCES("preferences")
}
