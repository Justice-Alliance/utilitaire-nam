import { MenuItem } from '../lib/menu-interface';

const topMenuItems: MenuItem[] = [
    {
      id: "menu-valider",
      title: "Valider",
      link: "/valider",
      icon: "",
      visible: true
    },
    {
      id: "menu-generer",
      title: "Générer NAM",
      link: "/generer",
      icon: "",
      visible: true
    },
    {
      id: "menu-information",
      title: "Obtenir information sur le NAM",
      link: "/information",
      icon: "",
      visible: true
    }
   ];

  export const topMenu={"topMenuItems":topMenuItems};
