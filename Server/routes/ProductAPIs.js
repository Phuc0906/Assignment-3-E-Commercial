const cors = require("cors");
const { query, Router } = require("express");
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
    multipleStatements: true,
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
        `SELECT PR.ID, PR.NAME, PR.DESCRIPTION, PR.PRICE, PR.PICTURE, BR.NAME as brand, CATE.NAME as category FROM PRODUCT PR, BRAND BR, CATEGORY CATE WHERE BR.ID = PR.BRAND AND PR.CATEGORY = CATE.ID AND PR.CATEGORY = ${req.query.category}`;
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
            const reqBody = req.body.info;
            const sizeBody = req.body.size;
            const sizeArr = sizeBody.split(',');
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
                        [newID + 1, 5.0, sizeArr[0]],
                        [newID + 1, 5.5, sizeArr[1]],
                        [newID + 1, 6.0, sizeArr[2]],
                        [newID + 1, 6.5, sizeArr[3]],
                        [newID + 1, 7.0, sizeArr[4]],
                        [newID + 1, 7.5, sizeArr[5]],
                        [newID + 1, 8.0, sizeArr[6]],
                        [newID + 1, 8.5, sizeArr[7]],
                        [newID + 1, 9.0, sizeArr[8]],
                        [newID + 1, 9.5, sizeArr[9]],
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
    const command = `SELECT PR.ID, PR.NAME, PR.DESCRIPTION, PR.PRICE, PR.PICTURE, ST.SIZE, ST.QUANTITY, BR.NAME as BRAND, CAT.NAME as category FROM PRODUCT PR, STOCK ST, BRAND BR, CATEGORY CAT WHERE PR.ID = ${req.query.id} AND PR.ID = ST.PRODUCT_ID AND PR.BRAND = BR.ID AND PR.CATEGORY = CAT.ID `;
    db.query(command, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            let arr = [];
            const shoes_quantity = result
                .map((item) => arr + item.QUANTITY)
                .join(",");

            res.send({
                Name: result[0].NAME,
                Description: result[0].DESCRIPTION,
                Price: result[0].PRICE,
                Brand: result[0].BRAND,
                Category: result[0].category,
                Picture: result[0].PICTURE,
                Quantity: shoes_quantity,
            });
        }
    });
});

router.patch('/detail', (req, res) => {
    const productId = req.query.id;
    const reqBody = req.body.info;
    const sizeBody = req.body.size;
    const sizeArr = sizeBody.split(',');
    const updateCommand = `UPDATE PRODUCT 
    SET NAME = '${reqBody.name}', DESCRIPTION = '${reqBody.description}', PRICE = ${reqBody.price}, CATEGORY = ${reqBody.category}, BRAND = ${reqBody.brand}
    WHERE ID = ${productId}`;

    if (reqBody.picture != undefined) {
        const transferData = "data:image/png;base64," + reqBody.picture;
        var buf = Buffer.from(
            transferData.replace(/^data:image\/\w+;base64,/, ""),
            "base64"
        );

        const paramsPatch = {
            Bucket: "ass3-android-bucket",
            Key: `${productId}.png`, // File name you want to save as in S3
            Body: buf,
            ContentEncoding: "base64",
            ContentType: "image/png",
        };

        s3.upload(paramsPatch, function (err, data) {
            if (err) {
                throw err;
            }
            console.log(`File uploaded successfully`);
        });
    }

    db.query(updateCommand, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            
            var updateStockCommand = ``;
            var i = 5;
            var sizeIdx = 0;
            var quantitySetting = 0;
            while (sizeIdx < sizeArr.length) {
                quantitySetting = parseInt(sizeArr[sizeIdx]);
                updateStockCommand += `UPDATE STOCK SET QUANTITY = ` + `${quantitySetting}` + ` WHERE PRODUCT_ID = ${productId} && SIZE = ${i};`; 
                i+= 0.5;
                sizeIdx++;
            }
            
            // res.send({
            //     result: result,
            //     sizeArray: sizeArr
            // })

            db.query(updateStockCommand, (stkErr, stkResult) => {
                if (stkErr) {
                    res.send(stkErr);
                }else {
                    res.send({
                        updateProductRes: result,
                        updateStkRes: stkResult
                    })
                }
            })

        }
    })
    
})

router.post("/cart", (req, res) => {
    const reqBody = req.body;
    const checkCommand = `SELECT * FROM IN_CART WHERE USERID = ${reqBody.userid} && PRODUCT_ID = ${reqBody.productid} && SIZE = ${reqBody.size}`;
    const insertCommand = `INSERT INTO IN_CART(USERID, PRODUCT_ID, SIZE, QUANTITY) VALUES(${reqBody.userid}, ${reqBody.productid}, ${reqBody.size}, ${reqBody.quantity})`;
    db.query(checkCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            if (result.length != 0) {
                const updateQuery = `UPDATE IN_CART SET QUANTITY = QUANTITY + ${reqBody.quantity} WHERE USERID = ${reqBody.userid} && PRODUCT_ID = ${reqBody.productid} && SIZE = ${reqBody.size}`;
                db.query(updateQuery, (upErr, upResult) => {
                    if (upErr) {
                        res.send(upErr);
                    } else {
                        res.send(upResult);
                    }
                });
            } else {
                db.query(insertCommand, (upErr, upResult) => {
                    if (upErr) {
                        res.send(upErr);
                    } else {
                        res.send(upResult);
                    }
                });
            }
        }
    });
});

router.get("/cart", (req, res) => {
    const queryCommand = `SELECT IC.PRODUCT_ID, IC.QUANTITY, IC.SIZE as SIZE, PR.NAME as PRODUCT_NAME, PR.PRICE, PR.PICTURE, BR.NAME as BRAND FROM IN_CART IC, PRODUCT PR, BRAND BR WHERE IC.PRODUCT_ID = PR.ID AND PR.BRAND = BR.ID AND IC.USERID = ${req.query.userid}`;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

router.delete("/cart", (req, res) => {
    const queryCommand = `DELETE FROM IN_CART WHERE USERID = ${req.query.userid} && PRODUCT_ID = ${req.query.productid} && SIZE = ${req.query.size}`;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

router.patch("/cart", (req, res) => {
    const bodyValues = req.body;

    var updateQuery = "";
    bodyValues.forEach(function(item) {
        updateQuery += `UPDATE IN_CART SET QUANTITY = ${item[0]} WHERE USERID = ${item[1]} && PRODUCT_ID = ${item[2]} && SIZE = ${item[3]};`
    })

    db.query(updateQuery, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            res.send(result);
        }
    });
});

router.post('/billing', (req, res) => {
    const reqBody = req.body;
    const getNewBuyId = `SELECT MAX(BUYID) as newid FROM PURCHASE`
    db.query(getNewBuyId, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            const resultRespone = result;
            const newId = result[0].newid;
            const insertPurchase = `INSERT INTO PURCHASE(USERID, BUYID, TOTAL_PRICE, STATUS, PAYMENT, ADDRESS) VALUES (${reqBody.userid}, ${newId + 1}, ${reqBody.totalPrice}, ${reqBody.status}, ${reqBody.payment}, ${reqBody.address});`
            db.query(insertPurchase, (errPur, resPur) => {
                if (errPur) {
                    res.send(errPur);
                }else {
                    const purResponse = resPur;
                    const insertBuyHistory = `INSERT INTO BUY_HISTORY(BUYID, PRODUCT_ID, QUANTITY, SIZE) SELECT ${newId + 1}, PRODUCT_ID, QUANTITY, SIZE FROM IN_CART WHERE USERID = ${reqBody.userid}`;
                    db.query(insertBuyHistory, (buyErr, buyRes) => {
                        if (buyErr) {
                            res.send(buyErr);
                        }else {
                            const buyResult = buyRes;
                            const deleteCart = `DELETE FROM IN_CART WHERE USERID = ${reqBody.userid}`;
                            db.query(deleteCart, (carErr, cartRes) => {
                                if (carErr) {
                                    res.send(carErr);
                                }else {
                                    res.send({
                                        resultRespone: resultRespone,
                                        purResponse: purResponse,
                                        buyResult: buyResult,
                                        delCart: cartRes
                                    })
                                }
                            })
                        }
                    })
                }
            })
        }
    })
});

router.get('/billing', (req, res) => {
    const queryCommand = "SELECT USR.NAME, PU.BUYID, PU.STATUS, PU.TOTAL_PRICE, PU.ADDRESS, PU.PAYMENT FROM PURCHASE PU, USER USR WHERE USR.ID = PU.USERID;";
    db.query(queryCommand, (err, result)=> {
        if (err) {
            res.send(err);
        }else {
            res.send(result);
        }
    })
});

router.get('/billing/detail', (req, res) => {
    const queryBilling = `SELECT PR.ID, PR.PICTURE, BR.NAME AS BRAND, PR.NAME, PR.PRICE * BU.QUANTITY AS ITEM_PRICE, BU.QUANTITY, BU.SIZE FROM PRODUCT PR, BUY_HISTORY BU, BRAND BR WHERE BU.BUYID = ${req.query.buyid} AND BU.PRODUCT_ID = PR.ID AND PR.BRAND = BR.ID;`
    db.query(queryBilling, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            res.send(result);
        }
    })
})

module.exports = router;
