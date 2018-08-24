var mongoose = require('mongoose');
var mongoosePaginate = require('mongoose-paginate');
var Schema = mongoose.Schema;

var kotoSchema = new Schema({
    name: { type: String, required: [true, 'El nombre es necesario'] },
    description: { type: String, required: false },
    dispose: { type: Boolean, required: true, default: true },
    user: { type: Schema.Types.ObjectId, ref: 'User' },
    img: { type: String, required: false },
    createdAt: { type: Date, default: Date.now },
    id: { type: Number, required: false }
});

kotoSchema.plugin(mongoosePaginate);
module.exports = mongoose.model('myKoto', kotoSchema)