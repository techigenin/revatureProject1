window.onload = function (){
	document.getElementById('requestButton').addEventListener('click', sendRequest);
	document.getElementById('link_createRequest').addEventListener('click', function() {makeVisible(this)});
	document.getElementById('link_requestList').addEventListener('click', function() {makeVisible(this)});
	document.getElementById('link_subsRequests').addEventListener('click', function() {makeVisible(this)});
	document.getElementById('link_bencoRequests').addEventListener('click', function() {makeVisible(this)});
	document.getElementById('eventCost').addEventListener('input', showProjRmbAmt);
	document.getElementById('eventType').addEventListener('change', showProjRmbAmt);
    setGreeting();
    setUserNameTag();
    listRequests();
    listSubsRequests();
    listBenco();
}

let empRequests = [];
let subsRequests = [];
let bencoRequests = [];

function makeVisible(regionID) {
	
	let regions = document.getElementsByClassName('pageRegions');;
	let str;

	for (o of regions)
	{
		o.style.display = 'none';
	}

	str = regionID.id.substring(5);
	
	document.getElementById(str).style.display = 'initial';
	
}

function sendRequest() {

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            listRequests();
        }
    }

    xhr.open('POST', 'http://localhost:8080/msTRMS/msRequest', true);

    xhr.send(getRequestInfoJSON());
}

function submitReqAppDeny() {

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
        	listSubsRequests();
        	listBenco();
        }
    }

    xhr.open('POST', 'http://localhost:8080/msTRMS/MSRequestResponse', true);

    xhr.send(submitReqAppDenyJSON());
}

function setGreeting() {

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
        	document.getElementById('greetings').innerHTML = `Greetings ${xhr.responseText}!`;
        }
    }

    xhr.open('GET', 'http://localhost:8080/msTRMS/MSEmployeeName', true);

    xhr.send();
}

function setUserNameTag() {

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
        	document.getElementById('username').innerHTML = `user:${xhr.responseText + '\t'}`;
        }
    }

    xhr.open('GET', 'http://localhost:8080/msTRMS/MSEmployeeUserID', true);

    xhr.send();
}

function listRequests() {
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = xhr.responseText;
            
            if (response.length != 0 & response[0] != '<'){
                response = JSON.parse(response);
                
              	updateRequestList(response);
            }
        }
    }

    xhr.open('GET', 'http://localhost:8080/msTRMS/msRequestList', true);

    xhr.send();
}

function listSubsRequests() {
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = xhr.responseText;
            
            if (response.length != 0 & response[0] != '<'){
                response = JSON.parse(response);
                
            	updateSubsList(response);
            }
        }
    }

    xhr.open('GET', 'http://localhost:8080/msTRMS/msRequestSubs', true);

    xhr.send();
}

function listBenco() {
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = xhr.responseText;
            
            if (response.length != 0 & response[0] != '<'){
                response = JSON.parse(response);
                
            	updateBencoList(response);
            }
        }
    }

    xhr.open('GET', 'http://localhost:8080/msTRMS/MSBencoReqs', true);

    xhr.send();
}

function getRequestInfoJSON () {
    let eDate = document.getElementById('eventDate').value;
    let eType = document.getElementById('eventType').value;
    let eTime = document.getElementById('eventTime').value;
    let eLoc = document.getElementById('eventLoc').value;
    let eDesc = document.getElementById('eventDesc').value;
    let eCost = document.getElementById('eventCost').value;
	let eGFmt = document.getElementById('eventGFmt').value;
	let eDurr = document.getElementById('eventDuration').value;

    let request = {
        date: eDate,
        type: eType,
        time: eTime,
        loc: eLoc,
        desc: eDesc,
        cost: eCost,
		gfmt: eGFmt,
		durr: eDurr
    }

    let requestJSON = JSON.stringify(request);
    return requestJSON;
}

function showEmpDetails(row) {
	row = empRequests[`${row.id.substring(10)}`];

	let statusVal = row.status;

	for (o of document.getElementsByClassName('empReqDets'))
		o.style.display = 'none';
	
	if (statusVal === 'pending' || statusVal === 'denied')  // Show Normal Details view
	{
	eDate = row['eventDate'];
	eTime = row['eventTime'];
	eDateTime = getDateTimeFromJSON(eDate, eTime);
	aDate = getDateTimeFromJSON(row['appliedDate']);
	
	document.getElementById('rLD-Desc').innerHTML = row.eventDesc;
	document.getElementById('rLD-Loc').innerHTML = row.eventLoc;
	document.getElementById('rLD-DateTime').innerHTML = eDateTime.toLocaleString();
	document.getElementById('rLD-GradeFmt').innerHTML = row.gradeFmt;
	document.getElementById('rLD-Type').innerHTML = row.eventType;
	document.getElementById('rLD-Cost').innerHTML = `$${row.eventCost.toFixed(2)}`;
	document.getElementById('rLD-Reimb').innerHTML = `$${row.rmbAmt.toFixed(2)}`; 
	document.getElementById('rLD-DateApp').innerHTML = aDate.toLocaleDateString();
	document.getElementById('rLD-Status').innerHTML = row.status;	
	document.getElementById('requestListDetails').style.display = 'initial';
	} else if (statusVal === 'approved') { // Show grade submission view
		document.getElementById('submitGradeDetails').style.display = 'initial';
	}
}

function showSubsDetails(row) {
	row = subsRequests[`${row.id.substring(14)}`];
	
	eDate = row['eventDate'];
	eTime = row['eventTime'];
	eDateTime = getDateTimeFromJSON(eDate, eTime);
	aDate = getDateTimeFromJSON(row['appliedDate']);
	
	document.getElementById('rLD-Desc').innerHTML = row.eventDesc;
	document.getElementById('rLD-Loc').innerHTML = row.eventLoc;
	document.getElementById('rLD-DateTime').innerHTML = eDateTime.toLocaleString();
	document.getElementById('rLD-GradeFmt').innerHTML = row.gradeFmt;
	document.getElementById('rLD-Type').innerHTML = row.eventType;
	document.getElementById('rLD-Cost').innerHTML = `$${row.eventCost.toFixed(2)}`;
	document.getElementById('rLD-Reimb').innerHTML = ''; // TODO
	document.getElementById('rLD-DateApp').innerHTML = aDate.toLocaleDateString();
	document.getElementById('rLD-Status').innerHTML = row.status;	
	document.getElementById('requestListDetails').style.display = 'initial';
}

function getDateTimeFromJSON (dateObj, timeObj) {
	if (dateObj != undefined && timeObj != undefined)
		return new Date(dateObj.year, 0, dateObj.dayOfYear, timeObj.hour, timeObj.minute);
	else if (dateObj != undefined)
		return new Date(dateObj.year, 0, dateObj.dayOfYear);
	else
		return new Date(0,0,0, timeObj.hour, timeObj.minute);
}

function updateRequestList(response) {
	let htmlUpdate = '';

	empRequests = response;
	if (response.length != 0){
		htmlUpdate += `
	    <h3>Here are your existing requests</h3>
	    <div  class="grid-container-empReqs">\n
	    \t<div class="table-header">Date</div>
	    \t<div class="table-header">Description</div>
	    \t<div class="table-header">Status</div>
		</div>`;

	    for (let i = 0; i < response.length; i++)
	    {
	    	let eDate; 
			let eTime;
			let eDateTime;
			let aDate; 
	    	
	    	eDate = response[i]['eventDate'];
	    	eTime = response[i]['eventTime'];
	    	eDateTime = getDateTimeFromJSON(eDate, eTime);

	    	aDate = getDateTimeFromJSON(response[i]['appliedDate']);
	    	save = eDateTime;

	    	
	        htmlUpdate += `<div id=requestRow${i} class="grid-container-empReqs request_rows pointer">\n`;
	        htmlUpdate += `\t<div class="grid-item-empReqs">${eDateTime.toLocaleDateString('en-US', {month: '2-digit', day: '2-digit', year: '2-digit'})}</div>\n`;
	        htmlUpdate += `\t<div class="grid-item-empReqs">${response[i]['eventDesc']}</div>\n`;
	        htmlUpdate += `\t<div class="grid-item-empReqs">${response[i]['status']}</div>\n`;
	        htmlUpdate += '</div>\n';
	        
	        
	    }
	
	    htmlUpdate += `</table><br/>\n`;
		document.getElementById('requestList').innerHTML = htmlUpdate;
		for (let i = 0; i < response.length; i++)
	    {
			
			document.getElementById(`requestRow${i}`).addEventListener('click', function() {showEmpDetails(this)});
	    }
    }
	else
		document.getElementById('requestList').innerHTML = '';
	}

function updateSubsList(response) {
	let htmlUpdate = '';
	
	subsRequests = response;
	if (response.length > 0){
		htmlUpdate += `
	        <h3>The following employee requests need your attention</h3>
	        <table>
	        \t<th>Employee Name</th>
	        \t<th>Date</th>
	        \t<th>Description</th>
	        \t<th>Approve</th>
	        \t<th>Deny</th>`;
		
		for (let i = 0; i < response.length; i++)
		{
			htmlUpdate += `<tr id=subsRequestRow${i} class="request_rows pointer">\n`;
			htmlUpdate += `\t<td>${response[i]['empName']}</td>\n`;
	        htmlUpdate += `\t<td>${new Date(Date(response[i]['eventDate'])).toLocaleDateString()}</td>\n`;
	        htmlUpdate += `\t<td>${response[i]['eventDesc']}</td>\n`;
	        htmlUpdate += `\t<td><input type="radio" class="reqButtons" name="req${response[i]['reqNum']}" id="reqT${response[i]['reqNum']}" value="true"/></td>\n`;
	        htmlUpdate += `\t<td><input type="radio" class="reqButtons" name="req${response[i]['reqNum']}" id="reqF${response[i]['reqNum']}" value="false"/></td>\n`;
	        htmlUpdate += '</tr>\n';
		}
		
		htmlUpdate += `</table><br/>\n`;
		htmlUpdate += `<button id='submitRequestResponses'>Submit</button><br/>`;

		document.getElementById('subsRequests').innerHTML = htmlUpdate;
		for (let i = 0; i < response.length; i++)
			document.getElementById(`subsRequestRow${i}`).addEventListener('click', function() {showSubsDetails(this)});
		
		document.getElementById('submitRequestResponses').addEventListener('click', submitReqAppDeny);
	}
	else
		document.getElementById('subsRequests').innerHTML = '';
}

function updateBencoList(response) {
	let htmlUpdate = '';

	console.log('here');
	
	bencoRequests = response;
	if (response.length > 0){
		htmlUpdate += `
	        <h3>The following employee requests need your attention</h3>
	        <table>
	        \t<th>Employee Name</th>
	        \t<th>Date</th>
	        \t<th>Description</th>
	        \t<th>Approve</th>
	        \t<th>Deny</th>`;
		
		for (let i = 0; i < response.length; i++)
		{
			htmlUpdate += '<tr>\n';
			htmlUpdate += `\t<td>${response[i]['empName']}</td>\n`;
	        htmlUpdate += `\t<td>${new Date(Date(response[i]['eventDate'])).toLocaleDateString()}</td>\n`;
	        htmlUpdate += `\t<td>${response[i]['eventDesc']}</td>\n`;
	        htmlUpdate += `\t<td><input type="radio" class="reqButtons" name="req${response[i]['reqNum']}" id="reqT${response[i]['reqNum']}" value="true"/></td>\n`;
	        htmlUpdate += `\t<td><input type="radio" class="reqButtons" name="req${response[i]['reqNum']}" id="reqF${response[i]['reqNum']}" value="false"/></td>\n`;
	        htmlUpdate += '</tr>\n';
		}
		
		htmlUpdate += `</table><br/>\n`;
		htmlUpdate += `<button id='submitRequestResponses'>Submit</button>`;
	
		document.getElementById('bencoRequests').innerHTML = htmlUpdate;
		
		document.getElementById('submitRequestResponses').addEventListener('click', submitReqAppDeny);
	}
	else
		document.getElementById('bencoRequests').innerHTML = '';	
}

function submitReqAppDenyJSON () {
	let buttons = document.getElementsByClassName('reqButtons');
	let reqResponses = [];
	
	function reqResponse (num, resp) {
		this.reqNum = num;
		this.response = resp;
	}
	
	for (i of buttons)
	    if(i.checked == true){
	    	if (i.id.substring(3,4) == 'T'){
	    		resp = new reqResponse(i.id.substring(4), true);
	    		console.log(resp)
	    		reqResponses.push(resp);
	    	}
	    	if (i.id.substring(3,4) == 'F') {
	    		resp = new reqResponse(i.id.substring(4), false);
	    		console.log(resp)
	    		reqResponses.push(resp);
	    	}
    	}
	
	return JSON.stringify(reqResponses);
}

function showProjRmbAmt() {
	let retVal = document.getElementById('eventCost').value;

	if (retVal !== '') {
	switch(document.getElementById('eventType').value) {
		case '1':
			retVal *= 0.80;
			break;
		case '2':
			retVal *= 0.60;
			break;
		case '3':
			retVal *= .75;
			break;
		// 4 is 100%, no change	
		case '5':
			retVal *= .90;
			break;
		case '6':
			retVal *= .30;
			break;
	}
		
		document.getElementById('projRmbAmt').innerHTML = `$${retVal.toFixed(2)}`;
	}
}
