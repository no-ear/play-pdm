/**
 * Part version view model class.<br>
 * This contains Part + PartVersion attribute.
 * 
 * @class PartVersion
 */
var PartVersion = Backbone.Model.extend(
/** @lends PartVersion# */
{
	/**
	 * Use in model url method.
	 */
	urlRoot : jsRoutes.controllers.PartManagerController.create().absoluteURL()
});
