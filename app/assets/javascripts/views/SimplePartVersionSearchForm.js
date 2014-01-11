/**
 * Part version Search form in menu bar view class.
 * 
 * @class SimplePartVersionSearchForm
 */
var SimplePartVersionSearchForm = Backbone.View.extend(
/** @lends SimplePartVersionSearchForm# */
{
	/**
	 * Backbone DOM element.
	 */
	el : "#simple-search",
	/**
	 * Relate callback function to event on DOM root(el property).
	 */
	events : {
		'submit' : 'onSubmit'
	},
	/**
	 * Initialize.
	 * 
	 * @param options
	 *            Send construct option
	 */
	initialize : function(options) {
		_.bindAll(this, "onSuccess");

		this.listenTo(mediator, 'selectedPartVersionClass', this.onSelectedPartVersionClass);
	},
	/**
	 * Submit event.
	 * 
	 * @param e
	 *            jQuery.Event object
	 */
	onSubmit : function(e) {
		// Not run nomal process. Not bubbling.
		e.preventDefault();

		var name = this.$el.find("[name='name']").val();
		var value = this.$el.find("[name='value']").val();

		jsRoutes.controllers.PartManagerController.readLike(this.selectedClassName, name, value).ajax({
			success : this.onSuccess,
			context : this
		});
	},
	/**
	 * Success search callback function.
	 * 
	 * @param data
	 *            Response data
	 * @param dataType
	 *            Response data type
	 */
	onSuccess : function(data, dataType) {
		partVersions.reset(data);
	},
	/**
	 * Callback that selected part version class.
	 * 
	 * @param selectedClassName
	 *            selected part version class name
	 */
	onSelectedPartVersionClass : function(selectedClassName) {
		this.selectedClassName = selectedClassName;
	}
});
