package org.pharmgkb.parsers.turtle;

import com.google.common.base.MoreObjects;
import org.pharmgkb.parsers.BadDataFormatException;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;


/**
 * A subject, predicate, or object in RDF.
 * @author Douglas Myers-Turnbull
 */
@Immutable
public class Node {

	private String m_value;
	private Optional<String> m_language;
	private Optional<String> m_dataType;

	public Node(@Nonnull String value, @Nonnull Optional<String> language, @Nonnull Optional<String> dataType) {
		m_value = value;
		m_language = language;
		m_dataType = dataType;
	}

	@Nonnull
	public String getValue() {
		return m_value;
	}

	@Nonnull
	public Optional<String> getLanguage() {
		return m_language;
	}

	@Nonnull
	public Optional<String> getDataType() {
		return m_dataType;
	}

	@Nonnull
	public String asString() {
		return m_value + m_dataType.map(s -> "^^" + s).orElse("") + m_language + m_language.map(s -> "@" + s).orElse("");
	}

	/**
	 * @return The value of the literal (without enclosing quotes), unless it's a full URI
	 * @throws BadDataFormatException If the value is neither a URI nor a literal
	 */
	@Nonnull
	public Optional<String> asLiteral() {
		if (m_value.startsWith("\"") && m_value.endsWith("\"")) {
			return Optional.of(m_value.substring(1, m_value.length() - 1));
		}
		if (m_value.startsWith("<") && m_value.endsWith(">")) {
			return Optional.empty();
		}
		throw new BadDataFormatException("Neither a literal nor a URI: " + m_value);
	}

	/**
	 * @return A URI of the value unless it's a true literal
	 * @throws BadDataFormatException If the value should be a URI but isn't valid, or if it's neither a URI nor a literal
	 */
	@Nonnull
	public Optional<URI> asUri() {
		if (m_value.startsWith("<") && m_value.endsWith(">")) {
			String trimmed = m_value.substring(1, m_value.length() - 1);
			try {
				return Optional.of(new URI(trimmed));
			} catch (URISyntaxException e) {
				throw new BadDataFormatException("Failed to parse URI " + trimmed);
			}
		}
		if (m_value.startsWith("\"") && m_value.endsWith("\"")) {
			return Optional.empty();
		}
		throw new BadDataFormatException("Neither a literal nor a URI: " + m_value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		return Objects.equals(m_value, node.m_value) &&
				Objects.equals(m_language, node.m_language) &&
				Objects.equals(m_dataType, node.m_dataType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(m_value, m_language, m_dataType);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("literal", m_value)
				.add("language", m_language)
				.add("dataType", m_dataType)
				.toString();
	}
}
