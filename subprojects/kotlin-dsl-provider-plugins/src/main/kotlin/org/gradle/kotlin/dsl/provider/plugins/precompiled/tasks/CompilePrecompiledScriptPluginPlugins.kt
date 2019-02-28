/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.kotlin.dsl.provider.plugins.precompiled.tasks

import org.gradle.api.file.Directory
import org.gradle.api.internal.AbstractTask
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

import org.gradle.kotlin.dsl.execution.scriptDefinitionFromTemplate

import org.gradle.kotlin.dsl.support.KotlinPluginsBlock
import org.gradle.kotlin.dsl.support.compileKotlinScriptModuleTo


@CacheableTask
open class CompilePrecompiledScriptPluginPlugins : ClassPathSensitiveTask() {

    @get:OutputDirectory
    var outputDir = directoryProperty()

    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:InputFiles
    val sourceFiles = sourceDirectorySet(
        "precompiled-script-plugin-plugins",
        "Precompiled script plugin plugins"
    )

    fun sourceDir(dir: Provider<Directory>) = sourceFiles.srcDir(dir)

    @TaskAction
    fun compile() {
        outputDir.withOutputDirectory { outputDir ->
            compileKotlinScriptModuleTo(
                outputDir,
                sourceFiles.name,
                sourceFiles.map { it.path },
                scriptDefinitionFromTemplate(
                    KotlinPluginsBlock::class,
                    implicitImportsForPrecompiledScriptPlugins()
                ),
                classPathFiles,
                logger,
                { it } // TODO: translate paths
            )
        }
    }
}


internal
fun AbstractTask.implicitImportsForPrecompiledScriptPlugins() =
    project.implicitImports() + "gradle.kotlin.dsl.plugins.*" // TODO:kotlin-dsl read this value from GenerateExternalPluginSpecBuilder
