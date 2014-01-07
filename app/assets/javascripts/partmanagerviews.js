$(function() {

	// Create views
	new ClassTable();

	new PartTableHeader();
	
	new FieldNameSelect();

	// Initialize collections
	partClassTypes.reset(CLASS_TYPE_INITIALIZER);
});
