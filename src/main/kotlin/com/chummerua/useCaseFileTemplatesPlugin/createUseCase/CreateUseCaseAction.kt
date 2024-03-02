package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import org.jdesktop.swingx.UIAction
import java.awt.event.ActionEvent

class CreateUseCaseAction(
    private val directory: PsiDirectory,
    private val project: Project,
    actionName: String,
    private val config: UseCaseConfig
) : UIAction(actionName) {

    override fun actionPerformed(p0: ActionEvent?) {
        with(config) {
            println("Create usecase:\n$useCaseName\n$useCaseType\n$overrideDispatcher\n$dispatcher\n$inputPsi\n$input\n$outputPsi\n$output")
        }
    }
}
