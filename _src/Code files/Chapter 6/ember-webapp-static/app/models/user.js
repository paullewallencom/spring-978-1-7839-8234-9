import DS from 'ember-data';

export default DS.Model.extend({
	name: DS.attr('string'),
	userName: DS.attr('string'),
	password: DS.attr('string'),
	dateOfBirth: DS.attr('date', { defaultValue: new Date() }),
	profileImage: DS.belongsTo('file')

});
