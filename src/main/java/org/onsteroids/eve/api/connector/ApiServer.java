/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.connector;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks a bound java.net.URI as the configure URI to use for API calls.
 *
 * @author Tobias Sarnowski
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiServer {

}
