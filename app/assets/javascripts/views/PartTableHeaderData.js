/**
 * Part class selectable row view.
 * 
 * @class PartTableHeaderData
 */
var PartTableHeaderData = Backbone.View.extend(
/** @lends PartTableHeaderData# */
{
	/**
	 * Be used to build the view "el" property.
	 */
	tagName : "td",
	/**
	 * Renders the view template from model data, and updates this.el with the
	 * new HTML.
	 * 
	 * @return A good convention is to return this at the end of render to
	 *         enable chained calls.
	 */
	render : function() {
		this.$el.append(this.model.get("displayName"));
		return this;
	}
});
