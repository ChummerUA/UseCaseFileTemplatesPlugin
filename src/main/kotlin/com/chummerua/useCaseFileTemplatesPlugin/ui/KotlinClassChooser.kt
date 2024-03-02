package com.chummerua.useCaseFileTemplatesPlugin.ui

import com.intellij.openapi.module.Module
import com.intellij.openapi.observable.util.whenFocusLost
import com.intellij.openapi.observable.util.whenKeyReleased
import com.intellij.openapi.observable.util.whenPropertyChanged
import com.intellij.psi.PsiElement
import com.intellij.ui.dsl.builder.Row
import com.intellij.util.textCompletion.TextFieldWithCompletion
import org.jetbrains.kotlin.idea.base.psi.kotlinFqName

class KotlinClassChooser(
    module: Module,
    value: String,
    oneLineMode: Boolean,
    forceAutoPopup: Boolean,
    showHint: Boolean,
    onElementSelected: ((PsiElement) -> Unit),
    private val onTextChanged: (String) -> Unit
) : TextFieldWithCompletion(
    module.project,
    KotlinClassAutoCompletionProvider(module) {
        onElementSelected(it)
        onTextChanged(it.kotlinFqName.toString())
    },
    value,
    oneLineMode,
    forceAutoPopup,
    showHint
) {
    init {
        whenKeyReleased {
            println("key released: $it")
            onTextChanged(text)
        }
        whenFocusLost {
            println("focus lost")
            onTextChanged(text)
        }
    }
}

fun Row.kotlinClassChooser(
    module: Module,
    text: String,
    onElementSelected: (PsiElement) -> Unit,
    onTextChanged: (String) -> Unit
) = cell(
    KotlinClassChooser(
        module,
        text,
        oneLineMode = true,
        forceAutoPopup = true,
        showHint = false,
        onElementSelected = onElementSelected,
        onTextChanged = onTextChanged
    )
)
