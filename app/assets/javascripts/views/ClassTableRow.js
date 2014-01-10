/**
 * Part class selectable row view.
 * 
 * @class ClassTableRow
 */
var ClassTableRow = Backbone.View.extend(
/** @lends ClassTableRow# */
{
	/**
	 * Be used to build the view "el" property.
	 */
	tagName : "tr",
	/**
	 * Underscore template.
	 */
	template : _.template($('#class-table-row-template').html()),
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
		var template = this.template(this.model);
		this.$el.html(template);
		return this;
	},
	/**
	 * Click event callback function.
	 * 
	 * @param e
	 *            jQuery.Event object
	 */
	onClick : function(e) {
		jsRoutes.controllers.PartManagerController.getPartIndex(this.model.get("name")).ajax({
			success : this.onSuccess,
			context : this
		});
	},
	/**
	 * Success search callback function.
	 * 
	 * @param data
	 *            responce data
	 * @param dataType
	 *            data type
	 */
	onSuccess : function(data, dataType) {
		attributeDefinitions.reset(data);

		mediator.trigger("selectedPartVersionClass", this.model.get("name"));
	}
});
