package view.panels.members;

import java.util.Map;

/**
 * Interface for {@link CommonPanel}.
 * 
 * @author Davide Borficchia
 *
 */

public interface ICommonPanel {
	
	/**
	 * 
	 * Sets the filed's value
	 * 
	 * @param field
	 * 		the field who want set
	 * @param value
	 * 		the field's value
	 */
	void setMap(final IFormField field, final String value);
	
	/**
	 * Gets the map of common fields
	 * 
	 * @return the map of fields and values of common fields
	 */
	Map<IFormField, String> getMapToPass();
}
