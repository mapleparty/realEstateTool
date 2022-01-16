/**
 * The price estimator gives out an estimation of the property price
 */

package realEstate;

import java.io.IOException;
import java.util.*;

public class PriceEstimator {

	// Final double zones stated out the distance within of each zone
	private final static double ZONE_1 = 0.5;
	private final static double ZONE_2 = 2;
	private final static double ZONE_3 = 5;

	private static double estimatedPrice; // The estimated price per sqft
	private static double priceSum; // The sum of the price added
	private static int totalWeight; // The total weight
	private static int weight; // Weight of an individual property

	// ArrayList distanceList contains distance from all properties to the point
	private static ArrayList<Double> distanceList = new ArrayList<Double>();

	/**
	 * The method estimatePrice estimates price per sqft of a property by comparing
	 * nearby properties
	 * 
	 * @param postalCode
	 * @return
	 * @throws IOException
	 */
	public static double estimatePrice(String postalCode) throws IOException {

		// The total weight
		totalWeight = 0;

		// The sum of all properties data
		priceSum = 0;

		// Retrieve the distanceList using Distance Calculator
		distanceList = DistanceCalculator.getDistanceList(postalCode);

		// Loop through all properties
		for (int x = 0; x < distanceList.size(); x++) {

			// The weight is initialized
			weight = 0;

			// If the property is within zone 1
			if (distanceList.get(x) <= ZONE_1) {

				if (distanceList.get(x) == -1) {
					return -1;
				}
				// If the property is sold
				if (Initialize.properties.get(x).isSold()) {

					// The weight is 2
					weight = 2;

				} else { // Not sold

					// The weight is 3
					weight = 3;
				}
				// Else if the property is within zone 2
			} else if (distanceList.get(x) <= ZONE_2) {

				// If the property is sold
				if (Initialize.properties.get(x).isSold()) {

					// The weight is 1
					weight = 1;

				} else {// Not sold

					// the weight is 2
					weight = 2;
				}

				// Else if the property is within zone 3
			} else if (distanceList.get(x) <= ZONE_3) {

				// if the property is not sold
				if (!Initialize.properties.get(x).isSold()) {

					// the weight is 1
					weight = 1;
				}

				// Property outside of all zones
			} else {

				// Has no impact on the determination
				weight = 0;
			}

			// Add the weight to the total weight
			totalWeight += weight;

			// Add the weighted price sqft to the price sum
			priceSum += (Initialize.properties.get(x).getPrice() / Initialize.properties.get(x).getSize()) * weight;

		}

		// If no property can be evaluated
		if (totalWeight == 0) {

			// Return -1
			return -1;

		} else {

			// Calculate the estimated price
			estimatedPrice = priceSum / totalWeight;
		}

		// Return the price
		return estimatedPrice;

	}

}
