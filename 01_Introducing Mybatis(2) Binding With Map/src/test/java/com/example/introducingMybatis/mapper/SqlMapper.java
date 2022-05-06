package com.example.introducingMybatis.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class SqlMapper {
	// database property file path
	private String configurationResource = "com/example/introducingMybatis/resources/config-jdbc.properties";
	private Properties configuration = new Properties();

	// query property file path
	private String sqlResource = "com/example/introducingMybatis/resources/sql-inline.properties";
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

	protected String parameterBindingByMap(String sqlId, Map<String, Object> parameters) throws Exception {
		try {
			String query = sqlById(sqlId);

			if (parameters.size() > 0) {
				String key = "";
				String value = "";

				Iterator<String> iteratorKey = parameters.keySet().iterator();
				while (iteratorKey.hasNext()) {
					key = iteratorKey.next();
					value = (parameters.get(key) == null) ? "" : parameters.get(key).toString();

					boolean isNumber = Pattern.matches("[0-9]+", value);
					if (isNumber) {
						query = query.replaceAll("#\\{" + key + "\\}", value);
					} else {
						query = query.replaceAll("#\\{" + key + "\\}", "'" + value + "'");
					}
				}
			}

			return query;
		} catch (Exception e) {
			throw e;
		}
	}

	protected static <T> T resultByType(ResultSet resultSet, String type) throws Exception {
		try {
			List<Object> fieldNames = new ArrayList<Object>();
			List<Object> fieldTypes = new ArrayList<Object>();
			List<Object> filedValues = new ArrayList<Object>();

			if (resultSet.next()) {
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					String column = resultSet.getMetaData().getColumnName(i);

					String[] columnSplit = column.split("[_]");
					StringBuilder camelColumn = new StringBuilder();
					for (int j = 0; j < columnSplit.length; j++) {
						if (j == 0) {
							camelColumn.append(columnSplit[j].toLowerCase());
						} else {
							camelColumn.append(columnSplit[j].toUpperCase().charAt(0));
							camelColumn.append(columnSplit[j].substring(1, columnSplit[j].length()).toLowerCase());
						}
					}

					fieldNames.add(camelColumn);

					filedValues.add(resultSet.getString(column));

					switch (resultSet.getMetaData().getColumnType(i)) {
					case 4:
						fieldTypes.add(int.class);
						break;
					case 12:
						fieldTypes.add(String.class);
						break;
					default:
						fieldTypes.add(String.class);
						break;
					}
				}
			}

			// reflection
			Class objectType = Class.forName(type);
			Class[] filedType = new Class[fieldNames.size()];
			for (int i = 0; i < fieldNames.size(); i++) {
				filedType[i] = (Class) fieldTypes.get(i);
			}

			Constructor constructor = objectType.getConstructor(filedType);
			Object listConstructor[] = new Object[fieldNames.size()];
			for (int i = 0; i < fieldNames.size(); i++) {
				if (filedType[i] == int.class) {
					listConstructor[i] = Integer.parseInt((String) filedValues.get(i));
				} else {
					listConstructor[i] = (String) filedValues.get(i);
				}
			}

			return (T) constructor.newInstance(listConstructor);
		} catch (Exception e) {
			throw e;
		}
	}

	public <T> T selectOne(String sqlId, Map<String, Object> parameters, String type) throws Exception {
		try {
			String sql = parameterBindingByMap(sqlId, parameters);

			preparedStatement = connect().prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

			return resultByType(resultSet, type);
		} catch (Exception e) {
			throw e;
		} finally {
			release();
		}
	}
}
