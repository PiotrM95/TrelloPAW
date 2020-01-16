import { Component, OnInit, Input } from '@angular/core';
import { List } from '../models/list.model';
import { Row } from '../models/row.model';
import { BoardsService } from '../services/boards.service';


@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  @Input() list: List;
  @Input() board_id: number;
  listExists: boolean;
  rows: Row[] = [];
  showRowInput: boolean;
  rowInput: string;
  deleteRowButton: boolean;
  selectedRow = -1;

  constructor(private boardsService: BoardsService) { }

  ngOnInit() {
    console.log(this.list);
    this.rows = this.list.rows;
    this.showRowInput = false;

    if (this.list.list_name != null) {
      this.listExists = true;
    } else {
      this.listExists = false;
    }
    //row_description loading
    let i = 1;
    for (let row of this.rows) {
      this.getRowDetails(i);
      i++;
    }
  }

  onAddCardClick() {
    this.showRowInput = true;
  }

  onHideClick() {
    this.showRowInput = false;
  }

  addNewRow(text: string) {
    this.boardsService.addRow(this.board_id.toString(), this.list.list_id.toString(), (this.rows.length + 1).toString(), text).subscribe(data => {
      console.log(data);
    });
    if (text === '') {
      this.showRowInput = true;
    } else {
      this.showRowInput = false;
      this.rows.push(new Row(0, text, null));
    }
  }

  showDeleteRowButton(row_id: number, value: boolean) {
    this.deleteRowButton = !this.deleteRowButton;
    this.selectedRow = row_id;
  }

  deleteRow(row_id: number) {
    let i = 0;
    for (let row of this.rows) {
      if (row.row_id == row_id) {
        this.rows.splice(i, 1);
      }
      i++;
    }
  }

  getRowDetails(row_order: number) {
    this.boardsService.getRowDetails(this.board_id, this.list.list_id, row_order).subscribe(data => {
      let row = data["row"];
      let rowname = row["row_name"];
      let desc = row["row_description"];
      let i = 0;
      for (let row of this.rows) {
        if (row.row_name == rowname) {
          this.rows[i].row_description = desc;
        }
        i++;
      }
    }, error1 => console.log(error1));
  }

}
