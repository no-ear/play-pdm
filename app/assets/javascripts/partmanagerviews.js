$(function() {

	// Create views
	new ClassTable();

	new PartTableHeader();

	// Initialize collections
	partClassTypes.reset(CLASS_TYPE_INITIALIZER);
});
