package AP_Assignment3;


public class Main {
	LoginScreenUI LoginScreenUI;
    AdminScreenUI AdminScreenUI;
	CustomerScreenUI CustomerScreenUI;	
	
	Main(){
		LoginScreenUI = new LoginScreenUI();
		Customer toLogin = LoginScreenUI.getLogin();

		if (LoginScreenUI.getAdmin()) {
		    // Admin login successful
			AdminScreenUI = new AdminScreenUI();
		} else if (toLogin != null) {
		    // Customer login successful
			CustomerScreenUI = new CustomerScreenUI(toLogin);
		} else {
		    // Handle case if both admin and customer login failed (optional)
		    System.out.println("Login failed.");
		}


	}

	public static void main(String[] args){
		new Main();
	}
}
