package org.eclipse.debug.core;

/**********************************************************************
Copyright (c) 2000, 2002 IBM Corp.  All rights reserved.
This file is made available under the terms of the Common Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/cpl-v10.html
**********************************************************************/

/**
 * A launches listener is notified of launches as they
 * are added and removed from the launch manager. Also,
 * when a process or debug target is added to a launch,
 * listeners are notified of a change.
 * <p>
 * This interface is analagous to <code>ILaunchListerner</code>, except
 * notifications are batched to include more than one launch object
 * when possible.
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * @see org.eclipse.debug.core.ILaunch
 * @see org.eclipse.debug.core.ILaunchManager
 * @since 2.1
 */
public interface ILaunchesListener {	
	/**
	 * Notifies this listener that the specified
	 * launches have been removed.
	 *
	 * @param launches the removed launch objects
	 */
	public void launchesRemoved(ILaunch[] launches);
	/**
	 * Notifies this listener that the specified launches
	 * have been added.
	 * 
	 * @param launches the newly added launch objects
	 */
	public void launchesAdded(ILaunch[] launches);	
	/**
	 * Notifies this listener that the specified launches
	 * have changed. For example, a process or debug target
	 * has been added to a launch.
	 * 
	 * @param launches the changed launch object
	 */
	public void launchesChanged(ILaunch[] launches);	
}
