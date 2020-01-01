import { Row } from '../models/row.model';

export class List {
    constructor(public list_id: number, public list_name: String, public rows: Row[]){

    }
}