package models;

/**
 * Model field meta info. like struct.
 */
public final class AttributeDefinition implements Comparable<AttributeDefinition> {
	/**
	 * Field name.
	 */
	public String name; // SUPPRESS CHECKSTYLE

	/**
	 * Display name in view.
	 */
	public String displayName; // SUPPRESS CHECKSTYLE

	/**
	 * Propertiable create flag.
	 */
	public boolean isCreate; // SUPPRESS CHECKSTYLE

	/**
	 * Propertiable read flag.
	 */
	public boolean isRead; // SUPPRESS CHECKSTYLE

	/**
	 * Propertiable update flag.
	 */
	public boolean isUpdate; // SUPPRESS CHECKSTYLE

	/**
	 * Property require flag.
	 */
	public boolean isRequired; // SUPPRESS CHECKSTYLE

	/**
	 * Numbering Display priority.
	 */
	public int priorityNumber; // SUPPRESS CHECKSTYLE

	@Override
	public int compareTo(final AttributeDefinition obj) {
		AttributeDefinition operand = (AttributeDefinition) obj;

		if (this.priorityNumber < operand.priorityNumber) {
			return -1;
		} else if (this.priorityNumber > operand.priorityNumber) {
			return 1;
		}

		return 0;
	}
}
