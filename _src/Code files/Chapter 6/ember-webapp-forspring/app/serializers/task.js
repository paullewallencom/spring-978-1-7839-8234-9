import DS from 'ember-data';

export default DS.RESTSerializer.extend(DS.EmbeddedRecordsMixin, {
    attrs: {
    	createdBy: {embedded: 'always'},
    	assignee: {embedded: 'always'},
    },
    normalizeFindAllResponse: function(store, primaryModelClass, payload, id, requestType) {
        return this._super(store, primaryModelClass, { tasks: payload }, id, requestType);
    },
    normalizeSaveResponse: function(store, primaryModelClass, payload, id, requestType) {
    	return this._super(store, primaryModelClass, {task: payload}, id, requestType);
    },
});
