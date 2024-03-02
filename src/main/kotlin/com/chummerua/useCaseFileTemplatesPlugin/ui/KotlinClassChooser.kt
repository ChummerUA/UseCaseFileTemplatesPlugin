package com.chummerua.useCaseFileTemplatesPlugin.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.module.Module
import com.intellij.openapi.observable.util.whenItemSelectedFromUi
import com.intellij.psi.PsiElement
import com.intellij.ui.EditorTextField
import com.intellij.ui.LanguageTextField
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.Row
import com.intellij.util.textCompletion.TextFieldWithCompletion
import org.jetbrains.annotations.ApiStatus
import javax.swing.JComboBox
import javax.swing.text.JTextComponent

class KotlinClassChooser(
    module: Module,
    value: String,
    oneLineMode: Boolean,
    forceAutoPopup: Boolean,
    showHint: Boolean,
    onElementSelected: ((PsiElement) -> Unit)
) : TextFieldWithCompletion(
    module.project,
    KotlinClassAutoCompletionProvider(module, onElementSelected),
    value,
    oneLineMode,
    forceAutoPopup,
    showHint
)

@ApiStatus.Experimental
fun <T: EditorTextField> Cell<T>.whenTextChangedFromUi(parentDisposable: Disposable? = null, listener: (String) -> Unit): Cell<T> {
    return applyToComponent { whenTextChangedFromUi(parentDisposable, listener) }
}

fun Row.kotlinClassChooser(
    module: Module,
    text: String,
    onElementSelected: (PsiElement) -> Unit
) = cell(
    KotlinClassChooser(
        module,
        text,
        oneLineMode = true,
        forceAutoPopup = true,
        showHint = false,
        onElementSelected = onElementSelected
    )
)
