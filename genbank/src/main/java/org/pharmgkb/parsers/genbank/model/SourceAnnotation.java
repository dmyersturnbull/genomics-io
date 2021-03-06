package org.pharmgkb.parsers.genbank.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

/**
 * Author Douglas Myers-Turnbull
 */
@Immutable
public class SourceAnnotation implements GenbankAnnotation {

	private final String m_name;
	private final String m_formalName;
	private final ImmutableList<String> m_lineage;

	public SourceAnnotation(@Nonnull String name, @Nonnull String formalName, @Nonnull ImmutableList<String> lineage) {
		m_name = name;
		m_formalName = formalName;
		m_lineage = lineage;
	}

	@Nonnull
	public String getName() {
		return m_name;
	}

	@Nonnull
	public String getFormalName() {
		return m_formalName;
	}

	@Nonnull
	public ImmutableList<String> getLineage() {
		return m_lineage;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", m_name)
				.add("formalName", m_formalName)
				.add("lineage", m_lineage)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SourceAnnotation that = (SourceAnnotation) o;
		return Objects.equals(m_name, that.m_name) &&
				Objects.equals(m_formalName, that.m_formalName) &&
				Objects.equals(m_lineage, that.m_lineage);
	}

	@Override
	public int hashCode() {
		return Objects.hash(m_name, m_formalName, m_lineage);
	}
}
