package projet_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    String url = "jdbc:mysql://binary-digit.net:3305/bd_auth";
	    String login = "cyrilloicludo";
	    String passwd = "cyrilloicludo";
	    Connection cn =null;
        Statement st =null;
        Integer num;
        String test;
	    try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, passwd);
			st = cn.createStatement();
			String sql = "SELECT * FROM authentification;";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
              num = rs.getInt("numero_fiche");
              System.out.println(num);
              test = rs.getString("email");
              System.out.println(test);
              test = rs.getString("tel");
              System.out.println(test);
            }
	    } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
                }
    }

}