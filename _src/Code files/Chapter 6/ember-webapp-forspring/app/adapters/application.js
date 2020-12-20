import Ember from 'ember';
import DS from 'ember-data';

export default DS.RESTAdapter.extend({
//	host: 'http://localhost:4200',
	host: 'http://localhost:8080/apiserver-spring-mvc-javaconfig',
	namespace: 'api/v1',
    pathForType: function(type) {
    	console.log(">>>>>>>>>>>> type = " + type);
    	console.log(">>>>>>>>>>>> Ember.String.decamelize(type) = " + Ember.String.decamelize(type));
        return Ember.String.decamelize(type);
      }

});
