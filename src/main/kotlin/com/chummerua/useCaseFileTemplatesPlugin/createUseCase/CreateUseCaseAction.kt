package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import kotlinx.coroutines.Dispatchers
import org.jdesktop.swingx.UIAction
import java.awt.event.ActionEvent
import kotlin.coroutines.CoroutineContext

class CreateUseCaseAction(
    private val directory: PsiDirectory,
    private val project: Project,
    actionName: String
) : UIAction(actionName) {
    var useCaseName: String = ""
    var useCaseType: UseCaseType = UseCaseType.EXECUTABLE
    var overrideDispatcher: Boolean = false
    var dispatcher: CoroutineContext = Dispatchers.Default
    var inputPsi: PsiElement? = null
    var input: String = ""
    var outputPsi: PsiElement? = null
    var output: String = ""

    override fun actionPerformed(p0: ActionEvent?) {
        println("Create usecase:\n$useCaseName\n$useCaseType\n$overrideDispatcher\n$dispatcher\n$inputPsi\n$input\n$outputPsi\n$output")
    }
}
