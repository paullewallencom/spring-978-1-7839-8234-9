import DS from 'ember-data';

export default DS.RESTSerializer.extend(DS.EmbeddedRecordsMixin, {
    attrs: {
    	profileImage: {embedded: 'always'},
    },
    normalizeFindAllResponse: function(store, primaryModelClass, payload, id, requestType) {
        return this._super(store, primaryModelClass, { users: payload }, id, requestType);
    },
    normalizeSaveResponse: function(store, primaryModelClass, payload, id, requestType) {
    	return this._super(store, primaryModelClass, {user: payload}, id, requestType);
    },
});
