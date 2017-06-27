package se.cbb.jprime.seqevo;

/**
 * Substitution model definition.
 * @author Gustaf Pihl.
 */
public class Mtrev24 {

	/**
	 * Returns the model type described by (Adachi and Hasegawa, 1996).
	 * @param cacheSize matrix cache size. Probably not useful with more than twice the number
	 * of arcs in tree...?
	 * @return the model type.
	 */
	public static SubstitutionMatrixHandler createMtrev24(int cacheSize) {
		double[] Pi = {
		     0.072, 0.019, 0.039, 0.019, 0.006, 0.025, 0.024, 0.056, 0.028, 0.088, 0.169, 0.023, 0.054, 0.061, 0.054, 0.072, 0.086, 0.029, 0.033, 0.043
		};
		
		double[] R = {
		     23.18, 26.95, 17.67, 59.93, 1.90, 9.77, 120.71, 13.90, 96.49, 25.46, 8.36, 141.88, 6.37, 54.31, 387.86, 480.72, 1.90, 6.48, 195.06, 
		     13.24, 1.90, 103.33, 220.99, 1.90, 23.03, 165.23, 1.90, 15.58, 141.40, 1.90, 4.69, 23.64, 6.04, 2.08, 21.95, 1.90, 7.64, 
		     794.38, 58.94, 173.56, 63.05, 53.30, 496.13, 27.10, 15.16, 608.70, 65.41, 15.20, 73.31, 494.39, 238.46, 10.68, 191.36, 1.90, 
		     1.90, 55.28, 583.55, 56.77, 113.99, 4.34, 1.90, 2.31, 1.90, 4.98, 13.43, 69.02, 28.01, 19.86, 21.21, 1.90, 
		     75.24, 1.90, 30.71, 141.49, 62.73, 25.65, 1.90, 6.18, 70.80, 31.26, 277.05, 179.97, 33.60, 254.77, 1.90, 
		     313.56, 6.75, 582.40, 8.34, 39.70, 465.58, 47.37, 19.11, 137.29, 54.11, 94.93, 1.90, 38.82, 19.00, 
		     28.28, 49.12, 3.31, 1.90, 313.86, 1.90, 2.67, 12.83, 54.71, 14.82, 1.90, 13.12, 21.14, 
		     1.90, 5.98, 2.41, 22.73, 1.90, 1.90, 1.90, 125.93, 11.17, 10.92, 3.21, 2.53, 
		     12.26, 11.49, 127.67, 11.97, 48.16, 60.97, 77.46, 44.78, 7.08, 670.14, 1.90, 
		     329.09, 19.57, 517.98, 84.67, 20.63, 47.70, 368.43, 1.90, 25.01, 1222.94, 
		     14.88, 537.53, 216.06, 40.10, 73.61, 126.40, 32.44, 44.15, 91.67, 
		     91.37, 6.44, 50.10, 105.79, 136.33, 24.00, 51.17, 1.90, 
		     90.82, 18.84, 111.16, 528.17, 21.71, 39.96, 387.54, 
		     17.31, 64.29, 33.85, 7.84, 465.58, 6.35, 
		     169.90, 128.22, 4.21, 16.21, 8.23, 
		     597.21, 38.58, 64.92, 1.90, 
		     9.99, 38.73, 204.54, 
		     26.25, 5.37, 
		     1.90
		};

		return new SubstitutionMatrixHandler("Mtrev24", SequenceType.AMINO_ACID, R, Pi, cacheSize);
	}

}