package hu.kalo.shoppinglist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Kategoriak {

	private int		id;
	private String	name;

	public Kategoriak() {
	}

	public Kategoriak(int kategoriak_id) {
		load( kategoriak_id );
	}

	private void load( int kategoriak_id ) {

		try {
			Connection connection = new DB().getConnection();
			PreparedStatement statement = connection
					.prepareStatement( "SELECT name FROM `kategotiak` WHERE id=?" );
			statement.setInt( 1, kategoriak_id );
			ResultSet resultSet = statement.executeQuery();
			if ( resultSet.next() ) {
				this.id = kategoriak_id;
				this.name = resultSet.getString( "name" );

			} else {
				new Hiba( "Nem létezik ilyen kategória!" );
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		}

	}

	public void save() {

		try {
			if ( this.name.trim().length() != 0 ) {
				Connection connection = new DB().getConnection();

				if ( this.id == 0 ) {

					// mentjük az adatokat a helyi változóból
					PreparedStatement statement = connection
							.prepareStatement( "INSERT INTO `kategoriak`(`id`, `name`) VALUES (?,?)" );
					statement.setInt( 1, this.id );
					statement.setString( 2, this.name );
					statement.executeUpdate();

					// id-t visszakérjük
					statement = connection
							.prepareStatement( "SELECT id FROM `kategoriak` ORDER BY id DESC LIMIT 1" );
					statement.setInt( 1, this.id );
					ResultSet resultSet = statement.executeQuery();

					this.id = resultSet.getInt( "id" );

				} else {
					// update
					PreparedStatement statement = connection
							.prepareStatement( "UPDATE `kategoriak` SET `name`=? WHERE `id`=?" );
					statement.setString( 1, this.name );
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

}
