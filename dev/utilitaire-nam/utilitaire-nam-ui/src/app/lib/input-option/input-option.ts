export interface InputOption{
    label:string;
    value:string;
    description?:string;
    selected?:boolean;
}

export interface InputOptionCollection{
    name:string;
    options:Array<InputOption>;
}