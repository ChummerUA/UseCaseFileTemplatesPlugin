package com.chummerua.useCaseFileTemplatesPlugin.ui

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.lookup.LookupElementPresentation
import com.intellij.codeInsight.lookup.LookupElementRenderer
import com.intellij.openapi.module.Module
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.search.searches.AllClassesSearch
import com.intellij.util.TextFieldCompletionProvider
import org.jetbrains.kotlin.idea.base.psi.kotlinFqName

class KotlinClassAutoCompletionProvider(
    private val module: Module
) : TextFieldCompletionProvider() {
    override fun addCompletionVariants(text: String, offset: Int, prefix: String, result: CompletionResultSet) {
        val scope = ProjectScope.getContentScope(module.project)
        AllClassesSearch
            .search(scope, module.project)
            .filter { it.qualifiedName?.contains(text) == true }
            .forEach {
                val element = LookupElementBuilder.create(it).withRenderer(KotlinLookupRenderer)
                result.addElement(element)
        }
    }

    override fun fillCompletionVariants(parameters: CompletionParameters, prefix: String, result: CompletionResultSet) {
        super.fillCompletionVariants(parameters, prefix, result)
        result.restartCompletionOnAnyPrefixChange()
    }

    object KotlinLookupRenderer : LookupElementRenderer<LookupElement>() {
        override fun renderElement(element: LookupElement?, presentation: LookupElementPresentation?) {
            presentation?.itemText = element?.psiElement?.kotlinFqName.toString()
        }
    }
}
