import Ember from 'ember';

export default Ember.Component.extend({
	tagName: "button",
	attributeBindings: ['type'],
	type: "button",
	classNames: ["btn"],
	classNameBindings: ["isActive:btn-primary:btn-default"],
	activeLabel: "On",
	inactiveLabel: "Off",
	isActive: false,
	activateAction: null,
	deactivateActoin: null,
	currentLabel: Ember.computed('isActive', 'activeLabel', 'inactiveLabel', function() {
		return this.get(this.get("isActive") ? "activeLabel" : "inactiveLabel");
	}),
	click: function() {
		var active = this.get("isActive")
		if(active && this.get("deactivateAction") != null) {
			this.get("deactivateAction").call();
		} else if(!active && this.get("activateAction") != null) {
			this.get("activateAction").call();
		}
		this.set("isActive", !active);
	}
	
});
