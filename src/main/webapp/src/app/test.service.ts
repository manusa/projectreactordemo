import { Injectable, NgZone } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/Rx';
import 'rxjs/add/operator/map';
import { Observable} from 'rxjs/Rx';
import { EventSource } from 'eventsource'

@Injectable()
export class TestService {

  private zone:NgZone;

  constructor(private http: Http) {
    this.zone = new NgZone({enableLongStackTrace: false});
  }

  public sayHello(user: string): Observable<any> {
    let observable = Observable.create(
      observer => {
        const eventSource = new EventSource('http://localhost:8082/test/hello/${user}');
        eventSource.onmessage = x => observer.next(console.log(JSON.parse(x.data)));
        eventSource.onerror = x => observer.error(console.log('EventSource failed'));
        return () => {
            eventSource.close();
          };
    });
    return this.http
      .get(`http://localhost:8082/test/hello/${user}`)
      .map((res: any) => res.json());
  }
}
