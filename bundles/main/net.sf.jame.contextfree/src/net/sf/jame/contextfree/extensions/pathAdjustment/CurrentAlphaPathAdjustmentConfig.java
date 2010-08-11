/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.pathAdjustment;

import java.lang.Boolean;
import java.lang.Float;
import java.util.ArrayList;
import java.util.List;
import net.sf.jame.core.common.BooleanElement;
import net.sf.jame.core.common.FloatElement;
import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.contextfree.cfdg.pathAdjustment.extension.PathAdjustmentExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class CurrentAlphaPathAdjustmentConfig extends PathAdjustmentExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement valueElement;
	private BooleanElement targetElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		valueElement = new FloatElement(0f);
		targetElement = new BooleanElement(false);
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(valueElement);
		elements.add(targetElement);
		return elements;
	}

	/**
	 * @return
	 */
	public FloatElement getValueElement() {
		return valueElement;
	}
	
	/**
	 * @return
	 */
	public Float getValue() {
		return valueElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setValue(final Float value) {
		valueElement.setValue(value);
	}
	/**
	 * @return
	 */
	public BooleanElement getTargetElement() {
		return targetElement;
	}
	
	/**
	 * @return
	 */
	public Boolean isTarget() {
		return targetElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setTarget(final Boolean value) {
		targetElement.setValue(value);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final CurrentAlphaPathAdjustmentConfig other = (CurrentAlphaPathAdjustmentConfig) obj;
		if (valueElement == null) {
			if (other.valueElement != null) {
				return false;
			}
		}
		else if (!valueElement.equals(other.valueElement)) {
			return false;
		}
		if (targetElement == null) {
			if (other.targetElement != null) {
				return false;
			}
		}
		else if (!targetElement.equals(other.targetElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public CurrentAlphaPathAdjustmentConfig clone() {
		final CurrentAlphaPathAdjustmentConfig config = new CurrentAlphaPathAdjustmentConfig();
		config.setValue(getValue());
		config.setTarget(isTarget());
		return config;
	}

	@Override
	public void toCFDG(StringBuilder builder) {
		if (valueElement.getValue() != null) {
			builder.append("a ");
			builder.append(valueElement.getValue());
		}
		if (targetElement.getValue() != null) {
			builder.append(targetElement.getValue() ? "|" : "");
		}
	}
}