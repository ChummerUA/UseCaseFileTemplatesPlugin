package com.chummerua.useCaseFileTemplatesPlugin

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule.*
import com.intellij.psi.PsiElement
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

sealed interface UseCaseType {
    val title: String
    val defaultCoroutineDispatcher: CoroutineDispatcher
    val supportedModule: UseCaseModule
    val parameters: List<UseCaseParameterType>

    data class Executable(
        override val title: String = "Executable",
        override val defaultCoroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
        override val supportedModule: UseCaseModule = DOMAIN,
        override val parameters: List<UseCaseParameterType> = listOf(
            anyUseCaseParameter("Argument"),
            anyUseCaseParameter("Result")
        )
    ): UseCaseType

    data class Http(
        override val title: String = "Http",
        override val defaultCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        override val supportedModule: UseCaseModule = NETWORK,
        override val parameters: List<UseCaseParameterType> = listOf(
            anyUseCaseParameter("Argument"),
            anyUseCaseParameter("Result")
        )
    ): UseCaseType

    data class DbFlow(
        override val title: String = "Flow",
        override val defaultCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        override val supportedModule: UseCaseModule = DB,
        override val parameters: List<UseCaseParameterType> = listOf(
            anyUseCaseParameter("Argument"),
            anyUseCaseParameter("Row"),
            transacterUseCaseParameter()
        )
    ): UseCaseType

    data class DbInsert(
        override val title: String = "Insert",
        override val defaultCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        override val supportedModule: UseCaseModule = DB
    ): UseCaseType {
        override val parameters: List<UseCaseParameterType>
            get() = listOf(
                anyUseCaseParameter("Argument"),
                transacterUseCaseParameter()
            )
    }

    data class DbItem(
        override val title: String = "Item",
        override val defaultCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        override val supportedModule: UseCaseModule = DB,
        override val parameters: List<UseCaseParameterType> = listOf(
            anyUseCaseParameter("Argument"),
            anyUseCaseParameter("Row"),
            transacterUseCaseParameter()
        )
    ): UseCaseType

    data class DbTransaction(
        override val title: String = "Transaction",
        override val defaultCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        override val supportedModule: UseCaseModule = DB,
        var hasResult: Boolean = false
    ): UseCaseType {
        override val parameters: List<UseCaseParameterType>
            get() = listOfNotNull(
                anyUseCaseParameter("Argument"),
                anyUseCaseParameter("Row").takeIf { hasResult },
                transacterUseCaseParameter()
            )
    }

    data class PreferencesFlow(
        override val title: String = "Flow",
        override val defaultCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        override val supportedModule: UseCaseModule = PREFERENCES,
        override val parameters: List<UseCaseParameterType> = listOf(anyUseCaseParameter("Type"))
    ): UseCaseType

    data class PreferencesItem(
        override val title: String = "Item",
        override val defaultCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        override val supportedModule: UseCaseModule = PREFERENCES,
        override val parameters: List<UseCaseParameterType> = listOf(anyUseCaseParameter("Type"))
    ): UseCaseType

    data class PreferencesRemove(
        override val title: String = "Remove",
        override val defaultCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        override val supportedModule: UseCaseModule = PREFERENCES,
        override val parameters: List<UseCaseParameterType> = listOf(anyUseCaseParameter("Type"))
    ): UseCaseType
}

enum class UseCaseModule(val title: String) {
    DOMAIN("domain"),
    DB("db"),
    NETWORK("network"),
    PREFERENCES("preferences")
}

data class UseCaseParameterType(
    val name: String,
    val qualifiedParentType: String? = null,
    var element: PsiElement? = null,
    var value: String = ""
)

private fun transacterUseCaseParameter() = UseCaseParameterType(
    name = "Transacter",
    qualifiedParentType = "app.cash.sqldelight.Transacter"
)

private fun anyUseCaseParameter(name: String) = UseCaseParameterType(
    name = name
)

val UseCaseModule.useCaseTypes
    get() = when (this) {
        DOMAIN -> listOf(UseCaseType.Executable())
        DB -> listOf(
            UseCaseType.DbInsert(),
            UseCaseType.DbFlow(),
            UseCaseType.DbInsert(),
            UseCaseType.DbTransaction()
        )
        NETWORK -> listOf(UseCaseType.Http())
        PREFERENCES -> listOf(
            UseCaseType.PreferencesFlow(),
            UseCaseType.PreferencesItem(),
            UseCaseType.PreferencesRemove()
        )
}
