export enum target {
   BLANK = "_blank",
   SELF = "_self",
   PARENT = "_parent",
   TOP = "top"
}

export interface MenuItem {
   title?: string; // Texte affiché à côté de l'icône
   link?: string;
   icon?: string;
   visible?: boolean;  // Si à True, l'item est visible.
   [key: string]: any;
}