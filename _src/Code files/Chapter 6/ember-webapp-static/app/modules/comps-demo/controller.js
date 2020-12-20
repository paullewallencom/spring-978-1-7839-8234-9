import Ember from 'ember';

export default Ember.Controller.extend({
	toggleButtonOn: function() {
		alert("ToggleButton activated..CC!!");
	},
	toggleButtonOff: function() {
		alert("ToggleButton deactivated..CC!!");
	}
});
