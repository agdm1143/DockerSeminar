package dbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ConnectDB {

	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/testdb";
		if (args.length > 0) {
			url = "jdbc:mysql://localhost:3306/testdb";
		}
		String user = "test";
		String password = "test";

		try (Connection conn = DriverManager.getConnection(url, user, password)) {

			if (conn != null) {
				String sql = "select * from member";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet result = ps.executeQuery();
				ResultSetMetaData metaData = result.getMetaData();
				List<String> columnNameList = new ArrayList<>();

				IntStream.range(1, metaData.getColumnCount() + 1).forEach(i -> {
					try {
						System.out.print(metaData.getColumnName(i) + "\t");
						columnNameList.add(metaData.getColumnName(i));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});

				while (result.next()) {
					System.out.println();
					columnNameList.stream().forEach(column -> {
						try {
							System.out.print(result.getString(column) + "\t");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
