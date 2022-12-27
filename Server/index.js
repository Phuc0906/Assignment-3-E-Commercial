var mysql = require("mysql");
const express = require("express");
const app = express();
const cors = require("cors");
const productAPI = require("./routes/ProductAPIs");
const userAPI = require("./routes/UserAPIs");
app.use(cors());
app.use("/product", productAPI);
app.use("/user", userAPI);
var connection = mysql.createConnection({
    host: "3.0.89.85",
    user: "phuchoang",
    password: "Assignment3Password!",
});

connection.connect(function (err) {
    if (err) throw err;
    console.log("Connect");
});

app.listen(3000, () => {
    console.log("Server is listening");
});
