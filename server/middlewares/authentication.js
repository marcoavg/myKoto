const jwt = require('jsonwebtoken')


let checkToken = (req, res, next) => {
    let token = req.get('token')
    jwt.verify(token, process.env.SEED, (error, decoded) => {
        if (error) {
            return res.status(401).json({
                error: {
                    message: 'el token no es valido'
                }
            })
        }
        req.user = decoded.user
        next()
    })

}

let checkAdminRol = (req, res, next) => {
    let user = req.user
    console.log(user.role);

    if (user.role === 'ADMIN_ROLE') {
        next()
        return
    } else {
        return res.json({
            ok: false,
            message: 'el usuario no tiene acceso'
        })
    }

}

let checkTokenImg = (req, res, next) => {
    let token = req.query.token
    jwt.verify(token, process.env.SEED, (err, decoded) => {
        if (err) {
            return res.status(401).json({
                err: {
                    message: 'el token no es valido'
                }
            })
        }

        req.user = decoded.user
        next()
    })

}
module.exports = {
    checkToken,
    checkAdminRol,
    checkTokenImg
}