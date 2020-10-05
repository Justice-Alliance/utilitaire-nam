export enum target {
   BLANK = "_blank",
   SELF = "_self",
   PARENT = "_parent",
   TOP = "top"
}

export interface MenuItem {
   id?:string;
   title?: string; 
   link?: string;
   icon?: string;
   visible?: boolean;  
   [key: string]: any;
}