/**
 * Search form in menu bar view class.
 * 
 * @class SimplePersonSearchForm
 */
var SimplePersonSearchForm = Backbone.View.extend(
/** @lends SimplePersonSearchForm# */
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

		jsRoutes.controllers.UserManagerController.readLike(name, value).ajax({
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
		persons.reset(data);
	}
});
