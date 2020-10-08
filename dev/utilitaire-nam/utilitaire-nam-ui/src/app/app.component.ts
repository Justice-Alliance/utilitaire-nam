import { Component, OnDestroy, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
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
export class AppComponent implements OnInit {

  NomApplication = 'Utilitaire NAM Application';
  topMenuItems = topMenu.topMenuItems;

  constructor(private titleService:Title) {}

  ngOnInit(): void {
    console.log(this.NomApplication.concat(' démarrée !'));
    this.setNomApplication(this.NomApplication)
  }

  private setNomApplication(titre:string) {
    this.titleService.setTitle(titre);
  }  
}
