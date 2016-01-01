package skyline.allPeople;

import java.util.Comparator;

public class PersonOnGroundComparator implements Comparator<PersonOnGround> {

	@Override
	public int compare(PersonOnGround p0, PersonOnGround p1) {
		if (p0.getDepth() < p1.getDepth()) {
			return -1;
		} else if (p0.getDepth() > p1.getDepth()) {
			return 1;
		}
		return p0.hashCode() - p1.hashCode();
	}


}
