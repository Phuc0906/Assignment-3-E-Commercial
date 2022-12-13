const res = require('express/lib/response');
const express = require('express');
const app = express();
const cors = require('cors');
var mysql = require('mysql');

app.use(cors());
app.use(express.json());

var connection = mysql.createConnection({
    host: "3.0.89.85",
    user: "g9",
    password: "Ass3Password!",
    database: "ASS3DB"
});

connection.connect(function(err) {
    if (err) throw err;
    console.log("Connect");
})

//user/data
//product/data

app.get('/user', (req, res) => {
    connection.query("SELECT * FROM USER", function(err, result) {
        if (err) console.log("ERR");
        console.log(result[0].NAME);
        res.send(result[0]);
    });
})



app.listen(3004, function() {
    console.log("Server listening");
})