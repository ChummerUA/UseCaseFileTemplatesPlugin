package com.chummerua.useCaseFileTemplatesPlugin.ui

import com.intellij.openapi.module.Module
import com.intellij.util.textCompletion.TextFieldWithCompletion

class KotlinClassChooser(
    module: Module,
    value: String,
    oneLineMode: Boolean,
    forceAutoPopup: Boolean,
    showHint: Boolean
) : TextFieldWithCompletion(
        module.project,
        KotlinClassAutoCompletionProvider(module),
        value,
        oneLineMode,
        forceAutoPopup,
        showHint) {
}
