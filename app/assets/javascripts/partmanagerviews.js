$(function() {

	// Create views
	new ClassTable();

	new PartTableHeader();
	
	new FieldNameSelect();
	
	new CreatePartDialogBody();
	
	new PartVersionPropertyCreateDialog();

	// Initialize collections
	partClassTypes.reset(CLASS_TYPE_INITIALIZER);
});
