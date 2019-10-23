import { Component, OnChanges, Renderer, ElementRef, Input, Output, EventEmitter } from '@angular/core';
import { ShareService } from 'services/share.service';
import { Subscription } from 'rxjs';
import { Metrica } from 'app/models/metrica';

declare var cytoscape: any;



@Component({
    selector: 'ng2-cytoscape',
    template: '<div id="cy"></div>',
    styles: [`#cy {
        height: 100%;
        width: 100%;
        position: relative;
        left: 0;
        top: 0;
    }`]
})


export class NgCytoComponent implements OnChanges {

    @Input() public elements: any;
    @Input() public style: any;
    @Input() public layout: any;
    @Input() public zoom: any;
    metrica : Metrica;
    subscription: Subscription;
    @Output() select: EventEmitter<any> = new EventEmitter<any>();

    @Output() metricaGrafo: EventEmitter<Metrica> = new EventEmitter<Metrica>();
    GradoEntrante: boolean = false;

    GradoSaliente: boolean = false;
    GradoVertice: boolean = false;

    nodes;

    public constructor(private renderer: Renderer, private el: ElementRef, private recibirService: ShareService) {
        this.metrica = new Metrica();
        this.layout = this.layout || {
            name: 'grid',
            directed: true,
            padding: 0
        };

        this.zoom = this.zoom || {
            min: 0.1,
            max: 1.5
        };

        this.style = this.style || cytoscape.stylesheet()

            .selector('node')
            .css({
                'shape': 'data(shapeType)',
                'width': 'mapData(weight, 40, 80, 20, 60)',
                'content': 'data(name)',
                'text-valign': 'center',
                'text-outline-width': 1,
                'text-outline-color': 'data(colorCode)',
                'background-color': 'data(colorCode)',
                'color': '#fff',
                'font-size': 10
            })
            .selector(':selected')
            .css({
                'border-width': 1,
                'border-color': 'black'
            })
            .selector('edge')
            .css({
                'curve-style': 'bezier',
                'opacity': 0.666,
                'width': 'mapData(strength, 70, 100, 2, 6)',
                'target-arrow-shape': 'triangle',
                'line-color': 'data(colorCode)',
                'source-arrow-color': 'data(colorCode)',
                'target-arrow-color': 'data(colorCode)',
                'label': 'data(name)',
                'font-size': 10,
                'text-margin-y': -6
            })
            .selector('edge.questionable')
            .css({
                'line-style': 'dotted',
                'target-arrow-shape': 'diamond'
            })
            .selector('.faded')
            .css({
                'opacity': 0.25,
                'text-opacity': 0
            });

        this.subscription = this.recibirService.getMessage().subscribe(message => {

            switch (message.text) {
                case "GradoVertice":
                    this.GradoVertice = true;
                    this.render();
                    break;
                case "GradoEntrante":
                    this.GradoEntrante = true;
                    this.render();
                    break;
                case "GradoSaliente":
                    this.GradoSaliente = true;
                    this.render();
                    break;
            }
            /*      if (message.text) {
                   console.log('recibido'+ message.text);
                   this.GradoVertice=true;
                   this.render();
                 }  */
        });



    }

    public ngOnChanges(): any {
        this.render();
        console.log(this.el.nativeElement);

    }

    public render() {
        //let myClonedObject = Object.assign({}, this.elements);
        var  myClonedObject = this.deepCopy(this.elements); 
        //const elementsAux= this.elements.slice();
        let cy_contianer = this.renderer.selectRootElement("#cy");
        let localselect = this.select;
        let cy = cytoscape({
            container: cy_contianer,
            layout: this.layout,
            minZoom: this.zoom.min,
            maxZoom: this.zoom.max,
            style: this.style,
            elements:  myClonedObject,
        });
       
       // var bf = cy.elements().bellmanFord({ root: "#PARADIGMA" });
        //console.log(bf);
        this.nodes=cy.nodes();
        cy.nodes().roots(function (raiz) {

            raiz.data('colorCode', 'orange');

            cy.nodes().leaves(function (hoja) {

                var aStar = cy.elements().aStar({ root: raiz, goal: hoja, directed: true });
                console.log(aStar.distance);
                //console.log(aStar.path.select());
            })



        })

        cy.nodes().leaves(function (element) {


            element.data('colorCode', 'green');
        })

        this.metrica.totalDegree = cy.nodes().totalDegree();
        this.metrica.maxDegree=cy.nodes().maxDegree();
        this.metrica.minDegree=cy.nodes().minDegree();

        this.metricaGrafo.emit(this.metrica);
     /*    console.log('TotalDegree:' + cy.nodes().totalDegree());
        console.log('maxDegree:' + cy.nodes().maxDegree());
        console.log('minDegree:' + cy.nodes().minDegree()); */
        
        if (this.GradoVertice) {
            cy.nodes(function (element) {
                if (element.isNode()) {
                    element.data('name', element.data('name') + ' : ' + element.degree());

                    //console.log(element.data('name') +)
                }

            }
            )
            this.GradoVertice = false;
        }

        if (this.GradoEntrante) {
            cy.nodes(function (element) {
                if (element.isNode()) {
                    element.data('name', element.data('name') + ' : ' + element.indegree());

                    //console.log(element.data('name') +)
                }

            }
            )
            this.GradoEntrante = false;
        }

        if (this.GradoSaliente) {
            cy.nodes(function (element) {
                if (element.isNode()) {
                    element.data('name', element.data('name') + ' : ' + element.outdegree());

                }

            }
            )
            this.GradoSaliente = false;
        }



        cy.on('tap', 'node', function (e) {
            var node = e.target;
            var neighborhood = node.neighborhood().add(node);
            cy.elements().addClass('faded');
            neighborhood.removeClass('faded');
            localselect.emit(node.data('name'));


        });

        cy.on('tap', function (e) {
            if (e.target === cy) {
                cy.elements().removeClass('faded');



            }
        });
       // myClonedObject = Object.assign({}, this.elements );
         
        /*        cy.filter(function(element, i){
                   return element.isNode() && element.data('Name') == 'RELACIONAL';
                 }); */
    }

    ngOnDestroy() {
        // unsubscribe to ensure no memory leaks
        this.subscription.unsubscribe();
    }

    deepCopy(obj) {
        var copy;
    
        // Handle the 3 simple types, and null or undefined
        if (null == obj || "object" != typeof obj) return obj;
    
        // Handle Date
        if (obj instanceof Date) {
            copy = new Date();
            copy.setTime(obj.getTime());
            return copy;
        }
    
        // Handle Array
        if (obj instanceof Array) {
            copy = [];
            for (var i = 0, len = obj.length; i < len; i++) {
                copy[i] = this.deepCopy(obj[i]);
            }
            return copy;
        }
    
        // Handle Object
        if (obj instanceof Object) {
            copy = {};
            for (var attr in obj) {
                if (obj.hasOwnProperty(attr)) copy[attr] = this.deepCopy(obj[attr]);
            }
            return copy;
        }
    
        throw new Error("Unable to copy obj! Its type isn't supported.");
    }
    
     

}
