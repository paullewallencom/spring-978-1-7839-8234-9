import Ember from 'ember';

export default Ember.Controller.extend({
	queryParams: ['taskFilter'],
	taskFilter: "All",
	
	_editingTask: null,
	editingTask: null,
	taskPriorities: [],
	taskStatuses: ["Open", "Closed"],
	allUsers: null,
	
	modalTitle: Ember.computed("editingTask", function() {
		if(this._editingTask === null) {
			return "Create new task";
		} else {
			return "Edit this task";
		}
	}),
	loadTaskPriorities: function() {
		for(var _idx=1; _idx<11; _idx++) {
			this.taskPriorities.pushObject(_idx);
		}
	}.on("init"),
	loadUsers: function() {
		this.set("allUsers", this.store.findAll('user'));
	}.on("init"),
	filteredTasks: Ember.computed('taskFilter', 'model', "editingTask", function() {
	    var taskFilter = this.get('taskFilter');
	    var tasks = this.get('model');

	    if (taskFilter) {
	    	if(taskFilter === "All") {
	    		return tasks;
	    	} else {
	    		return tasks.filterBy('status', taskFilter);
	    	}
	    } else {
	    	return tasks;
	    }
	  }),

	actions: {
		changeCreatedBy: function(_userId) {
			this.get("editingTask").set("createdBy", this.get("allUsers").findBy("id", _userId));
		},
		changeAssignee: function(_userId) {
			this.get("editingTask").set("assignee", this.get("allUsers").findBy("id", _userId));
		},
	}
});
