import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { environment } from 'src/environments/environment';
import { topMenu } from './components/topMenu';


export interface DateFormat {
  day: string;
  month: string;
  year: string;
 }
 
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

  title = 'utilitaire-nam-ui';
  topMenuItems = topMenu.topMenuItems;
  private urlUtilitaireNamApi: string = environment.urlUtilitaireNAM;
  
  private souscriptions = new Subscription();

  constructor() { }


  ngOnInit(): void {
    console.log("init called !");
  }

  ngOnDestroy(): void {
    if (this.souscriptions) {
      this.souscriptions.unsubscribe();
    }
  }
}
