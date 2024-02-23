package com.chummerua.useCaseFileTemplatesPlugin.ui

import com.intellij.openapi.project.Project
import com.intellij.util.textCompletion.TextFieldWithCompletion

class KotlinClassChooser(
    project: Project,
    value: String,
    oneLineMode: Boolean,
    forceAutoPopup: Boolean,
    showHint: Boolean) : TextFieldWithCompletion(
        project,
        KotlinClassAutoCompletionProvider(project),
        value,
        oneLineMode,
        forceAutoPopup,
        showHint)
