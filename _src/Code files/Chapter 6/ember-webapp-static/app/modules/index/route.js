import Ember from 'ember';

export default Ember.Route.extend({
	model: function() {
		var _model = Ember.Object.extend({
			tasks: null,
			openTasks: Ember.computed("tasks", function() {
				var _tasks = this.get("tasks");
				return Ember.isEmpty(_tasks) ? Ember.A([]): _tasks.filterBy("status", "Open");
			}),
			closedTasks: Ember.computed("tasks", function() {
				var _tasks = this.get("tasks");
				return Ember.isEmpty(_tasks) ? Ember.A([]): _tasks.filterBy("status", "Closed");
			}),
			
		}).create();
		
		this.store.findAll('task').then(function(_tasks) {
			_model.set("tasks", _tasks);
			return _model;
		});
	    return _model;
	  }
});
