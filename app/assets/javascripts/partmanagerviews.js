$(function() {

	// Create views
	new ClassTable();

	new PartTableHeader();

	new FieldNameSelect();

	new CreatePartDialogBody();

	new PartVersionPropertyCreateDialog();

	new PartVersionTable();

	new SimplePartVersionSearchForm({});

	// Initialize collections
	partClassTypes.reset(CLASS_TYPE_INITIALIZER);
});
