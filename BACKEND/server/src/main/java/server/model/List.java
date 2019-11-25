package server.model;

public class List {

    int list_id;
    String list_name;
    java.util.List<server.model.Row> rows;

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public java.util.List<Row> getRows() {
        return rows;
    }

    public void setRows(java.util.List<Row> rows) {
        this.rows = rows;
    }
}
