import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShareService {

  constructor() { }

  private subject = new Subject<any>();

  sendMessage(message: string) { //enviar mensaje
      this.subject.next({ text: message });
  }

  clearMessages() {//borrar mensaje
      this.subject.next();
  }

  getMessage(): Observable<any> {
      return this.subject.asObservable();
  }
}
