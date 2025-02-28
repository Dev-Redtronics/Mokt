package dev.redtronics.buildsrc/*
 * MIT License
 * Copyright 2024 Nils Jäkel & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.gradle.ext.ProjectSettings
import org.jetbrains.gradle.ext.TaskTriggersConfig

internal fun onSyncExec(project: Project, task: Task, idea: IdeaModel) {
    idea.project {
        this as ExtensionAware
        configure<ProjectSettings> {
            this as ExtensionAware
            configure<TaskTriggersConfig> {
                afterSync(project.tasks[task.name])
            }
        }
    }
}