import Ember from 'ember';

export default Ember.Component.extend({
	classNames: ["modal", "fade"],
	attributeBindings: ['label:aria-label', 'tabindex', 'labelId:aria-labelledby'],
	ariaRole: "dialog",
	tabindex: -1,
	labelId: Ember.computed('id', function() {
		if(Ember.isEmpty(this.get("id"))) {
			this.set("id", this.get("parentView.elementId") + "_Modal");
		}
	    return this.get('id') + "Label";
	  }),
//	modalTitle: "What is my title ?",
	
});
