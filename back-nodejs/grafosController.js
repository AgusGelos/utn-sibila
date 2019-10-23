function conectarseBD(){

var OrientDB = require('orientjs');

var server = OrientDB({
   host:       'localhost',
   port:       2424,
   username:   'admin',
   password:   'admin'
});

//Connect to 'ppr'
var db = server.use({
    name: 'PPR',
    username: 'admin',
    password: 'admin'
});

return db;
}


exports.buscar = function (req, res) {
    
  //var query = req.body.query;

  var db = conectarseBD();

    db.query(
      'select * from Relacion limit 100'
     ).then(function(data){
        
      
        res.json({
            data
          });
     });
 

};


exports.buscarQuery = function (req, res) {
  
  var _query = req.query.query; 
  var db = conectarseBD();

  db.query(
          _query
   ).then(function(datos){
    var nodes = [];
    var edgesAux = [];  
    var edges = [];
  
    
    datos.forEach(function(item) {

      var obparse = JSON.parse(JSON.stringify(item)); 
      var nodo = Nodo(); 
      var grafo = Grafo();

      nodo.id= obparse["Nombre"]; //Para que solo tome la primer palabra
      nodo.name=obparse["Nombre"]; 
      edgesAux.push(obparse);
      grafo.nodo = nodo;
      //edgesAux.push(grafo);
      //console.log(grafo);
      nodos = {data: nodo};
      nodes.push(nodos);
      //console.log((item, index););
    });
    const edges1 = edgesAux.slice();
   
    for (var i = 0; i <edgesAux.length; i++) {
     
     // for (var j = 0; j < edges.length; j++) {

        var propiedades = Object.keys(edgesAux[i]);
        
        for(let prop of propiedades){
        var llave1 = prop.slice(0,3);

        if(llave1.includes('in')){
          //console.log("Nodo entrada:" + edges[i]["Nombre"]);          
          //console.log("arista:" + prop.slice(3));

          for(let edge of edgesAux[i][prop] ){
         
           // console.log("id:" + edge );
            //for interno
            for (var j = 0; j <edges1.length; j++) {

              var propiedadesBusqueda = Object.keys(edges1[j]);
              //console.log("propiedades" + propiedadesBusqueda);
              for(let props of propiedadesBusqueda){
                var llave2 = props.slice(0,4);
                //console.log(llave2);
                if(llave2.includes('out')){   
                  //console.log("arista:" + props.slice(4));
        
                  for(let edgeOut of edges1[j][props] ){
        
                    if(edge == edgeOut){

                      if(edges1[j]["Nombre"] != edgesAux[i]["Nombre"] ){
                       
                        var arista = Arista(); 
                        arista.source = edges1[j]["Nombre"] ;
                        arista.target = edgesAux[i]["Nombre"] ;
                        arista.name =  props.slice(4);
                        aristasEdge = {data: arista};
                        edges.push(aristasEdge);
                        console.log("Source" + edges1[j]["Nombre"] );
                        console.log("Target" + edgesAux[i]["Nombre"] );                      
                        console.log("AristaID" + edgeOut);
                        console.log("AristaNombre" + props.slice(4));
                        
                      }
                     
                    
                    }
                   
                  
                  }
        
                }
        
              }
            }
         
         
          }

      
         }
       
       
        }

      console.log("--------------------------------------------");
   
    }
    

      res.json({
        nodes, edges 
        });
   
   
     });


};




exports.prueba = function (req, res) {
  var db = conectarseBD();
  
  db.query(
      'select * from Concepto limit 1'
   ).then(function(datos){
    
    for (let item of datos) {
      console.log(item); // Will display contents of the object inside the array
      var obparse = JSON.parse(JSON.stringify(item));
      //console.log(obparse[4]);
      var keys = Object.keys(item);

      for(let key of keys){
        if(key.includes('in')){
          //add this object to the filtered array
          console.log(key);
          }
          if(key.includes('out')){
            //add this object to the filtered array
            console.log(key);
            }
      }

    } 
    

  
    
      res.json({
          datos
        });
   });


};


exports.buscar1 = function (req, res) {
  var db = conectarseBD();

  db.query(
      'select * from Concepto limit 10'
   ).then(function(datos){
    var nodes = [];
    var edgesAux = [];
    var aristas = [];

    var edges = [];
    //var edges1 = [];
    //var _data = new Object();
    
    datos.forEach(function(item) {

      var obparse = JSON.parse(JSON.stringify(item)); 
      var nodo = Nodo(); 
      var grafo = Grafo();

      nodo.id= obparse["Nombre"]; //Para que solo tome la primer palabra
      nodo.name=obparse["Nombre"]; 
      edgesAux.push(obparse);
      grafo.nodo = nodo;
      //edgesAux.push(grafo);
      //console.log(grafo);
      nodos = {data: nodo};
      nodes.push(nodos);
      //console.log((item, index););
    });
    const edges1 = edgesAux.slice();
    //var edges1 = edges;
   // console.log("en el edge...");
   /*  for (var j = 0; j <edges1.length; j++) {

      var propiedadesBusqueda = Object.keys(edges1[j]);
      //console.log("propiedades" + propiedadesBusqueda);
      for(let props of propiedadesBusqueda){
        var llave2 = props.slice(0,4);
        //console.log(llave2);
        if(llave2.includes('out')){   
          console.log("arista:" + props.slice(4));

          for(let edgeOut of edges1[j][props] ){

            console.log("id:" + edgeOut );
          
          }

        }

      } */

/*       console.log("en el edge"+edges[j])
      var propiedadesBusqueda = Object.keys(edges[j]);
      
      for(let prop2 of propiedadesBusqueda){
        var llave2 = prop2.slice(0,4);
       console.log(llave2);
        if(llave2.includes('out')){           
       
          console.log("Nodo Salida:" + edges[i]["Nombre"]);          
          console.log("arista:" + prop.slice(4));
          for(let edgeOut of edges[j][prop2] ){

            console.log("id:" + edgeOut );
          
          }
     
        }
     
      } */


    //}







    for (var i = 0; i <edgesAux.length; i++) {
     
     // for (var j = 0; j < edges.length; j++) {

        var propiedades = Object.keys(edgesAux[i]);
        
        for(let prop of propiedades){
        var llave1 = prop.slice(0,3);

        if(llave1.includes('in')){
          //console.log("Nodo entrada:" + edges[i]["Nombre"]);          
          //console.log("arista:" + prop.slice(3));

          for(let edge of edgesAux[i][prop] ){
         
           // console.log("id:" + edge );
            //for interno
            for (var j = 0; j <edges1.length; j++) {

              var propiedadesBusqueda = Object.keys(edges1[j]);
              //console.log("propiedades" + propiedadesBusqueda);
              for(let props of propiedadesBusqueda){
                var llave2 = props.slice(0,4);
                //console.log(llave2);
                if(llave2.includes('out')){   
                  //console.log("arista:" + props.slice(4));
        
                  for(let edgeOut of edges1[j][props] ){
        
                    if(edge == edgeOut){

                      if(edges1[j]["Nombre"] != edgesAux[i]["Nombre"] ){
                       
                        var arista = Arista(); 
                        arista.source = edges1[j]["Nombre"] ;
                        arista.target = edgesAux[i]["Nombre"] ;
                        arista.name =  props.slice(4);
                        aristasEdge = {data: arista};
                        edges.push(aristasEdge);
                        console.log("Source" + edges1[j]["Nombre"] );
                        console.log("Target" + edgesAux[i]["Nombre"] );                      
                        console.log("AristaID" + edgeOut);
                        console.log("AristaNombre" + props.slice(4));
                        
                      }
                     
                    
                    }
                   
                  
                  }
        
                }
        
              }
            }
         
         
          }

         // console.log("valores:" + edges[i][prop]);
         }
       
       
        }
       /*  for(let edgeIn of edges[3]){
          
          for(let edgeOut of edges[j].out){
          
              if(edgeIn==edgeOut){

                var arista = Arista(); 
                console.log("Nodo salida:" + edges[j].nodo.name);
                console.log("Nodo entrada:" + edges[i].nodo.name);
                console.log("Nombre del edge:" + edgeOut);
                
                console.log("-----------------------------------------------------------");
              }
          

          } 
          

        } */
     
        
     
     
     
      //}
      console.log("--------------------------------------------");
   
    }
    
/*     var grafo = new Grafo();
grafo.nodes= nodes;
grafo.edges= aristas; */
// var jsObj = {    nodes,   aristas     };
      res.json({
        nodes, edges 
        });
   
   
     });


};

function Nodo() {
  return {
    'id':   '',
    'name':    '',
    'weight':  100,
    'colorCode': 'blue',
    'shapeType':   'roundrectangle'
  };
};

function Grafo() {
  return {
    'nodes':  [],
    'edges':  []

   
  };
}; 

function Arista() {
  return {
    'source': '',
    'target': '',
    'colorCode': 'blue',
    'name': '',
    'strength': 10
  };
};

/* "data": {
  "source": "a",
  "target": "b",
  "colorCode": "blue",
  "name": "Tipo",
  "strength": 10
} */

