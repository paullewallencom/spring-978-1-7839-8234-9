/*jshint node:true*/
module.exports = function(app) {
  
  var userDB = {
		  nextId: 1001,
		  userIdList: [],
		  userList: [],
		  users: {},
		  loadInitData: function() {
			  this.addNewUser({
				  name: "Shameer Kunjumohamed",
		    	  userName: "sameerean",
		    	  password: "password123",
		    	  dateOfBirth: new Date(1977, 2, 08),
		    		profileImage: {
		    			id: this.nextId,
		    			name: "18787D5656.jpg"
		    		}
			  });
			  this.addNewUser({
				  name: "Tarun Bhati",
		    	  userName: "tbhati",
		    	  password: "password123",
		    	  dateOfBirth: new Date(1977, 8, 15),
		    		profileImage: {
		    			id: this.nextId,
		    			name: "18787D5656.jpg"
		    		}
			  });
		  },
  		addNewUser: function(_user) {
  			var _id = this.nextId++;
  			this.users[_id] = _user;
  			_user.id = _id;
  			this.userIdList.push(_id);
  			this.userList.push(_user);
  			return _user;
  		},
  		findUserById: function(_id) {
  			return this.users[_id];
  		},
		deleteUserById: function(_id) {
			var _user = this.findUserById(_id);
			if(_user === null) {
				throw "User with id: " + _id + " is not found.";
			}
			this.users[_id] = null;
			
			var _idx = this.userList.indexOf(_user);
			if(_idx > -1) {
				this.userList.splice(_idx, 1);
			}

			_idx = this.userIdList.indexOf(_id);
			if(_idx > -1) {
				this.userIdList.splice(_idx, 1);
			}
  		},
		updateUserById: function(_id, _hash) {
			var _user = this.findUserById(_id);
			if(_user === null) {
				throw "User with id: " + _id + " is not found.";
			}
			_user.name = _hash.name;
			_user.userName = _hash.userName;
			_user.password = _hash.password;
			_user.dateOfBirth = _hash.dateOfBirth;
		}
  };
  
  userDB.loadInitData();
  app.userDB = userDB;
  
  
  var taskDB = {
		  nextId: 1001,
		  taskIdList: [],
		  taskList: [],
		  tasks: {},

		  loadInitData: function() {
			  
			  var _shameer = userDB.findUserById(1001);
			  var _tarun = userDB.findUserById(1002);
			  this.addNewTask({
				  name: "Order Food",
				  priority: 10,
		    	  status: "Open",
		    	  createdBy: _shameer,
				  createdDate: new Date(2015, 12, 02),
				  assignee: _tarun
			  });
			  this.addNewTask({
				  name: "Commit code changes",
				  priority: 5,
		    	  status: "Open",
		    	  createdBy: _shameer,
				  createdDate: new Date(2015, 12, 03),
				  assignee: _tarun
			  });
			  this.addNewTask({
				  name: "Review code changes",
				  priority: 6,
		    	  status: "Open",
		    	  createdBy: _tarun,
				  createdDate: new Date(2015, 12, 04),
				  assignee: _shameer
			  });
			  this.addNewTask({
				  name: "Release project version",
				  priority: 3,
		    	  status: "Open",
		    	  createdBy: _tarun,
				  createdDate: new Date(2015, 12, 05),
				  assignee: _shameer
			  });
		  },
  		addNewTask: function(_task) {
  			var _id = this.nextId++;
  			this.tasks[_id] = _task;
  			_task.id = _id;
  			this.taskIdList.push(_id);
  			this.taskList.push(_task);
  			return _task;
  		},
  		findTaskById: function(_id) {
  			return this.tasks[_id];
  		},
		deleteTaskById: function(_id) {
			var _task = this.findTaskById(_id);
			if(_task === null) {
				throw "Task with id: " + _id + " is not found.";
			}
			this.tasks[_id] = null;
			
			var _idx = this.taskList.indexOf(_task);
			if(_idx > -1) {
				this.taskList.splice(_idx, 1);
			}

			_idx = this.taskIdList.indexOf(_id);
			if(_idx > -1) {
				this.taskIdList.splice(_idx, 1);
			}
  		},
		updateTaskById: function(_id, _hash) {
			var _task = this.findTaskById(_id);
			if(_task === null) {
				throw "Task with id: " + _id + " is not found.";
			}
			_task.name = _hash.name;
			_task.priority = _hash.priority;
			_task.status = _hash.status;
			_task.createdBy = _hash.createdBy;
			_task.createdDate = _hash.createdDate;
			_task.assignee = _hash.assignee;
			_task.completedDate = _hash.completedDate;
			_task.comments = _hash.comments;
		}
  };
  
  taskDB.loadInitData();
  app.taskDB = taskDB;

  
};
