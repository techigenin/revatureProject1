// window.onload = function (){

//     let s = document.getElementById('submitButton');
//     s.value = 'Login';
//     s.addEventListener('click', login);
// }

// function login() {

//     let xhr = new XMLHttpRequest();

//     xhr.onreadystatechange = function() {
//         if (xhr.readyState == 4 && xhr.status == 200) {
//             console.log('meow');
//         }
//     }

//     xhr.open("POST", 'http://localhost:8080/msTRMS/login', true);

//     xhr.send(getUserPassJSON());
// }

// function getUserPassJSON () {
//     let vals = {
//         username: document.getElementById('uname').value,
//         password: document.getElementById('pword').value,
//         id: 0
//     }

//     vals = JSON.stringify(vals);

//     console.log(vals);
//     return vals;
// }
