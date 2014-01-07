/**
 * Field name select tag collection view.
 * 
 * @class FieldNameSelect
 */
var FieldNameSelect = CollectionView.extend(
/** @lends FieldNameSelect# */
{
	/**
	 * View use collection.
	 */
	collection : attributeDefinitions,
	/**
	 * Backbone DOM element.
	 */
	el : '#field-name-select',
	/**
	 * Create view of each model.
	 * 
	 * @param model
	 *            Backbone model
	 * @return child view
	 */
	createSubView : function(model) {
		return new FieldNameOption({
			"model" : model
		});
	}
});
