window.onload = function () {
    document.getElementById('submitButton').addEventListener('click', submitData, true);
}

function submitData() {
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = JSON.parse(xhr.responseText);
            let newRow = `<tr>
            <td>${response.name}</td>
            <td>${response.date}</td>
            <td>${response.time}</td>
            <td>${response.location}</td>
            <td>${response.description}</td>
            <td>${response.cost}</td>
            <td>${response.eventType}</td>
            <td>${response.gradeFormat}</td>
            </tr>`

            document.getElementById('resultTable').innerHTML += newRow;
        }
    }

    xhr.open("POST", 'http://localhost:8080/msExperiment/firstTest', true);

    xhr.send(JSON.stringify(buildPayload()));
}

function buildPayload() {
    return {
        name : document.getElementById('name').value,
        date : document.getElementById('date').value,
        time : document.getElementById('time').value,
        location : document.getElementById('location').value,
        description : document.getElementById('description').value,
        cost : document.getElementById('cost').value,
        gradeFormat : document.getElementById('gradeFormat').value,
        eventType : document.getElementById('eventType').value
    }
}

