import Ember from 'ember';

export function getIfEq(params, hash) {
	if(Ember.isEmpty(hash.param1) || Ember.isEmpty(hash.param2)) {
		return "Invalid inputs: Expected inputs are: param1, param2, valueYes(Optional) and valueNo(Optional)";
	} else {
		if(hash.param1 === hash.param2) {
			return Ember.isEmpty(hash.valueYes) ? true : hash.valueYes;
		} else {
			return Ember.isEmpty(hash.valueNo) ? false : hash.valueNo;
		}
	}
}

export default Ember.Helper.helper(getIfEq);
