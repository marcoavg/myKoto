const express = require('express')
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')
const User = require('../models/user')
const { checkToken } = require('../middlewares/authentication')
const app = express()

app.post('/login', (req, res) => {
    let body = req.body
    User.findOne({ email: body.email }, (err, userDB) => {
        if (err) {
            return res.status(500).json({
                err
            })
        }
        if (!userDB) {
            return res.status(400).json({
                ok: false,
                err: {
                    message: '(Usuario) o contraseña incorrectos'
                }
            })
        }

        if (!bcrypt.compareSync(body.password, userDB.password)) {
            return res.status(400).json({
                err: {
                    message: 'Usuario o (contraseña) incorrectos'
                }
            })
        }
        let token = jwt.sign({
            user: userDB
        }, process.env.SEED, { expiresIn: process.env.CAD_TOKEN })
        userDB.token = token
        res.json({
            user: userDB
        })
    })


})



module.exports = app