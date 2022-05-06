package com.example.introducingMybatis.bindingWithList.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class SqlMapper {
	// database setting property path
	private String configurationResource = "com/example/introducingMybatis/bindingWithList/resources/config-jdbc.properties";
	private Properties configuration = new Properties();

	// query setting property path
	private String sqlResource = "com/example/introducingMybatis/bindingWithList/resources/sql.properties";
	private Properties sql = new Properties();

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	// default constructor
	public SqlMapper() {
		try {
			configurationAsProperties();

			sqlAsProperties();

			Class.forName(configuration.getProperty("driver"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Connection connect() throws SQLException {
		try {
			if (connection == null) {
				connection = DriverManager.getConnection(configuration.getProperty("url"),
						configuration.getProperty("username"), configuration.getProperty("password"));
			}

			return connection;
		} catch (SQLException e) {
			throw e;
		}
	}

	protected void release() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
			}
		}

		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}

	private void configurationAsProperties() throws IOException {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		InputStream inputStream = contextClassLoader.getResourceAsStream(configurationResource);

		try {
			configuration.load(inputStream);
		} catch (IOException e) {
			throw e;
		} finally {
			inputStream.close();
		}
	}

	private void sqlAsProperties() throws IOException {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		InputStream inputStream = contextClassLoader.getResourceAsStream(sqlResource);

		try {
			sql.load(inputStream);
		} catch (IOException e) {
			throw e;
		} finally {
			inputStream.close();
		}
	}

	protected String sqlById(String sqlId) {
		return sql.getProperty(sqlId);
	}

	protected String parameterBindingByList(String sqlId, List<Object> parameters) throws Exception {
		try {
			// 쿼리문 반환
			String query = sqlById(sqlId);

			String[] querySplit = query.split("[?]");

			String value = "";
			StringBuilder replaceSql = new StringBuilder("");

			if (querySplit.length > 1) {
				for (int i = 0; i < parameters.size(); i++) {
					replaceSql.append(querySplit[i]);

					value = (parameters.get(i) == null) ? "" : parameters.get(i).toString();

					boolean isNumber = Pattern.matches("[0-9]+", value);
					if (isNumber) {
						replaceSql.append(value);
					} else {
						replaceSql.append("'").append(value).append("'");
					}

					if (!querySplit[0].contains("SELECT") && i == (parameters.size() - 1)) {
						replaceSql.append(")");
					}
				}

			} else if (querySplit.length == 1) {
				replaceSql.append(querySplit[0]);

				value = (parameters.get(0) == null) ? "" : parameters.get(0).toString();

				boolean isNumber = Pattern.matches("[0-9]+", value);
				if (querySplit[0].contains("WHERE")) {
					if (isNumber) {
						replaceSql.append(value);
					} else {
						replaceSql.append("'").append(value).append("'");
					}
				}
			}

			return replaceSql.toString();
		} catch (Exception e) {
			throw e;
		}
	}
}
