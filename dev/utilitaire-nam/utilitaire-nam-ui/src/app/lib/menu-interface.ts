export interface MenuItem {
   id?:string;
   title?: string; 
   link?: string;
   icon?: string;
   visible?: boolean;  
   [key: string]: any;
}