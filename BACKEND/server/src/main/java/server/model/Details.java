package server.model;

import java.util.ArrayList;
import java.util.List;

public class Details {
    Row row;
    List<Comment> comments;

    public Details(){
        row=new Row();
        comments=new ArrayList<>();
    }
    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
