import DS from 'ember-data';

export default DS.Model.extend({
	name: DS.attr('string'),
	priority: DS.attr('number'),
	status: DS.attr('string'),
	createdBy: DS.belongsTo('user'),
	createdDate: DS.attr('date'),
	assignee: DS.belongsTo('user'),
	completedDate: DS.attr('date'),
	comments: DS.attr('string'),
});
