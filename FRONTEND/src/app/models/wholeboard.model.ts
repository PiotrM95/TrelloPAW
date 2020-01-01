import { List } from '../models/list.model';

export class WholeBoard {
    public board_name: String;
    public board_id: number;
    public lists: List[];

    constructor(board_id: number, board_name: String, lists: List[]) {
        this.board_id = board_id;
        this.board_name = board_name;
        this.lists = lists;
    }

}