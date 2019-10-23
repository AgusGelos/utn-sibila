/* "data":{  
    "id":"a",
    "name":"Paradigma",
    "weight":100,
    "colorCode":"blue",
    "shapeType":"roundrectangle"
 } */

 export class Nodo{

    
    id: string ;
    name: string ;
    weight:  number ;
    colorCode:  string ;
    shapeType:  string ;

    constructor(){

    }
    
}

export class data{

   data: {}
    constructor(_nodo:Nodo){
     this.data= _nodo;
    }

    
}