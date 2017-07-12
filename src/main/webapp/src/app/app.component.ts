import { Component } from '@angular/core';
import { TestService } from './test.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [TestService]
})
export class AppComponent {
  title = 'app';
  constructor( private testService: TestService ){
  }

  private onTestHello(): void{
    // this.testService.sayHello('world').subscribe(
    //   (ans) => {
    //     console.log(ans);
    //   },
    //   (err)=>{
    //     console.log(err);
    //   }
    // );
    this.testService.eventTest().subscribe(
      (ans) => {
        console.log(ans);
      },
      (err)=>{
        console.log(err);
      }
    )
  }

}
