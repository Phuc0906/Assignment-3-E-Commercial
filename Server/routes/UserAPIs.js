const cors = require("cors");
const { query } = require("express");
const express = require("express");
const router = express.Router();

router.use(cors());
router.use(express.json());
var mysql = require("mysql");

var db = mysql.createConnection({
    host: "3.0.89.85",
    user: "g9",
    password: "Ass3Password!",
    database: "ASS3DB",
});

const AWS = require("aws-sdk");

const s3 = new AWS.S3({
    accessKeyId: "AKIAXIAIX4DZTI2H25AJ",
    secretAccessKey: "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",
});

router.get("/login", async (req, res) => {
    const email = req.query.email;
    const password = req.query.password;

    const queryCommand = `SELECT * FROM USER WHERE EMAIL = '${email}' && PASSWORD = '${password}'`;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            if (result.length > 0) {
                
                res.send({
                    verify: 1,
                    id: result[0].ID,
                    role: result[0].ROLE,
                });
            } else {
                res.send({
                    verify: 0,
                });
            }
        }
    });
});

router.get('/email', (req, res) => {
    const email = req.query.email;
    const command = `SELECT * FROM USER WHERE EMAIL = '${email}'`;

    

    db.query(command, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            if (result.length > 0) {
                res.send({
                    has: 1
                })
            }else {
                res.send({
                    has: 0
                })
            }
        }
    })
})

router.get("/information", (req, res) => {
    const id = req.query.id;
    const queryCommand = `SELECT * FROM USER WHERE ID = '${id}'`;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            if (result != null) {
                res.send(result[0]);
            }
        }
    });
});

router.patch('/information', (req, res) => {
    const reqBody = req.body;
    var updateCommand = ``;
    if (reqBody.picture == undefined) {
        updateCommand = `UPDATE USER 
        SET NAME = '${reqBody.name}', ADDRESS = '${reqBody.address}', PHONE_NUMBER = '${reqBody.phone}' WHERE ID = ${reqBody.userid}`;
    }else {
        const transferData = "data:image/png;base64," + reqBody.picture;
        var buf = Buffer.from(
            transferData.replace(/^data:image\/\w+;base64,/, ""),
            "base64"
        );
        updateCommand = `UPDATE USER 
        SET NAME = '${reqBody.name}', ADDRESS = '${reqBody.address}', PHONE_NUMBER = '${reqBody.phone}' , PICTURE = 'user_${reqBody.userid}.png' WHERE ID = ${reqBody.userid}`;
        const params = {
            Bucket: "ass3-android-bucket",
            Key: `user_${reqBody.userid}.png`, // File name you want to save as in S3
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
    }

    db.query(updateCommand, (err, result) => {
        if (err) {
            res.send(err);
        }else {
            
            res.send(result);
        }
    })
})

router.post("/information", (req, res) => {
    const reqBody = req.body;
    const queryCommand = `
    INSERT INTO USER(ID, NAME, ADDRESS, PHONE_NUMBER, GENDER, DATE_OF_BIRTH, POINT, PASSWORD, ROLE, EMAIL)
    VALUES(NULL, '${reqBody.name}', '${reqBody.address}', '${reqBody.phone}', '${reqBody.gender}', '', 0, '${reqBody.password}', 'USER', '${reqBody.email}')`;
    
    const transferData = "data:image/png;base64," + reqBody.img;
    var buf = Buffer.from(
        transferData.replace(/^data:image\/\w+;base64,/, ""),
        "base64"
    );
    
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            const newID = `SELECT MAX(ID) as newid FROM USER;`
            db.query(newID, (err, result) => {
                if (err) {
                    res.send(err);
                }else {
                    const userImgId = result[0].newid;
                    const params = {
                        Bucket: "ass3-android-bucket",
                        Key: `user_${userImgId}.png`, // File name you want to save as in S3
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

                    const updateCommand = `UPDATE USER SET PICTURE = 'user_${userImgId}.png' WHERE ID = ${userImgId}`;
                    db.query(updateCommand, (upErr, upResult) => {
                        if (err) {
                            res.send(upErr);
                        }
                    })
                }
            })
            res.send(result);
        }
    });
});

router.patch("/password", (req, res) => {
    const newPass = req.query.newPass;
    const id = req.query.id;

    const queryCommand = `UPDATE USER 
                            SET PASSWORD = '${newPass}' 
                            WHERE ID = ${id}`;
    db.query(queryCommand, (err, result) => {
        if (err) {
            res.send(err);
        } else {
            if (result.changedRows > 0) {
                res.send({
                    verify: 1,
                });
            } else {
                res.send({
                    verify: 0,
                });
            }
        }
    });
});

module.exports = router;
