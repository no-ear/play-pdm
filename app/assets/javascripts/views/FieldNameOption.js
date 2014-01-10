/**
 * Field name option in select tag.
 * 
 * @class FieldNameOption
 */
var FieldNameOption = Backbone.View.extend(
/** @lends FieldNameOption# */
{
	/**
	 * Be used to build the view "el" property.
	 */
	tagName : "option",
	/**
	 * Renders the view template from model data, and updates this.el with the
	 * new HTML.
	 * 
	 * @return A good convention is to return this at the end of render to
	 *         enable chained calls.
	 */
	render : function() {
		this.$el.html(this.model.get("displayName")).val(this.model.get("name"));
		return this;
	}
});
