import { Injectable } from '@angular/core'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Board } from '../models/board.model';
import { WholeBoard } from '../models/wholeboard.model';

@Injectable({ providedIn: 'root' })
export class BoardsService {

    private http: HttpClient;

    boards: Board[];
    wholeBoard: WholeBoard;

    constructor(http: HttpClient) {
        this.http = http;
    }

    getBoardsInfo() {
        return this.http.get<[]>('http://localhost:8080/board');
    }

    getBoardById(boardId: number) {
        return this.http.get('http://localhost:8080/board/' + boardId);
    }

    addBoard(board_name: string) {
        let json = JSON.stringify({ board_name: board_name })
        return this.http.post('http://localhost:8080/board/insert', json, {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            })
        });
    }

    addRow(board_id: string, list_id: string, row_id: string, row_name: string){
        let json = JSON.stringify({"lists":{"list_id":list_id,"rows":{"row_id":row_id,"row_name":row_name}}});
        console.log(json);
         return this.http.post('http://localhost:8080/board/' + board_id + '/insertRow', json, {
             headers: new HttpHeaders({
                 'Content-Type': 'application/json',
                 'Accept': 'application/json'
             })
         });
    }

    addList(board_id: string, list_order: number, list_name: string){
        let json = JSON.stringify({"board_id":board_id,"list_name":list_name});
        console.log(json);
         return this.http.post('http://localhost:8080/board/' + board_id + '/insertList', json, {
             headers: new HttpHeaders({
                 'Content-Type': 'application/json',
                 'Accept': 'application/json'
             })
         });
    }

    updateBoardName(board_id: string, board_name: string){
        let json = JSON.stringify({"board_name":board_name});
         return this.http.post('http://localhost:8080/board/' + board_id + '/updateName', json, {
             headers: new HttpHeaders({
                 'Content-Type': 'application/json',
                 'Accept': 'application/json'
             })
         });
    }

    getRowDetails(board_id: number, list_order: number, row_order: number){
        return this.http.get('http://localhost:8080/board/' + board_id + "/" + list_order + "+" + row_order);
    }

}