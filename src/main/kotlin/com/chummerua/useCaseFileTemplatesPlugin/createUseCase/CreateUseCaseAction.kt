package com.chummerua.useCaseFileTemplatesPlugin.createUseCase

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import java.awt.event.ActionEvent
import java.beans.PropertyChangeListener
import javax.swing.Action

class CreateUseCaseAction(
    private val config: UseCaseConfig,
    private val directory: PsiDirectory,
    private val project: Project
) : Action {
    override fun actionPerformed(p0: ActionEvent?) {
        TODO("Not yet implemented")
    }

    override fun getValue(p0: String?): Any {
        TODO("Not yet implemented")
    }

    override fun putValue(p0: String?, p1: Any?) {
        TODO("Not yet implemented")
    }

    override fun setEnabled(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun addPropertyChangeListener(p0: PropertyChangeListener?) {
        TODO("Not yet implemented")
    }

    override fun removePropertyChangeListener(p0: PropertyChangeListener?) {
        TODO("Not yet implemented")
    }
}
