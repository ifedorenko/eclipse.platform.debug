 /*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.core.sourcelookup.containers;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.internal.core.sourcelookup.SourceLookupUtils;

/**
 * A project in the workspace. Source is searched for in the root project
 * folder and all folders within the project recursively. Optionally,
 * referenced projects may be searched as well.
 * 
 * TODO: need project browser/workbench adapter
 * 
 * @since 3.0
 */
public class ProjectSourceContainer extends ContainerSourceContainer {

	boolean fReferencedProjects=false;
	/**
	 * Unique identifier for the project source container type
	 * (value <code>org.eclipse.debug.core.containerType.project</code>).
	 */	
	public static final String TYPE_ID = DebugPlugin.getUniqueIdentifier() + ".containerType.project"; //$NON-NLS-1$
	
	/**
	 * Constructs a project source container.
	 * 
	 * @param project the project to search for source in
	 * @param referenced whether referenced projects should be considered
	 */
	public ProjectSourceContainer(IProject project, boolean referenced) {
		super(project, true);
		fReferencedProjects = referenced;
	}
	
	/**
	 * Returns whether referenced projects are considered.
	 * 
	 * @return whether referenced projects are considered
	 */
	public boolean isSearchReferencedProjects() {
		return fReferencedProjects;
	}
	
	/**
	 * Returns the project this source container references.
	 * 
	 * @return the project this source container references
	 */
	public IProject getProject() {
		return (IProject) getContainer();
	}

	/* (non-Javadoc)
	* @see org.eclipse.debug.internal.core.sourcelookup.ISourceContainer#getType()
	*/
	public ISourceContainerType getType() {
		return SourceLookupUtils.getSourceContainerType(ProjectSourceContainer.TYPE_ID);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceContainer#isComposite()
	 */
	public boolean isComposite() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.containers.CompositeSourceContainer#createSourceContainers()
	 */
	protected ISourceContainer[] createSourceContainers() throws CoreException {
		if (isSearchReferencedProjects()) {
			IProject project = getProject();
			IProject[] projects = project.getReferencedProjects();
			ISourceContainer[] folders = super.createSourceContainers();
			List all = new ArrayList(folders.length + projects.length);
			for (int i = 0; i < folders.length; i++) {
				all.add(folders[i]);
			}
			for (int i = 0; i < projects.length; i++) {
				if (project.exists() && project.isOpen()) {
					ProjectSourceContainer container = new ProjectSourceContainer(projects[i], true);
					container.init(getDirector());
					all.add(container);
				}
			}
			return (ISourceContainer[]) all.toArray(new ISourceContainer[all.size()]);
		} 
		return super.createSourceContainers();
	}
}
