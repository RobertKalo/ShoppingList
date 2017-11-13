package hu.kalo.shoppinglist;

import java.sql.*;

public class User {

	private int		id;
	private String	email;
	private String	password;

	private void load( int id ) {

		try {
			PreparedStatement statement = new DB().getConnection()
					.prepareStatement( "SELECT * FROM `user` WHERE id=?" );
			statement.setInt( 1, id );
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				this.id = id;
				this.email = resultSet.getString( "email" );
				this.password = resultSet.getString( "password" );
			} else {
				new Hiba( "Hib�s felhaszn�l�n�v vagy jelsz�" );
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void login() {

		try {
			PreparedStatement statement = new DB().getConnection()
					.prepareStatement( "SELECT id FROM `user` WHERE `email`=? AND `password`=?" );
			statement.setString( 1, this.email );
			statement.setString( 2, this.password );
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				load( resultSet.getInt( "id" ) );
				this.logged_in = true;
			} else {
				new Hiba( "M�r nincs felhaszn�l� ezzel az e-mail c�mmel" );
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void logout() {
		this.logged_in = false;
	}

	public void registration() {

		try {
			PreparedStatement statement = new DB().getConnection()
					.prepareStatement( "SELECT id FROM `user` WHERE `email`=?" );
			statement.setString( 1, this.email );
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				new Hiba( "Ezzel az e-mail c�mmel m�r regisztr�ltak" );

			} else if (validation()) {

				statement = new DB().getConnection()
						.prepareStatement( "INSERT INTO `user`(`email`, `password`, `id`) VALUES (?, ?, ?)" );
				statement.setString( 1, this.email );
				statement.setString( 2, this.password );
				statement.setInt( 3, this.id );

				statement.executeUpdate();

				statement = new DB().getConnection().prepareStatement( "SELECT id FROM `user` WHERE `email`=?" );
				statement.setString( 1, this.email );
				resultSet = statement.executeQuery();

				if (resultSet.next()) {
					this.id = resultSet.getInt( "id" );
					this.logged_in = true;
				} else {
					new Hiba( "A kapcsolat megszakadt. Jelentkezz be �jra" );
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private boolean validation() {

		if (!this.email.contains( "@" ) || !this.email.contains( "." )) {
			new Hiba( "A megadott e-mail c�m �rv�nytelen" );
			return false;
		}

		if (this.password.length() < 8) {
			new Hiba( "A jelsz� legal�bb 8 karakter legyen" );
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", logged_in=" + logged_in + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public boolean isLogged_in() {
		return logged_in;
	}

	public void setLogged_in( boolean logged_in ) {
		this.logged_in = logged_in;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

	private boolean logged_in;

	public User() {
	}

	public User(int id) {
		this.id = id;

		load( id );
	}

}
