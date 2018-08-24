process.env.PORT = process.env.PORT || 3000


// Entorno 
process.env.NODE_ENV = process.env.NODE_ENV || 'dev'
let urlDB
if (process.env.NODE_ENV === 'dev') {
    urlDB = 'mongodb://localhost:27017/myKoto'
} else {
    //mongodb://myKotoUserDB:myKotoUserDB01@ds223812.mlab.com:23812/mykotodb
    urlDB = process.env.MONGO_URI
}
process.env.URLDB = urlDB


///vencimiento token
process.env.CAD_TOKEN = '100000h'
    ///seed
process.env.SEED = process.env.SEED || 'este-es-el-seed-dev'

//Hellsing2000