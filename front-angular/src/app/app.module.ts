import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { GraphComponent } from './graph/graph.component';
import { AppComponent } from './app.component';
import {NgCytoComponent} from './ng-cyto/ng-cyto.component';
import { NodosService } from 'services/nodos.service';
import { HttpClientModule } from '@angular/common/http';
import { ShareService } from 'services/share.service';

@NgModule({
  declarations: [
    AppComponent,
    NgCytoComponent,
    GraphComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
    
  ],
  providers: [NodosService, ShareService],
  bootstrap: [AppComponent]
})
export class AppModule { }
