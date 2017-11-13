package hu.kalo.shoppinglist;

public class Test {

	public static void main( String[] args ) {

		//		User user1 = new User();
		//		user1.setEmail( "robert.kalo@gmail.com" );
		//		user1.setPassword( "akarmi8795" );
		//		user1.registration();
		//		
		//		System.out.println( user1 );

		User user2 = new User();
		user2.setEmail( "robert.kalo@gmail.com" );
		user2.setPassword( "akarmi8795" );
		user2.login();
		System.out.println( user2 );

		Lista lista = new Lista();
		lista.setName( "Tesztlista" );
		lista.save();
		System.out.println( lista );
	}

}
