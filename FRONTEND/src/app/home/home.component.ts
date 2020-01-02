import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Board } from '../models/board.model';
import { BoardsService } from '../services/boards.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  loadedFeature = 'show-boards';
  selectedBoard: Board;

  boards: Board[] = [];

  constructor(private boardsService: BoardsService) { }

  ngOnInit() {
    console.log(this.boards);
    this.getBoardsFromApi();
  }

  goToBoard(id: number) {
    this.selectedBoard = this.getSelectedBoard(id);
    this.loadedFeature = 'board';
  }

  onNavigate(feature: string) {
    this.loadedFeature = feature;
  }

  getMaxId() {
    let maxId = 0;
    for (let board of this.boards) {
      if (maxId <= board.getBoardId()) {
        maxId = board.getBoardId();
      }
    }
    return maxId;
  }

  getAmountOfLists(board_id:number){
    let amount = 0;
    this.boardsService.getBoardById(board_id).subscribe(data => {
      console.log(data[0]);
      console.log('chuj');
      this.boardsService.wholeBoard = data[0];
      amount = this.boardsService.wholeBoard.lists.length;
    }, error1 => console.log(error1));
  }

  addNewBoard(boardName: string) {
    this.loadedFeature = 'show-boards';
    this.boardsService.addBoard(boardName).subscribe(data => {
      console.log(data);
    });
  }

  getSelectedBoard(board_id: number) {
    let b: Board;
    for (let board of this.boards) {
      if (board_id === board.getBoardId()) {
        b = board;
      }
    }
    return b;
  }

  getBoardsFromApi() {
    this.boardsService.getBoardsInfo().subscribe(data => {
      this.boardsService.boards = data;
      for (let obj of this.boardsService.boards) {
        this.boards.push(new Board(obj.board_id, obj.board_name));
      }
      console.log('Dodano tablice z bazy danych');
    }, error1 => console.log(error1));
  }

  setBoards(boards: Board[]) {
    this.boards = boards;
  }
}