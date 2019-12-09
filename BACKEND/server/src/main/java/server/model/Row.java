package server.model;

public class Row {

    int row_id;
    String row_name;
    String row_description;

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getRow_name() {
        return row_name;
    }

    public void setRow_name(String row_name) {
        this.row_name = row_name;
    }

    public String getRow_description() {
        return row_description;
    }

    public void setRow_description(String row_description) {
        this.row_description = row_description;
    }
}
