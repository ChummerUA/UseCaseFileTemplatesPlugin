package com.chummerua.useCaseFileTemplatesPlugin.ui

import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.util.TextFieldCompletionProvider

class KotlinClassAutoCompletionProvider(
    private val project: Project
) : TextFieldCompletionProvider() {
    override fun addCompletionVariants(text: String, offset: Int, prefix: String, result: CompletionResultSet) {
        // TODO implement auto completion
//        val scope = GlobalSearchScope.allScope(project)
//        JavaPsiFacade.getInstance(project).findClasses(text, scope).take(5).forEach {
//            result.addElement(
//                KotlinClassLookupElement(it)
//            )
//        }
    }

    data class KotlinClassLookupElement(
        private val element: PsiElement
    ) : LookupElement() {
        override fun getLookupString(): String {
            return element.text
        }
    }
}
