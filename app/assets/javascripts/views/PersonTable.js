/**
 * Person table view class.
 * 
 * @class PersonTable
 */
var PersonTable = CollectionView.extend(
/** @lends PersonTable# */
{
	/**
	 * View use collection.
	 */
	collection : persons,
	/**
	 * Backbone DOM element.
	 */
	el : '#person-table-body',
	/**
	 * Create view of each model.
	 * 
	 * @param model
	 *            Backbone model
	 * @return child view
	 */
	createSubView : function(model) {
		return new PersonTableRow({
			"model" : model
		});
	}
});
