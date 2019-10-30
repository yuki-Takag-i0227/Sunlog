package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.ShopBeans;

public class ShopDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rsrt = null;

	String url = "jdbc:postgresql://localhost:5432/sunlog";
	String user = "postgres";
	String pass = "root";
	String sql = null;

	public ShopBeans findByShop() {
		ShopBeans shop = null;

		//データベース接続
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url,user,pass);

			//SQL文を準備
			sql = "SELECT * FROM SHOP";
			pstmt = conn.prepareStatement(sql);


			//SELECT文を実行し結果を取得
			rsrt = pstmt.executeQuery();

			//一致したユーザーが存在した場合
			//そのユーザーを表すAccountインスタンスを生成
			if(rsrt.next()) {
				//結果表からデータを取得
				int userID = rsrt.getInt("USER_ID");
				String userName = rsrt.getString("NAME");
				String userPass = rsrt.getString("PASS");
				String userMailAdd = rsrt.getString("MAIL");
				int userAge = rsrt.getInt("AGE");
				shop = new ShopBeans(userID, userName, userPass, userAge, userMailAdd);
				conn.close();
				pstmt.close();
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//見つかったユーザーまたはnullを返す
		return shop ;
	}

	public List<Account> findAll(){
		List<Account> accList = new ArrayList<Account>();

		//データベース接続
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url,user,pass);

			//SELECT文を準備
			sql = "SELECT * FROM ACCOUNT";
			pstmt = conn.prepareStatement(sql);

			//SELECTを実行し結果を取得
			rsrt = pstmt.executeQuery();

			//結果表に格納されたレコードの内容を
			//Accountインスタンスに設定し、ArrayListインスタンスに追加
			while(rsrt.next()) {
				int id = rsrt.getInt("USER_ID");
				String pass = rsrt.getString("PASS");
				String mail = rsrt.getString("MAIL");
				String name = rsrt.getString("NAME");
				int age = rsrt.getInt("AGE");
				int fav = rsrt.getInt("FAVORITE");
				Account accunt = new Account(id, pass, mail, age, name, fav);
				accList.add(accunt);
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return accList;
	}

	//Accountテーブルにユーザー登録
	public boolean create(Account acc) {
		//データベース接続
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url,user,pass);

			//INSERT文の準備
			sql = "INSERT INTO ACCOUNT VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			//INSERT文中の「？」に使用する値を設定する
			pstmt.setInt(1, acc.getUserID());
			pstmt.setString(2, acc.getUserPass());
			pstmt.setString(3, acc.getUserMailAdd());
			pstmt.setString(4, acc.getUserName());
			pstmt.setInt(5, acc.getUserAge());
			pstmt.setInt(6, acc.getFavorite());

			//INSERT文を実行
			int result = pstmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}