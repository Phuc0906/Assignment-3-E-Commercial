var mysql = require("mysql");
const express = require("express");
const app = express();
const cors = require("cors");
const productAPI = require("./routes/ProductAPIs");
const userAPI = require("./routes/UserAPIs");
app.use(cors());
app.use("/product", productAPI);
app.use("/user", userAPI);

app.use(express.json({limit: '50mb', extended: true}));
app.use(express.urlencoded({limit: "50mb", extended: true, parameterLimit:50000}));



var connection = mysql.createConnection({
    host: "3.0.89.85",
    user: "phuchoang",
    password: "Assignment3Password!",
});

connection.connect(function (err) {
    if (err) throw err;
    console.log("Connect");
});

app.listen(process.env.PORT || 3004, () => {
    console.log("Server is listening");
});
