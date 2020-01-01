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

    addList(list_name: string){

    }

}