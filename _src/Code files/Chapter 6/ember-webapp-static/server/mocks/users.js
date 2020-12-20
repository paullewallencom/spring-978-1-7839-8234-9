/*jshint node:true*/
module.exports = function(app) {
  var express = require('express');
  var usersRouter = express.Router();

app.use('/api/v1/user', usersRouter);

var bodyParser = require('body-parser')
app.use( bodyParser.json() );       // to support JSON-encoded bodies
//app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
//  extended: true
//}));
app.use('/api/v1/user', bodyParser.json());

  
  
  
  usersRouter.get('/', function(req, res) {
	    res.send({
	    	'users': app.userDB.userList
	    });
  });

  usersRouter.post('/', bodyParser.json(), function(req, res) {
	    res.send({
	        'user': app.userDB.addNewUser(req.body.user)
	      });
  });

  usersRouter.get('/:id', function(req, res) {
    res.send({
      'user': app.userDB.findById(req.params.id)
    });
  });

  usersRouter.put('/:id', bodyParser.json(), function(req, res) {
	  
    res.send({
      'user': app.userDB.updateUserById(req.params.id, req.body.user)
    });
  });

  usersRouter.delete('/:id', function(req, res) {
	  console.log("req.params = " + JSON.stringify(req.params));
	  console.log("req.body = " + JSON.stringify(req.body));
	  app.userDB.deleteUserById(req.params.id);
    res.status(204).end();
  });

  // The POST and PUT call will not contain a request body
  // because the body-parser is not included by default.
  // To use req.body, run:

  // npm install --save-dev body-parser

  // After installing, you need to `use` the body-parser for
  // this mock uncommenting the following line:
  //
  // app.use('/api/users', require('body-parser'));
  // app.use('/api/v1/user', usersRouter);
};
