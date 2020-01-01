export class Board {
    public board_name: String;
    //public rowsAmount: number;
    public board_id: number;
    /*
    constructor(id: number, name: String, rowsAmount: number) {
        this.name = name;
        this.rowsAmount = rowsAmount;
        this.id = id;
    }


    constructor(apiResponse: any) {
        this.board_name = apiResponse.board_name;
        this.board_id = apiResponse.board_id;
    }
    */
    
    constructor(board_id: number, board_name: String) {
        this.board_name = board_name;
        this.board_id = board_id;
    }
    

    getBoardId(){
        return this.board_id;
    }

    getBoardName(){
        return this.board_name;
    }
}