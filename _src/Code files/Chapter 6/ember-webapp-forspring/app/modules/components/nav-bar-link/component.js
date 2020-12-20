import Ember from 'ember';

export default Ember.Component.extend({
	tagName: "li",
//	classNames: ["active"],
	classNameBindings: ["isCurrentRoute:active"],
	routeName: null,
	isCurrentRoute: Ember.computed('_controller.currentPath', function() {
	    return this.get('_controller.currentPath') === this.get("routeName");
	  }),
	
});
