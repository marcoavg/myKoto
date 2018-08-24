const mongoose = require('mongoose')
var mongoosePaginate = require('mongoose-paginate');
const uniqueValidator = require('mongoose-unique-validator');

let Schema = mongoose.Schema
let roleValid = {
    values: ['ADMIN_ROLE', 'USER_ROLE'],
    message: '{VALUE} no es un rol valido'
}


let userSchema = new Schema({
    name: { type: String, required: [true, 'El nombre es requerido'] },
    email: { type: String, unique: true, required: [true, 'El correo es requerido'] },
    password: { type: String, required: [true, 'pass es requerido'] },
    img: { type: String, required: false },
    role: { type: String, default: 'USER_ROLE', enum: roleValid },
    status: { type: Boolean, default: true },
    token: { type: String },
    createdAt: { type: Date, default: Date.now }
})
userSchema.methods.toJSON = function() {
    let user = this
    let userObject = user.toObject()
    delete userObject.password
    return userObject
}
userSchema.plugin(uniqueValidator, { message: '{PATH} debe de ser unico' })
userSchema.plugin(mongoosePaginate);
module.exports = mongoose.model('User', userSchema)