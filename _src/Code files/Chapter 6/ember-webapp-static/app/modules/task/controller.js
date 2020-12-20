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
		createNewTask: function() {
			this.set("_editingTask", null);
			this.set("editingTask", Ember.Object.create({
				name: null, 
				priority: 0,
				status: "Open",
				createdBy: null,
				createdDate: new Date(),
				assignee: null,
				completedDate: null,
				comments: null,
			}));
			Ember.$("#taskEditModal").modal("show");
		},
		showTask: function(_task) {
			this.set("_editingTask", _task);
			this.set("editingTask", Ember.Object.create(
					_task.getProperties("id", "name", "priority", "status",
				"createdBy", "createdDate", "assignee", "completedDate",
				"comments")));
			Ember.$("#taskViewModal").modal("show");
		},
		editTask: function(_task) {
			this.actions.closeViewModal.call(this);
			this.set("_editingTask", _task);
			this.set("editingTask", Ember.Object.create(
					_task.getProperties("id", "name", "priority", "status",
				"createdBy", "createdDate", "assignee", "completedDate",
				"comments")));
			Ember.$("#taskEditModal").modal("show");
		},
		changeCreatedBy: function(_userId) {
			this.get("editingTask").set("createdBy", this.get("allUsers").findBy("id", _userId));
		},
		changeAssignee: function(_userId) {
			this.get("editingTask").set("assignee", this.get("allUsers").findBy("id", _userId));
		},
		closeEditModal: function() {
			Ember.$("#taskEditModal").modal("hide");
			this.set("editingTask", null);
			this.set("_editingTask", null);
		},
		closeViewModal: function() {
			Ember.$("#taskViewModal").modal("hide");
			this.set("editingTask", null);
			this.set("_editingTask", null);
		},
		deleteTask: function(_task) {
			if(confirm("Task, " + _task.get("name") + " will be permanently removed from the system. Proceed ?")) {
				var _this = this;
				_task.destroyRecord().then(function() {
					_this.set("editingTask", null);
					_this.set("_editingTask", null);
					_this.set("model", _this.store.findAll('task'));
				});
			}
		},
		saveTask: function() {
			if(this.get("_editingTask") === null) {
				this.set("_editingTask", this.store.createRecord("task", this.get("editingTask").getProperties("id", "name", "priority", "status",
						"createdBy", "createdDate", "assignee", "completedDate",
						"comments")));
			} else {
				this.get("_editingTask").setProperties(this.get("editingTask")
						.getProperties("name", "priority", "status",
								"createdBy", "createdDate", "assignee", "completedDate",
								"comments"));
			}
			this.get("_editingTask").save();
			this.actions.closeEditModal.call(this);
		}
	}
});
