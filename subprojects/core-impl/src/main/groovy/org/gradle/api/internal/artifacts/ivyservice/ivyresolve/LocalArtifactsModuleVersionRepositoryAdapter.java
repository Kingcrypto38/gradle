/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.api.internal.artifacts.ivyservice.ivyresolve;

import org.gradle.api.internal.artifacts.ivyservice.ArtifactResolveContext;
import org.gradle.api.internal.artifacts.ivyservice.BuildableArtifactResolveResult;
import org.gradle.api.internal.artifacts.ivyservice.BuildableArtifactSetResolveResult;
import org.gradle.api.internal.artifacts.metadata.ComponentArtifactMetaData;
import org.gradle.api.internal.artifacts.metadata.ComponentMetaData;
import org.gradle.api.internal.artifacts.metadata.DependencyMetaData;

/**
 * A wrapper around a {@link org.gradle.api.internal.artifacts.ivyservice.ivyresolve.ModuleVersionRepository} presents a LocalAware interface
 */
public class LocalArtifactsModuleVersionRepositoryAdapter implements LocalArtifactsModuleVersionRepository {
    private final ModuleVersionRepository repository;

    public LocalArtifactsModuleVersionRepositoryAdapter(ModuleVersionRepository repository) {
        this.repository = repository;
    }

    public String getId() {
        return repository.getId();
    }

    public String getName() {
        return repository.getName();
    }

    public void listModuleVersions(final DependencyMetaData dependency, final BuildableModuleVersionSelectionResolveResult result) {
        repository.listModuleVersions(dependency, result);
    }

    public void getDependency(final DependencyMetaData dependency, final BuildableModuleVersionMetaDataResolveResult result) {
        repository.getDependency(dependency, result);
    }

    public void localResolveModuleArtifacts(ComponentMetaData component, ArtifactResolveContext context, BuildableArtifactSetResolveResult result) {
        if (repository instanceof LocalArtifactsModuleVersionRepository) {
            ((LocalArtifactsModuleVersionRepository) repository).localResolveModuleArtifacts(component, context, result);
        }
    }

    public void resolveModuleArtifacts(final ComponentMetaData component, final ArtifactResolveContext context, final BuildableArtifactSetResolveResult result) {
        repository.resolveModuleArtifacts(component, context, result);
    }

    public void resolveArtifact(final ComponentArtifactMetaData artifact, final ModuleSource moduleSource, final BuildableArtifactResolveResult result) {
        repository.resolveArtifact(artifact, moduleSource, result);
    }
}
