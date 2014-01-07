/**
 * Part class selectable row view.
 * 
 * @class CreatePartInput
 */
var CreatePartInput = Backbone.View.extend(
/** @lends CreatePartInput# */
{
	/**
	 * Be used to build the view "el" property.
	 */
	tagName : "div class='form-group'",
	/**
	 * Underscore template.
	 */
	template : _.template($('#part-property-input-template').html()),
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
