import Ember from 'ember';

export default Ember.Route.extend({
	model: function(args) {
	    return this.store.findAll('task');
	  },
		actions: {
			createNewTask: function() {
				this.controller.set("_editingTask", null);
				this.controller.set("editingTask", Ember.Object.create({
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
				this.controller.set("_editingTask", _task);
				this.controller.set("editingTask", Ember.Object.create(
						_task.getProperties("id", "name", "priority", "status",
					"createdBy", "createdDate", "assignee", "completedDate",
					"comments")));
				Ember.$("#taskViewModal").modal("show");
			},
			editTask: function(_task) {
				this.actions.closeViewModal.call(this);
				this.controller.set("_editingTask", _task);
				this.controller.set("editingTask", Ember.Object.create(
						_task.getProperties("id", "name", "priority", "status",
					"createdBy", "createdDate", "assignee", "completedDate",
					"comments")));
				Ember.$("#taskEditModal").modal("show");
			},
			changeCreatedBy: function(_userId) {
				this.controller.get("editingTask").set("createdBy", this.controller.get("allUsers").findBy("id", _userId));
			},
			changeAssignee: function(_userId) {
				this.controller.get("editingTask").set("assignee", this.controller.get("allUsers").findBy("id", _userId));
			},
			closeEditModal: function() {
				Ember.$("#taskEditModal").modal("hide");
				this.controller.set("editingTask", null);
				this.controller.set("_editingTask", null);
			},
			closeViewModal: function() {
				Ember.$("#taskViewModal").modal("hide");
				this.controller.set("editingTask", null);
				this.controller.set("_editingTask", null);
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
				if(this.controller.get("_editingTask") === null) {
					this.controller.set("_editingTask", this.store.createRecord("task", this.controller.get("editingTask").getProperties("id", "name", "priority", "status",
							"createdBy", "createdDate", "assignee", "completedDate",
							"comments")));
				} else {
					this.controller.get("_editingTask").setProperties(this.controller.get("editingTask")
							.getProperties("name", "priority", "status",
									"createdBy", "createdDate", "assignee", "completedDate",
									"comments"));
				}
				this.controller.get("_editingTask").save();
				this.actions.closeEditModal.call(this);
			}
		}
});
