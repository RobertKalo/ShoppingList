package hu.kalo.shoppinglist;

import java.sql.*;
import java.util.Vector;

public class Lista {

	private int				id;
	private String			name;
	private int				user_id;

	private Vector<Cikk>	cikkLista;

	Lista() {
		this.id = 0;
	}

	Lista(int lista_id) {
		load( lista_id );
	}

	public void load( int lista_id ) {

		try {
			Connection connection = new DB().getConnection();
			PreparedStatement statement = connection
					.prepareStatement( "SELECT * FROM `lista` WHERE id=?" );
			statement.setInt( 1, lista_id );
			ResultSet resultSet = statement.executeQuery();
			if ( resultSet.next() ) {
				this.id = lista_id;
				this.name = resultSet.getString( "name" );
				this.user_id = resultSet.getInt( "user_id" );

				statement = connection
						.prepareStatement( "SELECT cikkek_id FROM `lista_has_cikkek` WHERE lista_id=?" );
				statement.setInt( 1, lista_id );
				resultSet = statement.executeQuery();

				while ( resultSet.next() ) {
					cikkLista.add( new Cikk( lista_id, resultSet.getInt( "cikkek_id" ) ) );

				}
			} else {
				new Hiba( "Nem létezik ilyen lista!" );
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		}

	}

	public void save() {

		System.out.println( this.name );
		try {
			if ( this.name.trim().length() != 0 ) {
				System.out.println( "Van listanév" );
				Connection connection = new DB().getConnection();
				if ( this.id == 0 ) {

					// mentjük az adatokat a helyi változóból
					PreparedStatement statement = connection
							.prepareStatement( "INSERT INTO `lista`(`name`, `user_id`) VALUES (?,?)" );
					statement.setString( 1, this.name );
					statement.setInt( 2, this.user_id );
					statement.executeUpdate();

					// id-t visszakérjük
					statement = connection
							.prepareStatement( "SELECT id FROM `lista` ORDER BY id DESC LIMIT 1" );
					ResultSet resultSet = statement.executeQuery();
					
					
					if ( resultSet.next() ) {
						this.id=resultSet.getInt( "id" );
					}

				} else {
					// update
					PreparedStatement statement = connection
							.prepareStatement( "UPDATE `lista` SET `name`=?,`user_id`=? WHERE `id`=?" );
					statement.setString( 1, this.name );
					statement.setInt( 2, this.user_id );
					statement.setInt( 3, this.id );
					statement.executeUpdate();

				}

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		}

	}

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id( int user_id ) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Lista [id=" + id + ", name=" + name + ", user_id=" + user_id + ", cikkLista=" + cikkLista + "]";
	}
}
