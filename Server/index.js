var mysql = require('mysql');

var connection = mysql.createConnection({
    host: "3.0.89.85",
    user: "phuchoang",
    password: "Assignment3Password!"
});

connection.connect(function(err) {
    if (err) throw err;
    console.log("Connect");
})