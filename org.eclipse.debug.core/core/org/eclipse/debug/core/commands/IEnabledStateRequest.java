/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.debug.core.commands;



/**
 * A request to update the enabled state of a command. A enabled state request
 * is passed to a {@link IDebugCommandHandler} to update the enabled state of
 * the handler.
 * <p>
 * Clients than invoke command handlers may implement this interface.
 * </p>
 * @since 3.3
 */
public interface IEnabledStateRequest extends IDebugCommandRequest {

	/**
	 * Sets the enabled state of a command handler.
	 *
	 * @param result whether enabled
	 */
	public void setEnabled(boolean result);
}
