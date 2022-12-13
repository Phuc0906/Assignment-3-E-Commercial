const express = require('express');
const app = express();
const path = require('path');
const fs = require('fs');
const cors = require('cors');
const projectAPI = require('./routes/ProjectAPI')

app.use(express.json());
app.use(cors());
app.use('/projects', projectAPI);

const {Storage} = require('@google-cloud/storage');

const storage = new Storage({
    keyFilename: path.join(__dirname, "query-server-358416-da53c86c993b.json"),
    projectId: 'query-server-358416'
});

const file = storage.bucket('testing-bucket-ass1').file('project.csv');

const bucket = storage.bucket('ques1-bucket');



// object constructor
function Project(projectName, subtype, currentStatus, Capacity, completionYear, notDefine, listOfSponsorOrDeveloper, sponsorCompany, listOfLenderOrFinancier, lenderCompany, listOfContruction, contructionCompany, country, province, district, notDefine2, tributary, latitude, longtitude, proximity, anualOutput, notDefine3, dataSource, announcement, link, latestUpdate) {
    this.projectName = projectName;
    this.subtype = subtype;
    this.currentStatus = currentStatus;
    this.Capacity = Capacity;
    this.completionYear = completionYear;
    this.notDefine = notDefine;
    this.listOfSponsorOrDeveloper = listOfSponsorOrDeveloper;
    this.sponsorCompany = sponsorCompany;
    this.listOfLenderOrFinancier = listOfLenderOrFinancier;
    this.lenderCompany = lenderCompany;
    this.listOfContruction = listOfContruction;
    this.contructionCompany = contructionCompany;
    this.country = country;
    this.province = province;
    this.district = district;
    this.notDefine2 = notDefine2;
    this.tributary = tributary;
    this.latitude = latitude;
    this.longtitude = longtitude;
    this.proximity = proximity;
    this.anualOutput = anualOutput;
    this.notDefine3 = notDefine3;
    this.dataSource = dataSource;
    this.announcement = announcement;
    this.link = link;
    this.latestUpdate = latestUpdate;
}

app.get('/project', async (req, res) => {
    console.log("Calling")
    const projectId = req.query.projectId;
    file.download(function(err, content) {
        var data = Buffer.from(content).toString('utf8');
        const projectDocs = data.split('\n');
        var projects = [];
        for (let i = 1; i < 10; i++) {
            var quoteAppear = 0;
            var projectURL = "";
            const projectLine = projectDocs[i].split(',');
            for (let j = 1; j < projectLine.length; j++) {
                if (projectLine[j].includes('"') && quoteAppear === 0) {
                    try {
                        projectURL += projectLine[j];
                        j++;
                        while (!projectLine[j].includes('"')) {
                            projectURL += projectLine[j];
                            j++;
                        }
                        projectURL += projectLine[j];
                    }catch (err) {
                        break;
                    }
                    
                }
            }
            const pro2 = {
                'id': i,
                'projectName': projectLine[0],
                'subtype': projectLine[1],
                'currentStatus': projectLine[2],
                'Capacity': projectLine[3],
                'completionYear': projectLine[4],
                'notDefine': projectLine[5],
                'listOfSponsorOrDeveloper': projectLine[6],
                'sponsorCompany': projectLine[7],
                'listOfLenderOrFinancier': projectLine[8],
                'lenderCompany': projectLine[9],
                'listOfContruction': projectLine[10],
                'contructionCompany': projectLine[11],
                'country': projectLine[12],
                'province': projectLine[13],
                'district': projectLine[14],
                'notDefine2': projectLine[15],
                'tributary': projectLine[16],
                'latitude': projectLine[17],
                'longtitude': projectLine[18],
                'proximity': projectLine[19],
                'anualOutput': projectLine[20],
                'notDefine3': projectLine[21],
                'dataSource': projectLine[22],
                'announcement': projectLine[23],
                'link': projectURL,
                'latestUpdate': projectLine[25]

            }
            
            if (i == projectId) {
                res.send(pro2);
                break;
            }
                
                       
        }
    })
})

// building api ------------------------------
app.get('/', async (req, res) => {
    const maxGet = 10;
    file.download(function(err, content) {
        
        var data = Buffer.from(content).toString('utf8');
        const projectDocs = data.split('\n');
        var projects = [];
        for (let i = 1; i < projectDocs.length; i++) {
            var quoteAppear = 0;
            var projectURL = "";
            const projectLine = projectDocs[i].split(',');
            for (let j = 1; j < projectLine.length; j++) {
                if (projectLine[j].includes('"') && quoteAppear === 0) {
                    try {
                        projectURL += projectLine[j];
                        j++;
                        while (!projectLine[j].includes('"')) {
                            projectURL += projectLine[j];
                            j++;
                        }
                        projectURL += projectLine[j];
                    }catch (err) {
                        break;
                    }
                    
                }
            }
            const pro2 = {
                'id': i,
                'projectName': projectLine[0],
                'subtype': projectLine[1],
                'currentStatus': projectLine[2],
                'Capacity': projectLine[3],
                'completionYear': projectLine[4],
                'notDefine': projectLine[5],
                'listOfSponsorOrDeveloper': projectLine[6],
                'sponsorCompany': projectLine[7],
                'listOfLenderOrFinancier': projectLine[8],
                'lenderCompany': projectLine[9],
                'listOfContruction': projectLine[10],
                'contructionCompany': projectLine[11],
                'country': projectLine[12],
                'province': projectLine[13],
                'district': projectLine[14],
                'notDefine2': projectLine[15],
                'tributary': projectLine[16],
                'latitude': projectLine[17],
                'longtitude': projectLine[18],
                'proximity': projectLine[19],
                'anualOutput': projectLine[20],
                'notDefine3': projectLine[21],
                'dataSource': projectLine[22],
                'announcement': projectLine[23],
                'link': projectURL,
                'latestUpdate': projectLine[25]

            }
            
                
            
            projects.push(pro2);            
        }
        res.send(projects);
    })
    
    
})

app.post('/', async (req, res) => {
    const dataList = Object.values(req.body);
    var writingData = "";
    for (let i = 0; i < dataList.length; i++) {
        writingData += dataList[i] + ",";
    }
    
    console.log(writingData)
    file.download(function(err, content) {
        var data = Buffer.from(content).toString('utf8');

        var writtingFile = "";
        
        const contentParsing = data.split("\n");
        for (let i = 0; i < contentParsing.length; i++) {
            writtingFile += contentParsing[i] + "\n";
        }
        writtingFile +=  writingData
        file.save(writtingFile, err => {
            if (err) {
                console.log(err)
            }
        })
    })
    

    
    res.send("Sending done");
});

app.patch('/', async (req, res) => {
    const projectId = req.query.projectId;
    console.log(projectId)
    const dataList = Object.values(req.body);
    var writingData = "";
    for (let i = 1; i < dataList.length; i++) {
        writingData += dataList[i] + ",";
    }

    file.download(function(err, content) {
        var data = Buffer.from(content).toString('utf8');
        

        const projectDocs = data.split('\n');
    
        for (let j = 0; j < projectDocs.length; j++) {
            
            if (j == projectId) {
                projectDocs[j] = writingData;
                break;
            }
            
        }

        var fileContent = "";
        for (let i = 0; i < projectDocs.length; i++) {
            if (i < 10) {
                console.log(projectDocs[i])
            }
            
            if (i == projectDocs.length - 1) {
                fileContent += projectDocs[i];
            }else {
                fileContent += projectDocs[i] + "\n";
            }
        }


        file.save(fileContent, err => {
            if (err) {
                console.log(err)
            }
        })
    })

    
    res.send(req.body)
})

app.delete('/', async (req, res) => {
    const projectId = req.query.projectId;
    

    file.download(function(err, content) {
        var data = Buffer.from(content).toString('utf8');
        

        const projectDocs = data.split('\n');
        

        var fileContent = "";
        for (let i = 0; i < projectDocs.length; i++) {
            if (i == projectId) {
                continue;
            }

            

            if ((i == projectDocs.length - 1)){
                fileContent += projectDocs[i];
            }else {
                if ((i == projectDocs.length - 2) && (projectId == projectDocs.length - 1)) {
                    fileContent += projectDocs[i];
                }
                fileContent += projectDocs[i] + "\n";
            }
        }



        file.save(fileContent, err => {
            if (err) {
                console.log(err)
            }
        })
    })
    res.send("Delete Success")
})









app.listen(process.env.PORT || 9600, function() {
    console.log("Port is listing");
})