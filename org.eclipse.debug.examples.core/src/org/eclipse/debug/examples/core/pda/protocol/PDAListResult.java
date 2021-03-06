/*******************************************************************************
 * Copyright (c) 2008, 2013 Wind River Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Wind River Systems - initial API and implementation
 *     IBM Corporation - bug fixing
 *******************************************************************************/
package org.eclipse.debug.examples.core.pda.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * @see PDADataCommand
 */

public class PDAListResult extends PDACommandResult {

    final public String[] fValues;

    PDAListResult(String response) {
        super(response);
        StringTokenizer st = new StringTokenizer(response, "|"); //$NON-NLS-1$
		List<String> valuesList = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.length() != 0) {
                valuesList.add(token);
            }
        }

        fValues = new String[valuesList.size()];
        for (int i = 0; i < valuesList.size(); i++) {
            fValues[i] = valuesList.get(i);
        }
    }
}
