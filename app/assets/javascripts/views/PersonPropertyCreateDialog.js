/**
 * Person create dialog.
 * 
 * @class PersonPropertyCreateDialog
 */
var PersonPropertyCreateDialog = Backbone.View.extend(
/** @lends PersonPropertyCreateDialog# */
{
	/**
	 * Backbone DOM element.
	 */
	el : "#createPersonDialog",
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
	 *            コンストラクタのoptionsへ渡した値です。
	 */
	initialize : function(options) {
		_.bindAll(this, "onSaveSuccess");
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

		// Get form parameter.
		var form = this.$el;
		var param = {};

		$(form.serializeArray()).each(function(indexInArray, valueOfElement) {
			param[valueOfElement.name] = valueOfElement.value;
		});

		var person = new Person();

		person.save(param, {
			success : this.onSaveSuccess,
			wait : true
		});
	},
	/**
	 * New person create success callback.
	 * 
	 * @param model
	 *            Add new model
	 * @param response
	 *            response
	 */
	onSaveSuccess : function(model, response) {
		// TODO Add user collection.
	}
});
