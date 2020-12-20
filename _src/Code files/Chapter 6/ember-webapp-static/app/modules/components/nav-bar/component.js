import Ember from 'ember';

export default Ember.Component.extend({
	 willRender() {
		 this.set('myUrl', this.get("_controller.currentPath"));
	 },
});
