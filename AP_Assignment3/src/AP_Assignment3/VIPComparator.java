package AP_Assignment3;

import java.io.Serializable;
import java.util.Comparator;

public class VIPComparator implements Comparator<Order>, Serializable {
	private static final long serialVersionUID = 5816288676908569930L;

	@Override
    public int compare(Order o1, Order o2) {
        if (o1.isVIP() && !o2.isVIP()) return -1;
        if (!o1.isVIP() && o2.isVIP()) return 1;
        return 0;
    }
}