package server.model;

public class Table {
    String columnName;
    int order;
    String rowName;
    String Description;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
