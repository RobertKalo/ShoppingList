package hu.kalo.shoppinglist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cikk {

	private int		id;

	private String	name;
	private int		kategoriak_id;
	private String	kategoriak_name;

	private int		lista_user_id;

	private int		unit_id;
	private String	unit_name;

	private int		status;
	private int		amount;

	Cikk(int lista_id, int cikk_id) {
		this.load( lista_id, cikk_id );
	}

	public void load( int lista_id, int cikk_id ) {

		try {
			Connection connection = new DB().getConnection();
			PreparedStatement statement = connection
					.prepareStatement( "SELECT * FROM `cikkek` WHERE id=?" );
			statement.setInt( 1, cikk_id );
			ResultSet resultSet = statement.executeQuery();
			if ( resultSet.next() ) {
				this.id = cikk_id;
				this.name = resultSet.getString( "name" );
				this.kategoriak_id = resultSet.getInt( "kategoriak_id" );
				this.unit_id = resultSet.getInt( "unit_id" );

				statement = connection
						.prepareStatement(
								"SELECT lista_user_id, status, amount FROM `lista_has_cikkek` WHERE lista_id=? AND cikkek_id=?" );
				statement.setInt( 1, lista_id );
				statement.setInt( 2, cikk_id );
				resultSet = statement.executeQuery();

				if ( resultSet.next() ) {
					this.lista_user_id = resultSet.getInt( "lista_user_id" );
					this.status = resultSet.getInt( "status" );
					this.amount = resultSet.getInt( "amount" );
				}

				if ( amount > 0 ) {
					statement = connection
							.prepareStatement(
									"SELECT name FROM `unit` WHERE unit_id=?" );
					statement.setInt( 1, unit_id );
					resultSet = statement.executeQuery();
					if ( resultSet.next() ) {
						this.unit_name = resultSet.getString( "name" );
					}
				}

				statement = connection
						.prepareStatement(
								"SELECT name FROM `kategoriak` WHERE id=?" );
				statement.setInt( 1, kategoriak_id );
				resultSet = statement.executeQuery();
				if ( resultSet.next() ) {
					this.kategoriak_name = resultSet.getString( "name" );
				}

			} else {
				new Hiba( "Nem létezik ilyen árucikk!" );
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
							.prepareStatement(
									"INSERT INTO `cikkek`(`name`, `kategoriak_id`, `unit_id`) VALUES (?,?,?)" );
					statement.setString( 1, this.name );
					statement.setInt( 2, this.kategoriak_id );
					statement.setInt( 3, this.unit_id );
					statement.executeUpdate();

					statement = connection
							.prepareStatement( "INSERT INTO lista_has_cikkek (status, amount) VALUES (?,?)" );
					statement.setInt( 1, status );
					statement.setInt( 2, amount );
					statement.executeUpdate();

					
					// id-t visszakérjük
					statement = connection.prepareStatement(
							"SELECT cikk_id FROM `lista_has_cikkek` WHERE lista_user_id=? ORDER BY cikk_id DESC LIMIT 1" );
					statement.setInt( 1, this.lista_user_id );
					ResultSet resultSet = statement.executeQuery();
					
					this.id = resultSet.getInt( "cikk_id" );
					

				} else {
					// update
					PreparedStatement statement = connection
							.prepareStatement(
									"UPDATE `cikkek` SET `name`=?,`kategoriak_id`=?, `unit_id`=? WHERE `id`=?" );
					statement.setString( 1, this.name );
					statement.setInt( 2, this.kategoriak_id );
					statement.setInt( 3, this.unit_id );
					statement.executeUpdate();
				}

				new Unit(unit_id).save();
				new Kategoriak(kategoriak_id);
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

	public int getKategoriak_id() {
		return kategoriak_id;
	}

	public void setKategoriak_id( int kategoriak_id ) {
		this.kategoriak_id = kategoriak_id;
	}

	public String getKategoriak_name() {
		return kategoriak_name;
	}

	public void setKategoriak_name( String kategoriak_name ) {
		this.kategoriak_name = kategoriak_name;
	}

	public int getUnit_id() {
		return unit_id;
	}

	public void setUnit_id( int unit_id ) {
		this.unit_id = unit_id;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name( String unit_name ) {
		this.unit_name = unit_name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus( int status ) {
		this.status = status;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount( int amount ) {
		this.amount = amount;
	}

	public int getLista_user_id() {
		return lista_user_id;
	}

}
