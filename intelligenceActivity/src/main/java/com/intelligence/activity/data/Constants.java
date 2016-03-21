/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intelligence.activity.data;

import android.util.Config;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class Constants {
	public static enum machineType{kettle,humidifier,light,attendance,switchs}
	public static enum AttendanceType{Noraml,Absent,Late,Free}
	public static machineType getMachineType(String id){
		return null;
	}
	//public Config.DEVELOPER_MODE

}
