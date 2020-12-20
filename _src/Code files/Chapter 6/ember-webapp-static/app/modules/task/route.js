import Ember from 'ember';

export default Ember.Route.extend({
	model: function(args) {
	    return this.store.findAll('task');
	  }
});
