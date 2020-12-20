import Ember from 'ember';

export default Ember.Route.extend({
	model: function() {
	    return this.store.findAll('user');
	  },
		
		actions: {
			createNewUser: function() {
				this.controller.set("_editingUser", null);
				this.controller.set("editingUser", Ember.Object.create({
					name: null, 
					userName: null,
					password: null, 
					dateOfBirth: new Date()
				}));
				Ember.$("#userEditModal").modal("show");
			},
			showUser: function(_user) {
				this.controller.set("_editingUser", _user);
				this.controller.set("editingUser", Ember.Object.create(
						_user.getProperties("id", "name", "userName", 
								"password", "dateOfBirth", "profileImage")));
				Ember.$("#userViewModal").modal("show");
			},
			editUser: function(_user) {
				this.actions.closeViewModal.call(this);
				this.controller.set("_editingUser", _user);
				this.controller.set("editingUser", Ember.Object.create(
						_user.getProperties("id", "name", "userName", 
								"password", "dateOfBirth", "profileImage")));
				Ember.$("#userEditModal").modal("show");
			},
			deleteUser: function(_user) {
				if(confirm("User, " + _user.get("name") + " will be permanently removed from the system. Proceed ?")) {
					var _this = this.controller;
					_user.destroyRecord().then(function() {
						_this.set("editingUser", null);
						_this.set("_editingUser", null);
						_this.set("model", _this.store.findAll('user'));
					});
				}
			},
			closeEditModal: function() {
				Ember.$("#userEditModal").modal("hide");
				this.controller.set("editingUser", null);
				this.controller.set("_editingUser", null);
			},
			closeViewModal: function() {
				Ember.$("#userViewModal").modal("hide");
				this.controller.set("editingUser", null);
				this.controller.set("_editingUser", null);
			},
			saveUser: function() {
				if(this.controller.get("_editingUser") === null) {
					this.controller.set("_editingUser", this.store.createRecord("user", this.controller.get("editingUser").getProperties("id", "name", "userName", 
							"password", "dateOfBirth")));
				} else {
					this.controller.get("_editingUser").setProperties(this.controller.get("editingUser")
							.getProperties("name", "userName", 
									"password", "dateOfBirth"));
				}
				this.controller.get("_editingUser").save();
				this.actions.closeEditModal.call(this);
			}
		}
});
