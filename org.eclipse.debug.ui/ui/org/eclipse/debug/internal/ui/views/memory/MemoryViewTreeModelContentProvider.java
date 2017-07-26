/*******************************************************************************
 * Copyright (c) 2006, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.debug.internal.ui.views.memory;

import java.util.List;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IMemoryBlockRetrieval;
import org.eclipse.debug.internal.ui.viewers.model.ITreeModelContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.TreeModelContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.TreeModelViewer;
import org.eclipse.debug.internal.ui.viewers.provisional.AbstractModelProxy;

public class MemoryViewTreeModelContentProvider extends TreeModelContentProvider {

	@Override
	protected void updateNodes(IModelDelta[] nodes, int mask, List<AbstractModelProxy> installed) {

		if (getViewer() instanceof TreeModelViewer) {
			for (int i = 0; i < nodes.length; i++) {
				IModelDelta node = nodes[i];
				int flags = node.getFlags();

				if ((mask & ITreeModelContentProvider.CONTROL_MODEL_DELTA_FLAGS) != 0 && (flags & IModelDelta.ADDED) != 0 && (flags & IModelDelta.SELECT) != 0 && node.getElement() instanceof IMemoryBlock) {
					if ((flags & IModelDelta.SELECT) != 0) {
						if (getPresentationContext().getPart() instanceof MemoryView) {
							MemoryView view = (MemoryView) getPresentationContext().getPart();
							if (view.isPinMBDisplay() && !isFirstMemoryBlock()) {
								// turn off select if the view is currently
								// pinned and not the first memory block
								flags |= IModelDelta.SELECT;
								flags ^= IModelDelta.SELECT;
							}
						}
					}

					// override and select the first memory block
					if (isFirstMemoryBlock()) {
						flags |= IModelDelta.SELECT;
					}
				}
				flags = flags & mask;

				if ((flags & IModelDelta.ADDED) != 0) {
					handleAdd(node);
				}
				if ((flags & IModelDelta.REMOVED) != 0) {
					handleRemove(node);
				}
				if ((flags & IModelDelta.CONTENT) != 0) {
					handleContent(node);
				}
				if ((flags & IModelDelta.STATE) != 0) {
					handleState(node);
				}
				if ((flags & IModelDelta.INSERTED) != 0) {
					handleInsert(node);
				}
				if ((flags & IModelDelta.REPLACED) != 0) {
					handleReplace(node);
				}
				if ((flags & IModelDelta.INSTALL) != 0) {
					handleInstall(node, installed);
				}
				if ((flags & IModelDelta.UNINSTALL) != 0) {
					handleUninstall(node);
				}
				if ((flags & IModelDelta.EXPAND) != 0) {
					handleExpand(node);
				}
				if ((flags & IModelDelta.COLLAPSE) != 0) {
					handleCollapse(node);
				}
				if ((flags & IModelDelta.SELECT) != 0) {
					handleSelect(node);
				}
				if ((flags & IModelDelta.REVEAL) != 0) {
					handleReveal(node);
				}
				updateNodes(node.getChildDeltas(), mask, installed);
			}
		}
	}

	private boolean isFirstMemoryBlock() {
		Object input = getViewer().getInput();
		if (input instanceof IMemoryBlockRetrieval) {
			IMemoryBlock[] memoryBlocks = DebugPlugin.getDefault().getMemoryBlockManager().getMemoryBlocks((IMemoryBlockRetrieval) input);
			if (memoryBlocks.length == 1) {
				return true;
			}
		}
		return false;
	}
}
