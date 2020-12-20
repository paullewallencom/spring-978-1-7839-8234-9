/*jshint node:true*/


module.exports = function(app) {
  var express = require('express');
  var tasksRouter = express.Router();
  app.use('/api/v1/task', tasksRouter);
  
  var bodyParser = require('body-parser')
  app.use( bodyParser.json() );       // to support JSON-encoded bodies
  
  app.use('/api/v1/task', bodyParser.json());

  tasksRouter.get('/', function(req, res) {
    res.send({
    	'tasks': app.taskDB.taskList
    });
  });

  tasksRouter.post('/', bodyParser.json(), function(req, res) {
    res.send({
        'task': app.taskDB.addNewTask(req.body.task)
      });
  });

  tasksRouter.get('/:id', function(req, res) {
	res.send({
	    'task': app.taskDB.findById(req.params.id)
	  });
  });

  tasksRouter.put('/:id', bodyParser.json(), function(req, res) {
     res.send({
        'task': app.taskDB.updateTaskById(req.params.id, req.body.task)
      });
  });

  tasksRouter.delete('/:id', function(req, res) {
	  app.taskDB.deleteTaskById(req.params.id);
	    res.status(201).send({});

  });

  // The POST and PUT call will not contain a request body
  // because the body-parser is not included by default.
  // To use req.body, run:

  //    npm install --save-dev body-parser

  // After installing, you need to `use` the body-parser for
  // this mock uncommenting the following line:
  //
  //app.use('/api/tasks', require('body-parser'));
//  app.use('/api/tasks', tasksRouter);
};
