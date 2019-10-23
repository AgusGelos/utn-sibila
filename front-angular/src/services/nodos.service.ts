import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http'; 

@Injectable({
  providedIn: 'root'
})
export class NodosService {
  
private url = 'http://localhost:8080/api/grafos';

private urlQuery = 'http://localhost:8080/api/query';
  constructor(private http: HttpClient) {
   
    this.getJSON().subscribe(data => {
      console.log(data);
  });

   }

  public getJSON(): Observable<any> {
    return this.http.get("./assets/test.json");
  }

  public getNodos(): Observable<any> {
    return this.http.get(this.url);
  }

  getNodosQuery(_query:string): Observable<any>  {
    // now returns an Observable of Config
    let data = {query: _query};
    return this.http.get<any>(this.urlQuery,{params: data});
  }

  public postGrafo(query:String) : Observable<any>{

    return this.http.post<any>(this.url,query);

  }
}
