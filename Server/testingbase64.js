const fs = require('fs');

const bsFile = "";

const buffer = Buffer.from(bsFile, "base64");
fs.writeFileSync('new-img.png', buffer)