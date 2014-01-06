/**
 * Part Class table view class.
 * 
 * @class ClassTable
 */
var ClassTable = CollectionView.extend(
/** @lends ClassTable# */
{
	/**
	 * View use collection.
	 */
	collection : partClassTypes,
	/**
	 * Backbone DOM element.
	 */
	el : '#class-table-body',
	/**
	 * Create view of each model.
	 * 
	 * @param model
	 *            Backbone model
	 * @return child view
	 */
	createSubView : function(model) {
		return new ClassTableRow({
			"model" : model
		});
	}
});
