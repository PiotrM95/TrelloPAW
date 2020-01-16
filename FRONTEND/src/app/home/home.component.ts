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
  selectedBoardForRename = -1;
  showRenameInput = false;
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

  renameBoard(board_id: number, board_name: string) {
    this.boardsService.updateBoardName(board_id.toString(), board_name.toString()).subscribe(data => {
      console.log(data);
    });
    for (let board of this.boards) {
      if (board_id === board.getBoardId()) {
        board.board_name = board_name;
      }
    }
  }

  selectBoardForRename(board_id: number) {
    this.selectedBoardForRename = board_id;
    this.showRenameInput = true;
  }

  deleteBoard(board_id: number) {
    let i = 0;
    for (let board of this.boards) {
      if (board_id === board.getBoardId()) {
        this.boards.splice(i, 1);
      }
      i++;
    }
  }

}