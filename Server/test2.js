var mysql = require("mysql");

var db = mysql.createConnection({
    host: "3.0.89.85",
    user: "g9",
    password: "Ass3Password!",
    database: 'ASS3DB'
});

let newId = 0;
let updateStock = ""

updateStock = `INSERT INTO STOCK(PRODUCT_ID, SIZE, QUANTITY) VALUES ?`;
let stockValue = [
    [newId + 1, 5.0, 10],
    [newId + 1, 5.5, 10],
    [newId + 1, 6.0, 10],
    [newId + 1, 6.5, 10],
    [newId + 1, 7.0, 10],
    [newId + 1, 7.5, 10],
    [newId + 1, 8.0, 10],
    [newId + 1, 8.5, 10],
    [newId + 1, 9.0, 10],
    [newId + 1, 9.5, 10]
]

let stockRes = "";
db.query(updateStock, [stockValue] ,(stockErr, stockResult) => {
    if (stockErr) {
        console.log(stockErr);
        stockRes = stockErr;
    }else {
        console.log(stockResult);
        stockRes = stockResult;
    }
})