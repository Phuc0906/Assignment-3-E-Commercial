const express = require('express');
const router = express.Router();
const fs = require('fs');

router.post('/', async (req, res) => {
    const dataList = Object.values(req.body);
    var writingData = "";
    for (let i = 0; i < dataList.length; i++) {
        writingData += dataList[i] + ",";
    }
    fs.appendFile('project.csv', "\n" + writingData, function(err) {
        if (err) {
            console.log(err);
        }else {
            console.log("Added success")
        }
    })
    // getFile();
    res.send("Sending done");
})

module.exports = router;