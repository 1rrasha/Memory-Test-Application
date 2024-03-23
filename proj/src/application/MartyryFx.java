package application;

import java.io.Serializable;
import java.util.Date;

public class MartyryFx implements Serializable {

	Date date = new Date();
	String mName;
	String dateOfMartyrdom;

	public MartyryFx(String mName, String dateOfMartyrdom) {
		setDateOfMartyrdom(dateOfMartyrdom);
		setmName(mName);
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getDateOfMartyrdom() {
		return dateOfMartyrdom;
	}

	public void setDateOfMartyrdom(String dateOfMartyrdom) {
		this.dateOfMartyrdom = dateOfMartyrdom;
	}

}