/**
 * Part version create dialog.
 * 
 * @class PartVersionPropertyCreateDialog
 */
var PartVersionPropertyCreateDialog = Backbone.View.extend(
/** @lends PartVersionPropertyCreateDialog# */
{
	/**
	 * Backbone DOM element.
	 */
	el : "#create-part-dialog",
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
		_.bindAll(this, "onSaveSuccess");

		this.listenTo(mediator, 'selectedPartVersionClass', this.onSelectedPartVersionClass);
	},
	/**
	 * Submit event.
	 * 
	 * @param e
	 *            jQuery.Event object
	 */
	onSubmit : function(e) {
		// Not run normal process. Not bubbling.
		e.preventDefault();

		// Get form parameter.
		var form = this.$el;
		var param = {};

		$(form.serializeArray()).each(function(indexInArray, valueOfElement) {
			param[valueOfElement.name] = valueOfElement.value;
		});

		$.ajax({
			type : "POST",
			url : "partmanager/" + this.selectedClassName,
			data : JSON.stringify(param),
			success : this.onSaveSuccess,
			contentType : "application/json"
		});

		this.$el.modal('hide');
	},
	/**
	 * New part create success callback.
	 * 
	 * @param model
	 *            Add new model
	 * @param response
	 *            response
	 */
	onSaveSuccess : function(model, response) {
		partVersion.add(model);
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
