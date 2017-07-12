import { Injectable, NgZone, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/Rx';
import 'rxjs/add/operator/map';
import { Observable, BehaviorSubject } from 'rxjs';

import { TestEvent } from './test-event';

@Injectable()
export class TestService {
//https://stackoverflow.com/questions/43989889/angular-client-of-spring-boot-2-reactor-flux-api
  private zone:NgZone;
  private event: Observable<TestEvent>;
  private eventSource: EventSource;
  private events: BehaviorSubject<TestEvent> = new BehaviorSubject<TestEvent>(null);

  constructor(private http: Http) {
    this.zone = new NgZone({enableLongStackTrace: false});
  }

  ngOnInit(): void {
  }

  private createEventObservable(): Observable<TestEvent> {
    return this.events.asObservable();
  }

  private createEventSource(): EventSource {
      const eventSource = new EventSource('http://localhost:8082/test/events');
      eventSource.onmessage = sse => {
        console.log(sse);
        const event: TestEvent = new TestEvent(JSON.parse(sse.data));
        this.zone.run(()=>this.events.next(event));
      };
      eventSource.onerror = err => this.events.error(err);
      return eventSource;
  }

  public eventTest(): Observable<any> {
    console.log("initiatiing eventSource");
    this.eventSource = this.createEventSource();
    console.log("initiatiing event");
    this.event = this.createEventObservable();
    return this.event;
  }

  public sayHello(user: string): Observable<any> {
    // let observable = Observable.create(
    //   observer => {
    //     const eventSource = new EventSource(`http://localhost:8082/test/hello/${user}`);
    //     eventSource.onmessage = x => observer.next(console.log(JSON.parse(x.data)));
    //     eventSource.onerror = x => observer.error(console.log('EventSource failed'));
    //     return () => {
    //         eventSource.close();
    //       };
    // });
    // return observable;
    return this.http
      .get(`http://localhost:8082/test/hello/${user}`)
      .map((res: any) => res.json());
  }
}
