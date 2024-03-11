package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.chummerua.useCaseFileTemplatesPlugin.UseCaseModule
import com.chummerua.useCaseFileTemplatesPlugin.UseCaseType
import com.chummerua.useCaseFileTemplatesPlugin.ui.kotlinClassChooser
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.modules
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiDirectory
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.layout.selected
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.swing.Action
import javax.swing.JComponent
import kotlin.coroutines.CoroutineContext

class CreateUseCaseDialog(
    private val project: Project,
    private val directory: PsiDirectory,
    private val useCaseModule: UseCaseModule,
    useCaseType: UseCaseType
) : DialogWrapper(project) {

    private val dispatchers = listOf(
        Dispatchers.Main,
        Dispatchers.Default,
        Dispatchers.IO,
        Dispatchers.Unconfined
    )

    private val config = UseCaseConfig(useCaseType)

    private val module: Module
        get() = project.modules.first { it.name.contains(useCaseModule.title) }

    private val dispatcherRenderer = listCellRenderer<CoroutineContext> {
        text = it.toString()
    }

    init {
        title = "New UseCase"
        init()
    }

    override fun createCenterPanel(): JComponent = panel {
        lateinit var overrideCheckBox: JBCheckBox
        lateinit var dispatcherComboBox: ComboBox<CoroutineDispatcher>

        row("Name") {
            textField()
                .align(Align.FILL)
                .focused()
                .whenTextChangedFromUi {
                    config.useCaseName = it
                }
        }
        separator()

        config.useCaseType.parameters.map { parameter ->
            row(parameter.name) {
                kotlinClassChooser(
                    module,
                    "",
                    onElementSelected = { parameter.element = it },
                    onTextChanged = { parameter.value = it }
                ).align(Align.FILL)
            }
        }
        separator()

        row {
            checkBox("Override dispatcher")
                .bindSelected(config::overrideDispatcher)
                .whenStateChangedFromUi {
                    config.overrideDispatcher = it
                    updateDefaultDispatcherIfNeeded(dispatcherComboBox)
                }
                .also { overrideCheckBox = it.component }
        }
        row("Dispatcher") {
            comboBox(dispatchers, dispatcherRenderer)
                .align(Align.FILL)
                .whenItemSelectedFromUi {
                    config.dispatcher = it
                }
                .also { dispatcherComboBox = it.component }
        }.enabledIf(overrideCheckBox.selected)

        updateDefaultDispatcherIfNeeded(dispatcherComboBox)
    }

    private fun updateDefaultDispatcherIfNeeded(comboBox: ComboBox<CoroutineDispatcher>) = with(config) {
        if (!overrideDispatcher)
            comboBox.selectedItem = useCaseType.defaultCoroutineDispatcher
    }

    override fun getOKAction(): Action {
        return CreateUseCaseAction(directory, project, "Ok", config)
    }
}
