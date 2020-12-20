import ApplicationAdapter from './application';

export default ApplicationAdapter.extend({
	 /*findAll: function(store, type, sinceToken) {
		 console.log("Inside findAll...");
		    var query, url;

		    if (sinceToken) {
		      query = { since: sinceToken };
		    }

		    url = this.buildURL(type.modelName, null, null, 'findAll');
			 console.log("Inside findAll...url = " + url);
			 console.log("Inside findAll...query = " + query);

		    return this.ajax(url, 'GET', { data: query });
		  },*/
});
