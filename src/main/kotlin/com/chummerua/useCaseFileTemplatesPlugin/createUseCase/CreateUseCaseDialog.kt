package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseType
import com.chummerua.useCaseFileTemplatesPlugin.ui.KotlinClassChooser
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.modules
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiDirectory
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.layout.selected
import kotlinx.coroutines.Dispatchers
import javax.swing.JComponent

class CreateUseCaseDialog(
    private val project: Project,
    private val directory: PsiDirectory,
    private val useCaseModule: UseCaseModule
) : DialogWrapper(project) {

    private val config = UseCaseConfig()

    private val supportedUseCaseTypes
        get() = UseCaseType.entries.toList().filter { it.supportedModule == useCaseModule }

    private val module: Module
        get() = project.modules.first { it.name.contains(useCaseModule.title) }

    init {
        title = "UseCase"
        init()
    }

    override fun createCenterPanel(): JComponent = panel {
        row("Name") {
            textField()
                .bindText(config::name)
                .align(Align.FILL)
                .focused()
        }

        separator()

        row("Input") {
            cell(KotlinClassChooser(module, "", true, true, showHint = false)).align(Align.FILL)
        }

        row("Output") {
            cell(KotlinClassChooser(module, "", true, true, showHint = false)).align(Align.FILL)
        }

        val useCaseTypeSelectionVisible = supportedUseCaseTypes.size > 1
        separator().visible(useCaseTypeSelectionVisible)
        buttonsGroup("Type") {
            supportedUseCaseTypes.forEach { useCaseType ->
                row {
                    radioButton((useCaseType.title))
                        .bindSelected(
                            getter = { config.useCaseType == useCaseType },
                            setter = { config.useCaseType = useCaseType }
                        )
                }
            }
        }.visible(useCaseTypeSelectionVisible)

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
