package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseType
import com.chummerua.useCaseFileTemplatesPlugin.ui.KotlinClassChooser
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiDirectory
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.layout.selected
import kotlinx.coroutines.Dispatchers
import javax.swing.Action
import javax.swing.JComponent

class CreateUseCaseDialog(
    private val project: Project,
    private val directory: PsiDirectory
) : DialogWrapper(project) {

    private val config = UseCaseConfig()

    val selectedModule: Module
        get() = TODO("Implement selected module getter")
    val supportedUseCaseTypes
        get() = UseCaseType.entries.filter { it.supportedModule == selectedModule.name }

    init {
        title = "UseCase"
        init()
    }

    override fun createCenterPanel(): JComponent = panel {
        row("Name") {
            textField()
                .bindText(config::name)
                .focused()
        }

        row("Input") {
            cell(KotlinClassChooser(project, "", true, true, false)).horizontalAlign(HorizontalAlign.FILL)
        }

        row("Output") {
            cell(KotlinClassChooser(project, "", true, true, false)).horizontalAlign(HorizontalAlign.FILL)
        }

        separator()

        buttonsGroup("Type") {
            UseCaseType.entries.forEach { useCaseType ->
                row {
                    radioButton((useCaseType.title))
                        .bindSelected(
                            getter = { config.useCaseType == useCaseType },
                            setter = { config.useCaseType = useCaseType }
                        )
                }
            }
        }

        separator()

        lateinit var overrideCB: JBCheckBox
        row {
            overrideCB = checkBox("Override dispatcher")
                .bindSelected(config::overrideDispatcher)
                .component
        }

        buttonsGroup("Dispatcher") {
            listOf(
                Dispatchers.Main,
                Dispatchers.Default,
                Dispatchers.IO,
                Dispatchers.Unconfined
            ).forEach { context ->
                row {
                    radioButton(context.toString())
                        .bindSelected(
                            getter = { config.dispatcher == context },
                            setter = { if (it) config.dispatcher = context }
                        )
                        .enabledIf(overrideCB.selected)
                }
            }
        }
    }

    override fun getOKAction(): Action {
        return CreateUseCaseAction(config, directory, project)
    }
}
