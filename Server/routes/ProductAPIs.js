const cors = require('cors');
const { query } = require('express');
const express = require('express');
const router = express.Router();

router.use(cors());
router.use(express.json());
var mysql = require('mysql');


var db = mysql.createConnection({
    host: "3.0.89.85",
    user: "g9",
    password: "Ass3Password!",
    database: 'ASS3DB'
});

// View All Product
router.get('/', async (req, res) => {
    const queryCommand = 'SELECT * FROM PRODUCT';
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            res.send(result[0].PICTURE);
        }
    })
});

//View the latest products
router.get('/latest', (req, res) => {
    const queryCommand = 'SELECT * FROM PRODUCT ORDER BY DATE_ADDED ASC'
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            res.send(result);
        }
    })
});

// search by category
router.get('/search/category', (req, res) => {
    const queryCommand = 'SELECT * FROM PRODUCT WHERE CATEGORY = ' + req.query.category;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            res.send(result);
        }
    });
});

// search by name
router.get('/search/name', (req, res) => {
    const queryCommand = `SELECT * FROM PRODUCT`;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            var resArr = [];
            for (let i = 0; i < result.length; i++) {
                if (result[i].NAME.toLowerCase().includes(req.query.name.toLowerCase())) {
                    resArr.push(result[i]);
                }
            }
            res.send(resArr);
        }
    });
})

// search by brand
router.get('/search/brand', (req, res) => {
    const queryCommand = `SELECT * FROM PRODUCT WHERE BRAND = ` + req.query.brand;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            res.send(result);
        }
    });
});

//add product
router.post('/', (req, res) => {
    const reqBody = req.body;
    // command will be updated when app created
    const queryCommand = `
    INSERT INTO PRODUCT(ID, NAME, DESCRIPTION, PRICE, CATEGORY, PICTURE, BRAND)
    VALUES(NULL, '${reqBody.name}', '${reqBody.description}', ${reqBody.price}, ${reqBody.category}, '${reqBody.picture}', ${reqBody.brand}) 
    `
    res.send(queryCommand);
});

// update product
router.patch('/', (req, res) => {
    const reqBody = req.body;
    const queryCommand = `
    UPDATE PRODUCT
    SET NAME = '${reqBody.name}', DESCRIPTION = '${reqBody.description}', PRICE = ${reqBody.price}, CATEGORY = ${reqBody.category}, BRAND = ${reqBody.brand}, DATE_ADDED = CURRENT_TIMESTAMP
    WHERE ID = ${req.query.productid}
    `

    // command will be updated when app created
    res.send(queryCommand);
})

// delete product
router.delete('/', (req, res) => {
    // command will be updated when app created
    res.send(req.query.productid);
})

//product cart wait until user finish

// product wishlist API waiting for user


module.exports = router;