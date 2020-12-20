import Ember from 'ember';

export default Ember.Controller.extend({
	_editingUser: null,
	editingUser: null,
	
	modalTitle: Ember.computed("editingUser", function() {
		if(this._editingUser === null) {
			return "Create new user";
		} else {
			return "Edit this user";
		}
	}),
});
