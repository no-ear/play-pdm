/**
 * 実績ビュー
 * 
 * @class PersonTableRow
 */
var PersonTableRow = Backbone.View.extend(
/** @lends PersonTableRow# */
{
	/**
	 * Be used to build the view "el" property.
	 */
	tagName : "tr",
	/**
	 * Underscore template.
	 */
	template : _.template($('#person-table-row-template').html()),
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
	}
});
