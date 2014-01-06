/**
 * Part table header(thead) view class.
 * 
 * @class PartTableHeader
 */
var PartTableHeader = CollectionView.extend(
/** @lends PersonTableRow# */
{
	/**
	 * View use collection.
	 */
	collection : attributeDefinitions,
	/**
	 * Backbone DOM element.
	 */
	el : '#part-table-header',
	/**
	 * Create view of each model.
	 * 
	 * @param model
	 *            Backbone model
	 * @return child view
	 */
	createSubView : function(model) {
		return new PartTableHeaderData({
			"model" : model
		});
	}
});
