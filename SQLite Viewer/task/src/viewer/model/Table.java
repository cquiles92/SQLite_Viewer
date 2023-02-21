package viewer.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Table {
    private final Object[] columns;
    private final Object[][] data;

    public Table(ResultSet resultSet) throws SQLException {
        columns = generateColumns(resultSet.getMetaData());
        data = generateData(resultSet);
    }

    public Object[] getColumns() {
        return columns;
    }

    public Object[][] getData() {
        return data;
    }

    private Object[] generateColumns(ResultSetMetaData metaData) throws SQLException {
        Object[] columns = new Object[metaData.getColumnCount()];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = metaData.getColumnName(i + 1);
        }
        return columns;
    }

    private Object[][] generateData(ResultSet resultSet) throws SQLException {
        ArrayList<Object[]> objects = new ArrayList<>(); //unknown size of rows

        while (resultSet.next()) {
            Object[] row = new Object[columns.length];
            for (int i = 0; i < columns.length; i++) {
                row[i] = resultSet.getObject(i + 1);
            }
            objects.add(row);
        }

        return objects.toArray(new Object[0][]);
    }
}
