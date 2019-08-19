$(function() {
	var options = optionsDesp || {},
		methods = methodsDesp || {},
		events = eventsDesp || {};

	var htmlStr = '';
	for(var i in options) {
		htmlStr+=
		'<tr><td>'+i+'</td>'
           + '<td>'+options[i][0]+'</td>'
           + '<td>'+options[i][1]+'</td>'
        '</tr>';
	}
	$('tbody.options').html(htmlStr);

	htmlStr = '';
	for(var i in methods) {
		htmlStr+=
		'<tr><td>'+i+'</td>'
           + '<td>'+methods[i]+'</td>'
        '</tr>';
	}
	$('tbody.methods').html(htmlStr);

	htmlStr = '';
	for(var i in events) {
		htmlStr+=
		'<tr><td>'+i+'</td>'
           + '<td>'+events[i]+'</td>'
        '</tr>';
	}
	$('tbody.events').html(htmlStr);
});
