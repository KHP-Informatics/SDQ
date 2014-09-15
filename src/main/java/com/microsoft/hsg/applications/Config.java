/*
 * Copyright 2011 Microsoft Corp.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.microsoft.hsg.applications;

import com.microsoft.hsg.ApplicationConfig;

/**
 * The Class Config.
 */
public class Config {

	/** The Constant RedirectUrl. */
	public static final String RedirectUrl = 
		ApplicationConfig.getValue("target.auth.redirect");
	
	/** The Constant ActionQS. */
	public static final String ActionQS = 
		ApplicationConfig.getValue("target.auth.actionqs");
	
	/** The Constant ShellUrl. */
	public static final String ShellUrl = 
		ApplicationConfig.getValue("shell.url");
		
}
