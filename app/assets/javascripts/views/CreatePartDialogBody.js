/**
 * Create part property dialog view.
 * 
 * @class CreatePartDialogBody
 */
var CreatePartDialogBody = CollectionView.extend(
/** @lends CreatePartDialogBody# */
{
	/**
	 * View use collection.
	 */
	collection : attributeDefinitions,
	/**
	 * Backbone DOM element.
	 */
	el : '#create-part-dialog-body',
	/**
	 * Create view of each model.
	 * 
	 * @param model
	 *            Backbone model
	 * @return child view
	 */
	createSubView : function(model) {
		return new CreatePartInput({
			"model" : model
		});
	}
});
