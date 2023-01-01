const cors = require("cors");
const { query } = require("express");
const express = require("express");
const router = express.Router();
const AWS = require("aws-sdk");

const s3 = new AWS.S3({
    accessKeyId: "AKIAXIAIX4DZTI2H25AJ",
    secretAccessKey: "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",
});

// aws secret key: 4cNqeEJDwJFwrp5qMnANtxt2C1KiXutAat32bQL1
// aws access key: AKIAXIAIX4DZTI2H25AJ

router.use(cors());
router.use(express.json());
var mysql = require("mysql");

var db = mysql.createConnection({
    host: "3.0.89.85",
    user: "g9",
    password: "Ass3Password!",
    database: "ASS3DB",
});

// View All Product
router.get("/", async (req, res) => {
    const queryCommand = "SELECT * FROM PRODUCT";
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

router.get("/images", (req, res) => {
    const queryCommand = "SELECT PICTURE FROM PRODUCT LIMIT 1;";
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

//View the latest products
router.get("/latest", (req, res) => {
    const queryCommand =
        "SELECT PR.ID, PR.NAME, PR.DESCRIPTION, PR.PRICE, PR.PICTURE, BR.NAME as brand, CATE.NAME as category FROM PRODUCT PR, BRAND BR, CATEGORY CATE WHERE BR.ID = PR.BRAND AND PR.CATEGORY = CATE.ID ORDER BY DATE_ADDED ASC";
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

// search by category
router.get("/search/category", (req, res) => {
    const queryCommand =
        "SELECT * FROM PRODUCT WHERE CATEGORY = " + req.query.category;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

// search by name
router.get("/search/name", (req, res) => {
    const queryCommand = `SELECT * FROM PRODUCT`;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            var resArr = [];
            for (let i = 0; i < result.length; i++) {
                if (
                    result[i].NAME.toLowerCase().includes(
                        req.query.name.toLowerCase()
                    )
                ) {
                    resArr.push(result[i]);
                }
            }
            res.send(resArr);
        }
    });
});

// search by brand
router.get("/search/brand", (req, res) => {
    const queryCommand =
        `SELECT * FROM PRODUCT WHERE BRAND = ` + req.query.brand;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

//add product
router.post("/", (req, res) => {
    const reqBody = req.body;
    console.log(reqBody);
    // command will be updated when app created
    const queryCommand = `
    INSERT INTO PRODUCT(ID, NAME, DESCRIPTION, PRICE, CATEGORY, PICTURE, BRAND)
    VALUES(NULL, '${reqBody.name}', '${reqBody.description}', ${reqBody.price}, ${reqBody.category}, '${reqBody.picture}', ${reqBody.brand}) 
    `;

    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

// update product
router.patch("/", (req, res) => {
    const reqBody = req.body;
    const queryCommand = `
    UPDATE PRODUCT
    SET NAME = '${reqBody.name}', DESCRIPTION = '${reqBody.description}', PRICE = ${reqBody.price}, CATEGORY = ${reqBody.category}, BRAND = ${reqBody.brand}, DATE_ADDED = CURRENT_TIMESTAMP
    WHERE ID = ${req.query.productid}
    `;

    // command will be updated when app created
    res.send(queryCommand);
});

// delete product
router.delete("/", (req, res) => {
    // command will be updated when app created
    res.send(req.query.productid);
});

// get category
router.get("/category", (req, res) => {
    const queryCommand = "SELECT * FROM CATEGORY";
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

router.get("/brand", (req, res) => {
    const queryCommand = "SELECT * FROM BRAND";
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

router.post("/newapi", (req, res) => {
    const getNewIDCommand = `SELECT MAX(ID) as newid FROM PRODUCT`;
    db.query(getNewIDCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            const newID = result[0].newid;
            const reqBody = req.body;
            const insertCommand = `
            INSERT INTO PRODUCT(ID, NAME, DESCRIPTION, PRICE, CATEGORY, PICTURE, BRAND)
            VALUES('${newID + 1}', '${reqBody.name}', '${
                reqBody.description
            }', ${reqBody.price}, ${reqBody.category}, '${newID + 1}.png', ${
                reqBody.brand
            }) 
            `;

            const transferData = "data:image/png;base64," + reqBody.picture;
            var buf = Buffer.from(
                transferData.replace(/^data:image\/\w+;base64,/, ""),
                "base64"
            );

            const params = {
                Bucket: "ass3-android-bucket",
                Key: `${newID + 1}.png`, // File name you want to save as in S3
                Body: buf,
                ContentEncoding: "base64",
                ContentType: "image/png",
            };

            s3.upload(params, function (err, data) {
                if (err) {
                    throw err;
                }
                console.log(`File uploaded successfully`);
            });

            db.query(insertCommand, (inerr, inresult) => {
                if (inerr) {
                    res.send(err);
                } else {
                    let updateStock = "";

                    updateStock = `INSERT INTO STOCK(PRODUCT_ID, SIZE, QUANTITY) VALUES ?`;
                    let stockValue = [
                        [newID + 1, 5.0, 10],
                        [newID + 1, 5.5, 10],
                        [newID + 1, 6.0, 10],
                        [newID + 1, 6.5, 10],
                        [newID + 1, 7.0, 10],
                        [newID + 1, 7.5, 10],
                        [newID + 1, 8.0, 10],
                        [newID + 1, 8.5, 10],
                        [newID + 1, 9.0, 10],
                        [newID + 1, 9.5, 10],
                    ];

                    let stockRes = "";
                    db.query(
                        updateStock,
                        [stockValue],
                        (stockErr, stockResult) => {
                            if (stockErr) {
                                stockRes = stockErr;
                            } else {
                                stockRes = stockResult;
                            }
                        }
                    );
                    res.send({
                        inRe: inresult,
                        stockRep: stockRes,
                    });
                }
            });
        }
    });
});

router.get("/detail", (req, res) => {
    const command = `SELECT PR.ID, PR.NAME, PR.DESCRIPTION, PR.PRICE, PR.PICTURE, ST.SIZE, ST.QUANTITY, BR.NAME as BRAND FROM PRODUCT PR, STOCK ST, BRAND BR WHERE PR.ID = ${req.query.id} AND PR.ID = ST.PRODUCT_ID AND PR.BRAND = BR.ID`;
    db.query(command, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            let arr = [];
            const shoes_quantity = result
                .map((item) => arr + item.QUANTITY)
                .join(", ");

            res.send({
                Name: result[0].NAME,
                Description: result[0].DESCRIPTION,
                Price: result[0].PRICE,
                Brand: result[0].BRAND,
                PICTURE: result[0].PICTURE,
                Quantity: shoes_quantity,
            });
        }
    });
});

//product cart wait until user finish

// product wishlist API waiting for user

module.exports = router;
