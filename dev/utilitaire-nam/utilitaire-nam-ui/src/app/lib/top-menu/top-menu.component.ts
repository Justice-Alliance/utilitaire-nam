import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from '../menu-interface';

@Component({
  selector: 'app-top-menu',
  templateUrl: './top-menu.component.html',
  styleUrls: ['./top-menu.component.css']
})
export class TopMenuComponent  {
  
  @Input()
  public topMenuItems: MenuItem[];
  
  constructor(private router:Router) {}

  isMenuItemActif(link:string):boolean{
    return this.router.url.includes(link);
  }

}
