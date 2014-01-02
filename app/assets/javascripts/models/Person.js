/**
 * Person view model class.
 * 
 * @class Person
 */
var Person = Backbone.Model.extend(
/** @lends Person# */
{
	/**
	 * Use in model url method.
	 */
	urlRoot : jsRoutes.controllers.UserManagerController.create().absoluteURL()
});
