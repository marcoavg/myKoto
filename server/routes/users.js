var express = require('express')
var bcrypt = require('bcrypt')
var _ = require('underscore')
var User = require('../models/user')
var { checkToken } = require('../middlewares/authentication')
var app = express()
var fs = require('fs')
var path = require('path')


app.post('/user', function(req, res) {
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
    let user = new User({
        name: body.name,
        email: body.email,
        password: bcrypt.hashSync(body.password, 10),
        role: body.role
    })

    user.save((error, userDB) => {
        if (error) {
            return res.status(400).json({
                error
            })
        }
        if (file) {
            saveImage(res, req, userDB._id)
        } else {
            res.json({
                user: userDB
            })
        }
    })

})


app.get('/user/:id', [checkToken], (req, res) => {
    let id = req.params.id
    User.findById(id, (error, userDB) => {
        if (error)
            return res.status(400).json({ error })
        if (userDB === false)
            return res.status(400).json({ error })
        else
            res.json({ userDB })

    })
})


function saveImage(res, req, id) {
    let file = req.files.file;
    let cutName = file.name.split('.')
    let extencion = cutName[cutName.length - 1]
    let nameFile = `${id}-${ new Date().getMilliseconds()}.${extencion}`
    file.mv(`public/users/${nameFile}`, (error) => {
        if (error) {
            console.log("jsdndfsdkjf");
            return res.status(500).json({
                error

            })
        } else
            imagenUsuario(id, res, nameFile)
    });
}

function imagenUsuario(id, res, nameFile) {
    User.findById(id, (error, userDB) => {
        if (error) {
            deleteFile(nameFile)
            return res.status(500).json({
                error
            })
        }

        if (!userDB) {
            deleteFile(nameFile)
            return res.status(400).json({
                error: {
                    mensaje: `no existe el usuario`
                }
            })
        }
        deleteFile(userDB.img)
        userDB.img = nameFile
        userDB.save((error, userSave) => {
            if (error) {
                return res.status(500).json({
                    error
                })
            }

            res.json({
                user: userSave
            })
        })

    })
}


function deleteFile(filename) {
    let pathImg = path.resolve(__dirname, `../../public/users/${filename}`)
    if (fs.existsSync(pathImg)) {
        fs.unlinkSync(pathImg)
    }
}

module.exports = app