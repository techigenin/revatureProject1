window.onload = function (){
	document.getElementById('link_requestList').style.display = 'none';
	document.getElementById('link_subsRequests').style.display = 'none';
	document.getElementById('link_bencoRequests').style.display = 'none';


	document.getElementById('requestButton').addEventListener('click', sendRequest);
	document.getElementById('link_createRequest').addEventListener('click', function() {makeVisible(this)});
	document.getElementById('link_requestList').addEventListener('click', function() {makeVisible(this)});
	document.getElementById('link_subsRequests').addEventListener('click', function() {makeVisible(this)});
	document.getElementById('link_bencoRequests').addEventListener('click', function() {makeVisible(this)});
	document.getElementById('eventCost').addEventListener('input', showProjRmbAmt);
	document.getElementById('eventType').addEventListener('change', showProjRmbAmt);
	document.getElementById('sGD-Submit').addEventListener('click', gradeFiles);
	document.getElementById('bGD-Submit').addEventListener('click', gradeApproval);
	
	setGreeting();
    setUserNameTag();
    listRequests();
    listSubsRequests();
    listBenco();
}

const rootURL = 'https://ms84103newbucket.s3.amazonaws.com/'

let empRequests = [];
let empGrades	= [];
let eventFiles  = [];
let subsRequests = [];
let bencoRequests = [];

function gradeApproval() {
	if (document.getElementById('bGD-AppDeny').value === 1) { 
		document.getElementById('bGD-WarnMessage').innerHTML = "Please select approve or deny";
		document.getElementById('bGD-WarnMessage').style.color = 'red';
	}
	else {
		let reqNum = document.getElementById('bGD-reqNum').value;

		let bencoGradeResponse = {
			reqNum: reqNum,
			presentation: document.getElementById('bGD-PresBox').checked,
			response: document.getElementById('bGD-AppDeny').value
		}

		let xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function() {
			if (xhr.readyState === 4 && xhr.status === 200) {
				listBenco();
				makeVisible(document.getElementById('link_bencoRequests'));
			}
		}

		xhr.open('POST', 'MSGradeResponse', true);

		xhr.send(JSON.stringify(bencoGradeResponse));
	}
}

function makeVisible(regionID) {
	
	let str = hidePageRegions();

	str = regionID.id.substring(5);
	
	document.getElementById(str).style.display = 'initial';
	
}

function hidePageRegions() {
	let regions = document.getElementsByClassName('pageRegions');
	;
	let str;
	for (o of regions) {
		o.style.display = 'none';
	}
	return str;
}

function sendRequest() {

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
			let reqNum = JSON.parse(xhr.responseText);

			let dSupAppFileName = dsupAppFile(reqNum);
			let deptAppFileName = deptAppFile(reqNum);
			let eventFileNames = getEventFiles(reqNum);

			listRequests();

			let fileNames = {
				reqNum: reqNum,
				dSupAppFileName: dSupAppFileName,
				deptAppFileName: deptAppFileName,
				eventFileNames: JSON.stringify(eventFileNames) // want to keep this array as a single string in DB
			}

			sendFileNames(fileNames);
        }
    }

    xhr.open('POST', 'msRequest', true);

    xhr.send(getRequestInfoJSON());
}

function sendFileNames(filesNames) {
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function () {
		if (xhr.readyState === 4 && xhr.status === 200) {
			console.log('file upload and db update complete');
		}
	}

	xhr.open("POST", 'MSRequestFiles', true);

	xhr.send(JSON.stringify(filesNames));
}

function dsupAppFile (reqNum) {
	let files = document.getElementById('dsupAppEmail').files;
	let filename = '';

	if (files.length) {
		let file = files[0];
		filename = `req${reqNum}/dsup/${file.name}`;

		uploadFile(file, filename);
	}

	return filename;
}

function deptAppFile (reqNum) {
	let files = document.getElementById('deptAppEmail').files;
	let filename = '';

	if (files.length) {
		let file = files[0];
		filename = `req${reqNum}/dept/${file.name}`;

		uploadFile(file, filename);
	}
	
	return filename;
}

function getEventFiles (reqNum) {
	let files = (document.getElementById('eventFiles')).files;
	
	let filenames = [];	

	if (files.length) {
		for (file of files)	{
			let filename = `req${reqNum}/eventFiles/${file.name}`;
			uploadFile(file, filename);
			console.log(file);
			filenames.push(filename);
		}
	}
	return filenames;
}

function gradeFiles () {
	let files = (document.getElementById('sGD-Files')).files;
	let reqNum = document.getElementById('sGD-ReqNum').value;
	let filenames = [];	

	if (files.length) {
		for (file of files)	{
			let filename = `req${reqNum}/gradeFiles/${file.name}`;
			uploadFile(file, filename);
			console.log(file);
			filenames.push(filename);
		}
	}

	sendGrade(filenames);
}

function sendGrade(filenames) {
	let xhr = new XMLHttpRequest();
	
	let grade = {
		reqNum: document.getElementById('sGD-ReqNum').value,
		grade: document.getElementById('sGD-GradeVal').value,
		gradeFiles: JSON.stringify(filenames)
	};

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			// maybe do something...?
		}
	}

	xhr.open('POST', 'MSGradeFiles', true);

	xhr.send(JSON.stringify(grade));
}

function submitReqAppDeny() {

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
			hidePageRegions();
			listSubsRequests();
			listBenco();
        }
    }

    xhr.open('POST', 'MSRequestResponse', true);

    xhr.send(submitReqAppDenyJSON());
}

function setGreeting() {

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
		if ((xhr.readyState === 4) && (xhr.status === 200)) {
			document.getElementById('greetings').innerHTML = `Greetings ${xhr.responseText}!`;
        }
	}

    xhr.open('GET', 'MSEmployeeName', true);

    xhr.send();
}

function setUserNameTag() {

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
        	document.getElementById('username').innerHTML = `user:${xhr.responseText + '\t'}`;
        }
    }

    xhr.open('GET', 'MSEmployeeUserID', true);

    xhr.send();
}

function listRequests() {
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let response = xhr.responseText;
            
            if (response.length !== 0 & response[0] !== '<'){
                response = JSON.parse(response);

				if (response.length) {
					document.getElementById('link_requestList').style.display = '';
					updateRequestList(response);
				} else {
					document.getElementById('link_requestList').style.display = 'none';
				}
			}
        }
    }

    xhr.open('GET', 'msRequestList', true);

    xhr.send();
}

function listSubsRequests() {
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let response = xhr.responseText;
            
            if (response.length !== 0 & response[0] !== '<'){
                response = JSON.parse(response);
			
				if (response.length) {
					document.getElementById('link_subsRequests').style.display = '';
            		updateSubsList(response);
				} else {
					document.getElementById('link_subsRequests').style.display = 'none';
				}
			}
        }
    }

    xhr.open('GET', 'msRequestSubs', true);

    xhr.send();
}

function listBenco() {
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let response = xhr.responseText;
            
            if (response.length !== 0 & response[0] !== '<'){
                response = JSON.parse(response);
				
				if (response.length) {
					document.getElementById('link_bencoRequests').style.display = '';
            		updateBencoList(response);
				} else {
					document.getElementById('link_bencoRequests').style.display = 'none';
				}
            }
        }
    }

    xhr.open('GET', 'MSBencoReqs', true);

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
		durr: eDurr,
    }

    let requestJSON = JSON.stringify(request);
    return requestJSON;
}

function showEmpDetails(row) {
	let rNum = parseInt(`${row.id.split('_')[1]}`);

	for (r of empRequests){
		if (r.reqNum === rNum) {
			row = r;
			break;
		}
	}

	let statusVal = row.status;

	for (o of document.getElementsByClassName('empReqDets'))
		o.style.display = 'none';
	
	eDate = row['eventDate'];
	eTime = row['eventTime'];
	eDateTime = getDateTimeFromJSON(eDate, eTime);
	aDate = getDateTimeFromJSON(row['appliedDate']);

	if (statusVal === 'pending' || statusVal === 'denied') { // Show Normal Details view 
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
		document.getElementById('sGD-ReqNum').value = row.reqNum;
		document.getElementById('sGD-Desc').innerHTML = row.eventDesc;
		document.getElementById('sGD-Date').innerHTML = eDateTime.toLocaleDateString();
		document.getElementById('submitGradeDetails').style.display = 'initial';
	}
}

function showSubsDetails(row) {
	let rNum = parseInt(`${row.id.split('_')[1]}`);

	for (r of subsRequests){
		if (r.reqNum === rNum) {
			row = r;
			break;
		}
	}

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

function showBencoDetails(row) {
	
	let rNum = parseInt(`${row.id.split('_')[1]}`);

	for (r of bencoRequests){
		if (r.reqNum === rNum) {
			row = r;
			break;
		}
	}


	for (o of document.getElementsByClassName('bencoReqDets'))
		o.style.display = 'none';

	if (row.status !== 'approved') {
		eDate = row['eventDate'];
		eTime = row['eventTime'];
		eDateTime = getDateTimeFromJSON(eDate, eTime);
		aDate = getDateTimeFromJSON(row['appliedDate']);
		
		document.getElementById('bLD-Name').innerHTML = row.empName;
		document.getElementById('bLD-Desc').innerHTML = row.eventDesc;
		document.getElementById('bLD-Loc').innerHTML = row.eventLoc;
		document.getElementById('bLD-DateTime').innerHTML = eDateTime.toLocaleString();
		document.getElementById('bLD-GradeFmt').innerHTML = row.gradeFmt;
		document.getElementById('bLD-Type').innerHTML = row.eventType;
		document.getElementById('bLD-Cost').innerHTML = `$${row.eventCost.toFixed(2)}`;
		document.getElementById('bLD-Reimb').innerHTML = `$${row.rmbAmt.toFixed(2)}`;
		document.getElementById('bLD-DateApp').innerHTML = aDate.toLocaleDateString();
		document.getElementById('bLD-Status').innerHTML = row.status;	
		document.getElementById('bencoListDetails').style.display = 'initial';

		
		let divs = document.getElementsByClassName('hiddenDiv');

			for (d of divs){
				d.style.display = 'none';
			}
		addEventFiles(row.reqNum);
	}
	else {
		document.getElementById('bGD-reqNum').value = row.reqNum;
		document.getElementById('bGD-Name').innerHTML = row.empName;
		document.getElementById('bGD-Desc').innerHTML = row.eventDesc;
		document.getElementById('bGD-GradeFmt').innerHTML = row.gradeFmt;
		document.getElementById('bGD-GradeSub').innerHTML = (row.hasGrade) ? getGrade(row.reqNum) : '';
		document.getElementById('bencoGradeDetails').style.display = 'initial';

		if (row.hasGrade) {
			addGradeFiles(row.reqNum);
			document.getElementById('bGD-Submit').style.display = '';
		}	
		else {
			document.getElementById('bGD-Submit').style.display = 'none';
			let divs = document.getElementsByClassName('hiddenDiv');

			for (d of divs){
				d.style.display = 'none';
			}
		}
	}
}

function addEventFiles(reqNum) {
	let thisEvent;
	
	for (e of eventFiles) {
		if (e.reqNum === reqNum) {
			
			thisEvent = e;
			break;
		}
	}

	if (thisEvent === undefined)
		return;

	if (thisEvent.dSupAppFileName.length | thisEvent.deptAppFileName.length | thisEvent.eventFileNames.length) {
		let a = document.getElementsByClassName('hiddenDiv');

		for (o of a)
    		o.style.display = 'grid';
	}

	document.getElementById('dSupFilesDiv').innerHTML = (thisEvent.dSupAppFileName.length) ? `<a href="${rootURL + thisEvent.dSupAppFileName}">${thisEvent.dSupAppFileName.split('/')[2]}</a>` : '';


	document.getElementById('deptFilesDiv').innerHTML = (thisEvent.deptAppFileName.length) ? `<a href="${rootURL + thisEvent.deptAppFileName}">${thisEvent.deptAppFileName.split('/')[2]}</a>` : '';

	if (thisEvent.eventFileNames.length) {
		let links = JSON.parse(thisEvent.eventFileNames);
		let htmlUpdate = '';

		console.log(thisEvent);

		for (l of links) {
			if (l.length)
				htmlUpdate += `<a href=${rootURL + l}>${l.split('/')[2]}</a>`;
		}

		console.log(htmlUpdate);
		document.getElementById('eventFilesDiv').innerHTML = (htmlUpdate.length) ? htmlUpdate : '';
	}



}

function getGrade(reqNum){
	for (g of empGrades) {
		if (g.reqNum === reqNum) 
			return g.grade;
	}
}

function addGradeFiles(reqNum) {
	let divs = document.getElementsByClassName('hiddenDiv');

	for (d of divs){
		d.style.display = 'grid';
	}

	let links;

	for (g of empGrades){
		if (g.reqNum === reqNum) {
			links = g.gradeFiles;
		}
	}

	if (links)
		links = JSON.parse(links);
	else
		return;
	
	let htmlUpdate = '';

	for (l of links) {
		htmlUpdate += `<a href=${rootURL + l}>${l.split('/')[2]}</a><br/>`;
	}

	document.getElementById('gradeFilesDiv').innerHTML = htmlUpdate;
}

function getDateTimeFromJSON (dateObj, timeObj) {
	if (dateObj !== undefined && timeObj !== undefined)
		return new Date(dateObj.year, 0, dateObj.dayOfYear, timeObj.hour, timeObj.minute);
	else if (dateObj !== undefined)
		return new Date(dateObj.year, 0, dateObj.dayOfYear);
	else
		return new Date(0,0,0, timeObj.hour, timeObj.minute);
}

function updateRequestList(response) {
	let htmlUpdate = '';

	empRequests = response;
	if (response.length !== 0){
		htmlUpdate += `
	    <h5>Here are your existing requests</h5>
	    <div  class="grid-container-empReqs">\n
	    \t<div class="table-header">Date</div>
	    \t<div class="table-header">Description</div>
	    \t<div class="table-header">Status</div>
		</div>`;

	    for (r of response)
	    {
	    	let eDate; 
			let eTime;
			let eDateTime;
	    	
	    	eDate = r.eventDate;
	    	eTime = r.eventTime;
	    	eDateTime = getDateTimeFromJSON(eDate, eTime);
	    	
	        htmlUpdate += `<div id=requestRow_${r.reqNum} class="grid-container-empReqs request_rows pointer">\n`;
	        htmlUpdate += `\t<div class="grid-empReqs">${eDateTime.toLocaleDateString('en-US', {month: '2-digit', day: '2-digit', year: '2-digit'})}</div>\n`;
	        htmlUpdate += `\t<div class="grid-empReqs">${r.eventDesc}</div>\n`;
	        htmlUpdate += `\t<div class="grid-empReqs">${r.status}</div>\n`;
	        htmlUpdate += '</div>\n';
	    }
	
	    htmlUpdate += `</table><br/>\n`;
		document.getElementById('requestList').innerHTML = htmlUpdate;
		for (r of response){
			let row = document.getElementById(`requestRow_${r.reqNum}`);
			row.addEventListener('click', function() {showEmpDetails(this)});
			
			console.log(r.status);
			if (r.status === "awarded") {
				console.log('got here');
				row.style.background = '#00dd00'; 
			}
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
			<h5>The following employee requests need your attention</h5>
			<div  class="grid-container-managerReqs ">\n
			\t<div class="table-header grid-item header">Employee Name</div>
			\t<div class="table-header grid-item header">Date</div>
			\t<div class="table-header grid-item header">Description</div>
			\t<div class="table-header grid-item header">Approve</div>
			\t<div class="table-header grid-item header">Deny</div>
			</div>`;

		for (p of response)
		{
			let eDate; 
			let eTime;
			let eDateTime;

			eDate = p['eventDate'];
			eTime = p['eventTime'];
			eDateTime = getDateTimeFromJSON(eDate, eTime);
			
			htmlUpdate += `<div id="subsRequestrow_${p.reqNum}" class="grid-container-managerReqs request_rows benco_request_rows pointer">\n`;
			htmlUpdate += `\t<div class="grid-item body">${p.empName}</div>\n`;
			htmlUpdate += `\t<div class="grid-item body">${eDateTime.toLocaleDateString('en-US', {month: '2-digit', day: '2-digit', year: '2-digit'})}</div>\n`;
			htmlUpdate += `\t<div class="grid-item body">${p.eventDesc}</div>\n`;
			htmlUpdate += `\t<div class="grid-item body"><input type="radio" class="reqButtons" name="req${p.reqNum}" id="reqT${p.reqNum}" value="true"/></div>\n`;
			htmlUpdate += `\t<div class="grid-item body"><input type="radio" class="reqButtons" name="req${p.reqNum}" id="reqF${p.reqNum}" value="false"/></div>\n`;
			htmlUpdate += '</div>\n';
		}
		
		htmlUpdate += `</table><br/>\n`;
		htmlUpdate += `<button id='submitRequestResponses'>Submit</button><br/>`;

		document.getElementById('subsRequests').innerHTML = htmlUpdate;
		for (r of response)
			document.getElementById(`subsRequestrow_${r.reqNum}`).addEventListener('click', function() {showSubsDetails(this)});
		
		document.getElementById('submitRequestResponses').addEventListener('click', submitReqAppDeny);
	}
	else
		document.getElementById('subsRequests').innerHTML = '';
}

function updateBencoList(response) {
	let htmlUpdate = '';

	let pending = [];
	let approved = [];
	
	if (response.length > 0){
		for (r of response) {
			if (r.status === 'pending')
				pending.push(r);
			else if (r.status === 'approved')
				approved.push(r);
		}

		bencoRequests = response;
		if (pending.length > 0){

			htmlUpdate += `
				<h5>The following employee requests need your attention</h5>
				<div  class="grid-container-managerReqs ">\n
				\t<div class="table-header grid-item header">Employee Name</div>
				\t<div class="table-header grid-item header">Date</div>
				\t<div class="table-header grid-item header">Description</div>
				\t<div class="table-header grid-item header">Approve</div>
				\t<div class="table-header grid-item header">Deny</div>
				</div>`;

			for (p of pending)
			{
				let eDate; 
				let eTime;
				let eDateTime;

				eDate = p['eventDate'];
				eTime = p['eventTime'];
				eDateTime = getDateTimeFromJSON(eDate, eTime);
				
				htmlUpdate += `<div id="bencoRequestRow_${p.reqNum}" class="grid-container-managerReqs request_rows benco_request_rows pointer">\n`;
				htmlUpdate += `\t<div class="grid-item body">${p.empName}</div>\n`;
				htmlUpdate += `\t<div class="grid-item body">${eDateTime.toLocaleDateString('en-US', {month: '2-digit', day: '2-digit', year: '2-digit'})}</div>\n`;
				htmlUpdate += `\t<div class="grid-item body">${p.eventDesc}</div>\n`;
				htmlUpdate += `\t<div class="grid-item body"><input type="radio" class="reqButtons" name="req${p.reqNum}" id="reqT${p.reqNum}" value="true"/></div>\n`;
				htmlUpdate += `\t<div class="grid-item body"><input type="radio" class="reqButtons" name="req${p.reqNum}" id="reqF${p.reqNum}" value="false"/></div>\n`;
				htmlUpdate += '</div>\n';
			}
		}

		htmlUpdate += `<br/>\n`;
		htmlUpdate += `<button id='submitRequestResponses'>Submit</button><br/>`;
		htmlUpdate += `<br/>`
	

		if (approved.length > 0) {
			htmlUpdate += `
				<h5>The following employee requests are awaiting grade approval</h5>
				<div  class="grid-container-bencoGrade ">\n
				\t<div class="table-header grid-item header">Employee Name</div>
				\t<div class="table-header grid-item header">Date</div>
				\t<div class="table-header grid-item header">Description</div>
				</div>`;
		
			for (a of approved)
			{
				let eDate; 
				let eTime;
				let eDateTime;

				eDate = a['eventDate'];
				eTime = a['eventTime'];
				eDateTime = getDateTimeFromJSON(eDate, eTime);
				
				htmlUpdate += `<div id="bencoGradeRow_${a.reqNum}" class="grid-container-bencoGrade request_rows benco_request_rows pointer">\n`;
				htmlUpdate += `\t<div class="grid-item body">${a.empName}</div>\n`;
				htmlUpdate += `\t<div class="grid-item body">${eDateTime.toLocaleDateString('en-US', {month: '2-digit', day: '2-digit', year: '2-digit'})}</div>\n`;
				htmlUpdate += `\t<div class="grid-item body">${a.eventDesc}</div>\n`;
				htmlUpdate += '</div>\n';
			}
		}
	
		document.getElementById('bencoRequests').innerHTML = htmlUpdate;
		
		document.getElementById('submitRequestResponses').addEventListener('click', submitReqAppDeny);

		for (p of pending) {
			let row = document.getElementById(`bencoRequestRow_${p.reqNum}`);
			row.addEventListener('click', function() {showBencoDetails(this)});
			getEventAJAX(p.reqNum);
		}
		
		for (a of approved) {
			let row = document.getElementById(`bencoGradeRow_${a.reqNum}`);
			row.addEventListener('click', function() {showBencoDetails(this)});
			if (a.hasGrade) {
				row.style.background = '#00dd00';
				getGradeAJAX(a.reqNum);
			}
		}
	}

	else {
		document.getElementById('bencoRequests').innerHTML = '';	
	}
}

function getGradeAJAX(reqNum) {
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function () {
		if (xhr.readyState === 4 && xhr.status === 200) {
			let grade = JSON.parse(xhr.responseText);
			empGrades.push(grade);
		}
	}

	xhr.open("POST", 'MSGrade', true);

	xhr.send(reqNum);
}

function getEventAJAX(reqNum) {
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function () {
		if (xhr.readyState === 4 && xhr.status === 200) {
			let eventFile = JSON.parse(xhr.responseText);
			eventFiles.push(eventFile);
		}
	}

	xhr.open("POST", 'MSEventFiles', true);

	xhr.send(reqNum);
}

function submitReqAppDenyJSON () {
	let buttons = document.getElementsByClassName('reqButtons');
	let reqResponses = [];
	
	function reqResponse (num, resp) {
		this.reqNum = num;
		this.response = resp;
	}
	
	for (i of buttons)
	    if(i.checked === true){
	    	if (i.id.substring(3,4) === 'T'){
	    		resp = new reqResponse(i.id.substring(4), true);
	    		reqResponses.push(resp);
	    	}
	    	if (i.id.substring(3,4) === 'F') {
	    		resp = new reqResponse(i.id.substring(4), false);
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
