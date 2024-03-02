package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseType
import com.chummerua.useCaseFileTemplatesPlugin.ui.KotlinClassChooser
import com.chummerua.useCaseFileTemplatesPlugin.ui.kotlinClassChooser
import com.chummerua.useCaseFileTemplatesPlugin.ui.whenTextChangedFromUi
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.modules
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiDirectory
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.layout.selected
import kotlinx.coroutines.Dispatchers
import javax.swing.Action
import javax.swing.JComponent
import kotlin.coroutines.CoroutineContext

class CreateUseCaseDialog(
    private val project: Project,
    private val directory: PsiDirectory,
    private val useCaseModule: UseCaseModule
) : DialogWrapper(project) {
    private val createUseCaseAction = okAction as CreateUseCaseAction

    private val supportedUseCaseTypes
        get() = UseCaseType.entries.toList().filter { it.supportedModule == useCaseModule }

    private val module: Module
        get() = project.modules.first { it.name.contains(useCaseModule.title) }

    private val useCaseRenderer = listCellRenderer<UseCaseType> {
        text = it.title
    }
    private val dispatcherRenderer = listCellRenderer<CoroutineContext> {
        text = it.toString()
    }

    init {
        title = "UseCase"
        init()
    }

    override fun createCenterPanel(): JComponent = panel {
        row("Name") {
            textField()
                .align(Align.FILL)
                .focused()
                .whenTextChangedFromUi {
                    println("setting usecase name: ${it}, ${createUseCaseAction.useCaseName}")
                    createUseCaseAction.useCaseName = it
                }
        }
        separator()

        row("Input") {
            kotlinClassChooser(module, "") { createUseCaseAction.inputPsi = it }
                .align(Align.FILL)
                .whenTextChangedFromUi { createUseCaseAction.input = it }
        }
        row("Output") {
            kotlinClassChooser(module, "") { createUseCaseAction.outputPsi = it }
                .align(Align.FILL)
                .whenTextChangedFromUi { createUseCaseAction.output = it }
        }
        separator()

        row("Type") {
            comboBox(supportedUseCaseTypes, useCaseRenderer).whenItemSelectedFromUi {
                createUseCaseAction.useCaseType = it
            }.align(Align.FILL)
        }
        separator()

        lateinit var overrideCB: JBCheckBox
        row {
            overrideCB = checkBox("Override dispatcher")
                .bindSelected(createUseCaseAction::overrideDispatcher)
                .whenStateChangedFromUi {
                    createUseCaseAction.overrideDispatcher = it
                }
                .component
        }
        row("Dispatcher") {
            comboBox(
                listOf(
                    Dispatchers.Main,
                    Dispatchers.Default,
                    Dispatchers.IO,
                    Dispatchers.Unconfined
                ),
                dispatcherRenderer
            )
                .align(Align.FILL)
                .whenItemSelectedFromUi {
                    createUseCaseAction.dispatcher = it
                }
        }.enabledIf(overrideCB.selected)
    }

    override fun getOKAction(): Action {
        return CreateUseCaseAction(directory, project, "Ok")
    }
}
