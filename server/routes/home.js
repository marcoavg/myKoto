var express = require('express')
var bcrypt = require('bcrypt')
var _ = require('underscore')
var Koto = require('../models/myKoto')
var { checkToken } = require('../middlewares/authentication')
var app = express()
var fs = require('fs')
var path = require('path')


app.post('/home', function(req, res) {
    let file = req.files
    if (file) {
        let file = req.files.file;

        let ext = ['png', 'jpg', 'jpeg']
        let nameCut = file.name.split('.')
        let extencion = nameCut[nameCut.length - 1]

        if (ext.indexOf(extencion) < 0) {
            return res.status(400).json({
                error: {
                    error: 'las extenciones validas son ' + ext.join(', ')
                }
            })
        }
    }
    let body = req.body
    let id = 0
    Koto.count((error, count) => {
        id = count + 1
        let koto = new Koto({
            name: body.name,
            description: body.description,
            user: body.id,
            id: id
        })

        koto.save((error, kotoDB) => {
            if (error) {
                return res.status(400).json({
                    error
                })
            }
            if (file) {
                saveImage(res, req, kotoDB._id)
            } else {
                res.json({
                    koto: kotoDB
                })
            }
        })
    })


})

app.get('/home', (req, res) => {
    let page = req.query.page || 1
    page = Number(page)
    let limit = req.query.limit || 10
    limit = Number(limit)

    let skip = (page - 1) * limit;

    Koto.find({ dispose: true })
        .limit(limit)
        .skip(skip)
        .sort({ createdAt: -1 })
        .populate('user', 'name')
        .exec((error, kotos) => {
            if (error) {
                return res.status(400).json({
                    error
                })
            }
            Koto.count({ dispose: true }, (error, count) => {
                res.json({
                    kotos,
                    pages: Math.ceil(count / limit)
                })
            })

        })

})


function saveImage(res, req, id) {
    let file = req.files.file;
    let cutName = file.name.split('.')
    let extencion = cutName[cutName.length - 1]
    let nameFile = `${id}-${ new Date().getMilliseconds()}.${extencion}`
    file.mv(`public/coto/${nameFile}`, (error) => {
        if (error) {
            return res.status(500).json({
                error
            })
        } else
            imagenKoto(id, res, nameFile)
    });
}


function imagenKoto(id, res, nameFile) {
    Koto.findById(id, (error, kotoDB) => {
        if (error) {
            deleteFile(nameFile)
            return res.status(500).json({
                error
            })
        }

        if (!kotoDB) {
            deleteFile(nameFile)
            return res.status(400).json({
                error: {
                    mensaje: `no existe el coto`
                }
            })
        }
        deleteFile(kotoDB.img)
        kotoDB.img = nameFile
        kotoDB.save((error, kotoSave) => {
            if (error) {
                return res.status(500).json({
                    error
                })
            }

            res.json({
                coto: kotoSave
            })
        })

    })
}

function deleteFile(filename) {
    let pathImg = path.resolve(__dirname, `../../public/coto/${filename}`)
    if (fs.existsSync(pathImg)) {
        fs.unlinkSync(pathImg)
    }
}


module.exports = app