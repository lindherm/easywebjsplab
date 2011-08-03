package org.easyweb.helloworld.model;

// Generated 2008-2-28 15:00:14 by Hibernate Tools 3.2.0.b9

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public abstract class AbstractHelloWorld implements java.io.Serializable {
	String id;

	String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof AbstractHelloWorld))
			return false;
		AbstractHelloWorld castOther = (AbstractHelloWorld) other;
		return new EqualsBuilder().append(id, castOther.id).append(title, castOther.title).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(title).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", id).append("title", title).toString();
	}
}
