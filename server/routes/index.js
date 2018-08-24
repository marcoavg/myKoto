const express = require('express')
const app = express()

var fileUpload = require('express-fileupload');

app.use(fileUpload());
app.use(require('./users'))
app.use(require('./login'))
app.use(require('./home'))

module.exports = app