/**
 * Part version table row view.
 * 
 * @class PartVersionTableRow
 */
var PartVersionTableRow = Backbone.View.extend(
/** @lends PartVersionTableRow# */
{
	/**
	 * Be used to build the view "el" property.
	 */
	tagName : "tr",
	/**
	 * Relate callback function to event on DOM root(el property).
	 */
	events : {
		'click' : 'onClick'
	},
	/**
	 * Renders the view template from model data, and updates this.el with the
	 * new HTML.
	 * 
	 * @return A good convention is to return this at the end of render to
	 *         enable chained calls.
	 */
	render : function() {

		attributeDefinitions.each(function(model) {
			var $tableData = $("<td>");

			$tableData.html(this.model.get(model.get("name")));

			this.$el.append($tableData);
		}, this);

		return this;
	},
	/**
	 * Click event.
	 * 
	 * @param e
	 *            jQuery.Event object
	 */
	onClick : function(e) {
		window.open(jsRoutes.controllers.PartVersionDetailController.index(this.model.get("id")).absoluteURL());
	}
});
