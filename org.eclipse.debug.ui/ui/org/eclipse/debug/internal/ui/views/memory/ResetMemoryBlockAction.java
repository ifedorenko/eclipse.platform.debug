/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.debug.internal.ui.views.memory;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.internal.ui.preferences.IDebugPreferenceConstants;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.memory.IMemoryRenderingContainer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ResetMemoryBlockAction  implements IViewActionDelegate{

	private IViewPart fView;
	private ArrayList fSelectedMB = new ArrayList();

	public void init(IViewPart view) {
		fView = view;
		
	}

	public void run(IAction action) {
		
		if (fSelectedMB.isEmpty())
			return;
		
		boolean resetVisible = false;
		
		String resetPref = DebugUITools.getPreferenceStore().getString(IDebugPreferenceConstants.PREF_RESET_MEMORY_BLOCK);
		if (resetPref.equals(IDebugPreferenceConstants.RESET_VISIBLE))
			resetVisible = true;
		
		Iterator iter = fSelectedMB.iterator();
		
		while(iter.hasNext())
		{
			IMemoryBlock mb = (IMemoryBlock)iter.next();
			if (fView instanceof MemoryView)
			{
				MemoryView memView = (MemoryView)fView;
				IMemoryRenderingContainer[] containers = memView.getMemoryRenderingContainers();
				
				for (int i=0; i<containers.length; i++)
				{
					if (containers[i] instanceof RenderingViewPane)
					{
						((RenderingViewPane)containers[i]).resetRenderings(mb, resetVisible);
					}
				}
			}
		}
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection)
		{
			IStructuredSelection strucSel = (IStructuredSelection)selection;
			Object[] objs = strucSel.toArray();
			
			fSelectedMB.clear();
			for (int i=0; i<objs.length; i++)
			{
				if (objs[i] instanceof IMemoryBlock)
					fSelectedMB.add(objs[i]);
			}
		}
	}

}