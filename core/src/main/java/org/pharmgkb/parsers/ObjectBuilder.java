package org.pharmgkb.parsers;

import javax.annotation.Nonnull;

/**
 * An implementation of the Builder pattern for an object.
 * When subclassing, use the <a href="http://www.artima.com/weblogs/viewpost.jsp?thread=133275">Curiously Recurring Generic Pattern</a>.
 * @author Douglas Myers-Turnbull
 */
public interface ObjectBuilder<T> {
	@Nonnull T build();
}
