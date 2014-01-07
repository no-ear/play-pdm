$(function() {

	// Create views
	new ClassTable();

	new PartTableHeader();
	
	new FieldNameSelect();
	
	new CreatePartDialogBody();

	// Initialize collections
	partClassTypes.reset(CLASS_TYPE_INITIALIZER);
});
