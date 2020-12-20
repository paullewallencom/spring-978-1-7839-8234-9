import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType
});

Router.map(function() {
  this.route('index', {path:'/'});
  this.route('user');
  this.route('task');
});

export default Router;
