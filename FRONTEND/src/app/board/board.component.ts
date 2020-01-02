import { Component, OnInit, Input } from '@angular/core';
import { Board } from '../models/board.model';
import { BoardsService } from '../services/boards.service';
import { WholeBoard } from '../models/wholeboard.model';
import { List } from '../models/list.model';
import { Row } from '../models/row.model';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  @Input() b: Board;
  wholeBoard: WholeBoard;
  list: List;

  constructor(private boardsService: BoardsService) { }

  ngOnInit() {
    this.boardsService.getBoardById(this.b.board_id).subscribe(data => {
      this.boardsService.wholeBoard = data[0];
      this.wholeBoard = this.boardsService.wholeBoard;
      if (this.wholeBoard.lists[0].list_name == null) {
        this.wholeBoard.lists.pop();
      }
      //console.log(this.wholeBoard.board_name);
    }, error1 => console.log(error1));
  }

  addList(list_name: string) {
    if (list_name !== '') {
      let rows: Row[] = [];
      this.wholeBoard.lists.push(new List(0, list_name, rows));
    }
  }


}
