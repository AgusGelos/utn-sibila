// Carga Libreria Express
let express = require('express')
// Libreria para parsear el body
let bodyParser = require('body-parser');
//Libreria Cross origin
var cors = require('cors');

// Inicializar la APP
let app = express();

// Habilitar el cross origin
app.use(cors());

let apiRoutes = require("./api-routes")
// Configure bodyparser to handle post requests
app.use(bodyParser.urlencoded({
    extended: true
 }));

 app.use(bodyParser.json());

// Puerto donde levanta la app
var port = process.env.PORT || 8080;
// Send message for default URL
app.get('/', (req, res) => res.send('Se lmente el server. Bienvevanto correctaenido a los grafos'));
// Launch app to listen to specified port
// Import routes

// Usamos el api route
app.use('/api', apiRoutes)

app.listen(port, function () {
     console.log("Se levanto la app en el puerto" + port);
});

