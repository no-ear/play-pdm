/**
 * Part version table view class.
 * 
 * @class PartVersionTable
 */
var PartVersionTable = CollectionView.extend(
/** @lends PartVersionTable# */
{
	/**
	 * View use collection.
	 */
	collection : partVersions,
	/**
	 * Backbone DOM element.
	 */
	el : '#part-table-body',
	/**
	 * Create view of each model.
	 * 
	 * @param model
	 *            Backbone model
	 * @return child view
	 */
	createSubView : function(model) {
		return new PartVersionTableRow({
			"model" : model
		});
	}
});
