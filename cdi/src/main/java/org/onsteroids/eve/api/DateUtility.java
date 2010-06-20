/**
 * Copyright 2010 Tobias Sarnowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onsteroids.eve.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Tobias Sarnowski
 */
public final class DateUtility {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtility.class);

    // CCPs date scheme
    private static final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static Date parse(String date) {
	    try {
		    return dateParser.parse(date);
	    } catch (ParseException e) {
		    throw new IllegalArgumentException(e);
	    }
    }

    public static Date withTimeDifference(Date date, long timeDifference, TimeUnit timeDifferenceUnit) {
        return new Date(date.getTime() + timeDifferenceUnit.toMillis(timeDifference));
    }

    public static Date parse(String date, long timeDifference, TimeUnit timeDifferenceUnit) {
        return withTimeDifference(parse(date), timeDifference, timeDifferenceUnit);
    }

}