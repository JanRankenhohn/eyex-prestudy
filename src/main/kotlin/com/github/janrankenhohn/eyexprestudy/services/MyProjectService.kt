package com.github.janrankenhohn.eyexprestudy.services

import com.intellij.openapi.project.Project
import com.github.janrankenhohn.eyexprestudy.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
