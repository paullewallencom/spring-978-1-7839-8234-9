import Ember from 'ember';

export function formatDate(params, hash) {
	if(!Ember.isEmpty(hash.format)) {
		return moment(new Date(params)).format(hash.format);
	}
  return params;
}

export default Ember.Helper.helper(formatDate);
