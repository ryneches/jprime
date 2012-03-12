package se.cbb.jprime.seqevo;

import se.cbb.jprime.io.SampleDoubleArray;

/**
 * Creates matrix handlers corresponding to various substitution models.
 * TODO: the models JC69, etc. should be moved to another subclass
 * of TransitionHandler that implements analytical solutions of the
 * member functions.
 * <p/>
 * Note: Not a proper factory class yet, but maybe eventually.
 * 
 * @author Bengt Sennblad.
 * @author Lars Arvestad.
 * @author Joel Sjöstrand.
 */
public class SubstitutionMatrixHandlerFactory {

	/**
	 * Convenience method for creating a known substitution model from
	 * its string identifier.
	 * @param model the identifier.
	 * @param cacheSize matrix cache size. Probably not useful with more than twice the number
	 * of arcs in tree...?
	 */
	public static SubstitutionMatrixHandler create(String model, int cacheSize) {
		model = model.trim().toUpperCase();
		if (model == "JC69") {
			return createJC69(cacheSize);
		} else if (model == "UNIFORMAA") {
			return createUniformAA(cacheSize);
		} else if (model == "JTT") {
			return createJTT(cacheSize);
		} else if (model == "UNIFORMCODON") {
			return createUniformCodon(cacheSize);
		} else if (model == "ARVECODON") {
			return createArveCodon(cacheSize);
		} else if (model.startsWith("USERDEFINED")) {
			// TODO: Clean-up.
			// HACK! Assumes string like "USERDEFINED=DNA;[pi1,...,pik];[r1,...,rj]".
			String seqType = model.substring(model.indexOf("=")+1, model.indexOf(";"));
			String pi = model.substring(model.indexOf(";")+1, model.indexOf(";", 22));
			String r = model.substring(model.indexOf(";", 22)+1);
			return createUserDefined(seqType,
					SampleDoubleArray.toDoubleArray(pi), SampleDoubleArray.toDoubleArray(r), cacheSize);
		} else {
			throw new IllegalArgumentException("Cannot create unknown substitution model: " + model);
		}
	};

	/**
	 * Returns the DNA model type described by Jukes & Cantor 1969.
	 * @param cacheSize matrix cache size. Probably not useful with more than twice the number
	 * of arcs in tree...?
	 * @return the model type.
	 */
	public static SubstitutionMatrixHandler createJC69(int cacheSize) {
		double[] Pi = new double[4];
		double[] R = new double[6];
		for (int i = 0; i < 4; i++) {
			Pi[i] = 0.25;
		}
		// for (int i = 0; i < 4 * (4 - 1) / 2; i++)
		for (int i = 0; i < 6; i++) {
			R[i] = 1.0;
		}

		return new SubstitutionMatrixHandler("JC69", SequenceType.DNA, R, Pi, cacheSize);
	}

	/**
	 * Returns the amino acid model type corresponding to that
	 * described by Jukes & Cantor 1969 for DNA.
	 * @param cacheSize matrix cache size. Probably not useful with more than twice the number
	 * of arcs in tree...?
	 * @return the model type.
	 */
	public static SubstitutionMatrixHandler createUniformAA(int cacheSize) {
		double[] Pi = new double[20];
		double[] R = new double[190];
		for (int i = 0; i < 20; i++) {
			Pi[i] = 0.05;
		}
		for (int i = 0; i < 190; i++) {
			R[i] = 1.0;
		}
		return new SubstitutionMatrixHandler("UniformAA", SequenceType.AMINO_ACID, R, Pi, cacheSize);
	}

	/**
	 * Returns the model type described by Jones, Taylor and Thornton.
	 * @param cacheSize matrix cache size. Probably not useful with more than twice the number
	 * of arcs in tree...?
	 * @return the model type.
	 */
	public static SubstitutionMatrixHandler createJTT(int cacheSize) {
		double[] Pi = {
				0.077000, 0.051000, 0.043000, 0.052000, 
				0.020000, 0.041000, 0.062000, 0.074000, 
				0.023000, 0.052000, 0.091000, 0.059000, 
				0.024000, 0.040000, 0.051000, 0.069000, 
				0.059000, 0.014000, 0.032000, 0.066000
		};
		double[] R = {
				247.00, 216.00, 386.00, 106.00, 208.00, 600.00, 1183.0, 
				46.000, 173.00, 257.00, 200.00, 100.00, 51.000, 901.00, 
				2413.0, 2440.0, 11.000, 41.000, 1766.0, 
				116.00, 48.000, 125.00, 750.00, 119.00, 614.00, 
				446.00, 76.000, 205.00, 2348.0, 61.000, 16.000, 217.00,
				413.00, 230.00, 109.00, 46.000, 69.000, 
				1433.0, 32.000, 159.00, 180.00, 291.00, 
				466.00, 130.00, 63.000, 758.00, 39.000, 15.000, 31.000,
				1738.0, 693.00, 2.0000, 114.00, 55.000, 
				13.000, 130.00, 2914.0, 577.00, 
				144.00, 37.000, 34.000, 102.00, 27.000, 8.0000, 39.000, 
				244.00, 151.00, 5.0000, 89.000, 127.00, 
				9.0000, 8.0000, 98.000, 
				40.000, 19.000, 36.000, 7.0000, 23.000, 66.000, 15.000, 
				353.00, 66.000, 38.000, 164.00, 99.000, 
				1027.0, 84.000, 
				635.00, 20.000, 314.00, 858.00, 52.000, 9.0000, 395.00, 
				182.00, 149.00, 12.000, 40.000, 58.000, 
				610.00, 
				41.000, 43.000, 65.000, 754.00, 30.000, 13.000, 71.000, 
				156.00, 142.00, 12.000, 15.000, 226.00, 
				41.000, 25.000, 56.000, 142.00, 27.000, 18.000, 93.000, 
				1131.0, 164.00, 69.000, 15.000, 276.00, 
				26.000, 134.00, 85.000, 21.000, 50.000, 157.00, 
				138.00, 6.000, 5.0000, 514.00, 22.000, 
				1324.0, 75.000, 704.00, 196.00, 31.000, 
				172.00, 930.00, 12.000, 61.000, 3938.0,
				94.000, 974.00, 1093.0, 578.00, 
				436.00, 172.00, 82.000, 84.000, 1261.0,
				103.00, 7.0000, 77.000, 
				228.00, 398.00, 9.0000, 20.000, 58.000,
				49.000, 23.000, 
				54.000, 343.00, 8.0000, 17.000, 559.00, 
				36.000, 
				309.00, 39.000, 37.000, 850.00, 189.00,  
				1138.0, 412.00, 6.0000, 22.000, 84.000, 
				2258.0, 36.000, 164.00, 219.00, 
				8.0000, 45.000, 526.00, 
				41.000, 27.000,  
				42.000
		};

		return new SubstitutionMatrixHandler("JTT", SequenceType.AMINO_ACID, R, Pi, cacheSize);
	}

	/**
	 * Returns the codon model type corresponding to that
	 * described by Jukes & Cantor 1969 for DNA.
	 * @param cacheSize matrix cache size. Probably not useful with more than twice the number
	 * of arcs in tree...?
	 * @return the model type.
	 */
	public static SubstitutionMatrixHandler createUniformCodon(int cacheSize) {
		double[] Pi = new double[61];
		double[] R = new double[1830];
		for (int i = 0; i < 61; ++i) {
			Pi[i] = 1.0 / 61;
		}
		//   for (int i = 0; i < 4 * (4 - 1) / 2; ++i)
		for (int i = 0; i < 1830; ++i) {
			R[i] = 1.0;
		}
		return new SubstitutionMatrixHandler("UniformCodon", SequenceType.CODON, R, Pi, cacheSize);
	}

	/**
	 * Returns the codon model type corresponding to that
	 * described by Arvestad (unpublished).
	 * @param cacheSize matrix cache size. Probably not useful with more than twice the number
	 * of arcs in tree...?
	 * @return the model type.
	 */
	public static SubstitutionMatrixHandler createArveCodon(int cacheSize) {
		double[] Pi = {
				0.029921, 0.020764, 0.025687, 0.021398, 0.013549,
				0.017641, 0.01023, 0.013274, 0.0107, 0.012081,
				0.0073553, 0.0093028, 0.012984, 0.024209, 0.02265,
				0.027304, 0.016606, 0.011223, 0.018475, 0.011238, 
				0.011833, 0.010097, 0.010897, 0.010633, 0.0048438,
				0.012486, 0.0068343, 0.0091069, 0.0078156, 0.016177,
				0.026229, 0.015601, 0.03326, 0.024097, 0.027832,
				0.029487, 0.017596, 0.0250340,.016777, 0.020227,
				0.017732, 0.022614, 0.01137, 0.01929, 0.011278,
				0.017862, 0.023242, 0.020762, 0.01718, 0.017503,
				0.010649, 0.010953, 0.0079887, 0.012242, 0.0077217,
				0.012898, 0.0070888, 0.016598, 0.021956, 0.016697,
				0.022919
		};
		double[] R = {
				0.00854994, 0.122963, 0.0178157, 0.0130137,
				0.00271482, 0.00426035, 0.00851927, 0.0479118,
				0.00886397, 0.0188614, 0.0132263, 0.00579243,
				0.000840711, 0.00188008, 0.00274494, 0.0327853,
				0.00353128, 0.00508215, 0.00881618, 0.00567886,
				0.00288007, 0.00372793, 0.00613782, 0.0270664,
				0.0128452, 0.0114011, 0.0222772, 0.0026108,
				0.00186393, 0.00106889, 0.0032803, 0.0214788,
				0.00123678, 0.00339617, 0.00786925, 0.0079525,
				0.000718638, 0.00223166, 0.00467974, 0.00501493,
				0.00147735, 0.00272723, 0.00221656, 0.00440515,
				0.000738877, 0.000587498, 0.00428742, 0.0012252,
				0.00527692, 0.00891641, 0.00128847, 0.00154003,
				0.0075859, 0.000913126, 0.00133935, 0.00161742,
				0.00496633, 0.000417829, 0.00162405, 0.00229351,
				0.00963658, 0.174478, 0.00480734, 0.0128393,
				0.00653095, 0.00327462, 0.0054656, 0.0433738,
				0.00699734, 0.00501837, 0.00121412, 0.00253957,
				0.00332949, 0.000470048, 0.00521548, 0.0215958,
				0.0116336, 0.00316306, 0.00234701, 0.00448119,
				0.00356574, 0.00155197, 0.00373814, 0.00581641,
				0.0039176, 0.00175085, 0.00117586, 0.0023864,
				0.00159857, 0.000448511, 0.00318004, 0.0244705,
				0.00564439, 0.00752804, 0.00127968, 0.00427098,
				0.00416509, 0.00147172, 0.00314896, 0.0102328,
				0.00443401, 0.0032603, 0.000613234, 0.00191482,
				0.000570605, 0.00122832, 0.00398949, 0.00239503,
				0.00413314, 0.0115717, 0.00695936, 0.00468415,
				0.00427097, 0.000940527, 0.00126934, 0.000116884,
				0.00224281, 0.000896405, 0.000196966,
				0.00860432, 0.00275706, 0.00751679, 0.00974005,
				0.0044676, 0.0149918, 0.0109242, 0.0569291,
				0.00666702, 0.00123078, 0.00251896, 0.00237987,
				0.00107275, 0.00425986, 0.00573364, 0.0246283,
				0.00433179, 0.00352359, 0.00608434, 0.00521002,
				0.00302636, 0.0161042, 0.0274032, 0.0310123,
				0.0141694, 0.00174386, 0.00181278, 0.00253353,
				0.00208162, 0.00498462, 0.00645016, 0.0186416,
				0.00257251, 0.000374841, 0.0062577, 0.00523582,
				0.00320906, 0.00162182, 0.00368435, 0.00321505,
				0.00132755, 0.000737541, 0.00273662, 0.00291114,
				0.000699915, 0.00339587, 0.00124119, 0.0033836,
				0.00653029, 0.00774351, 0.00305463, 0.000995664,
				0.00122595, 0.000472452, 0.000954726, 0.00139577,
				0.00339364, 0.000563719,
				0.00947168, 0.00189076, 0.00757368, 0.0165904,
				0.00927891, 0.00736448, 0.00813079, 0.0548633,
				0.00278923, 0.000346076, 0.00360341, 0.00299621,
				0.0173722, 0.000789757, 0.00404547, 0.0276244,
				0.00434794, 0.001774, 0.00181666, 0.00581587,
				0.00753644, 4.44433e-06, 0.00207961, 0.00568982,
				0.0026938, 0.000341071, 5.90257e-06, 0.00258749,
				0.010963, 0.00445782, 0.00231546, 0.0306184,
				0.00450959, 0.000984741, 0.00153967, 0.00465134,
				0.00899127, 0.00162194, 0.00564149, 0.00861734,
				0.00254181, 5.57394e-05, 0.00118185, 0.00192972,
				0.00197326, 0.00530137, 0.0113844, 0.000555397,
				0.00803118, 0.0130905, 0.00148245, 0.00122674,
				0.00398858, 0.0031643, 0.000254394, 0.00217425,
				0.00330026,
				0.0948145, 0.199589, 0.250428, 0.0173659,
				0.0178219, 0.00116293, 0.0232354, 0.0187516,
				0.00326356, 0.00783583, 0.00152539, 0.0107171,
				0.00299978, 0.00341303, 0.00337866, 0.0104394,
				0.00414938, 5.0926e-06, 0.00384761, 0.00620699,
				4.44433e-06, 0.000496608, 6.09351e-06, 0.00211149,
				0.00253226, 2.11573e-06, 0.00366449, 0.0073512,
				0.00150439, 0.002192, 0.00514617, 0.0219249,
				2.21672e-06, 0.00110051, 0.00588578, 0.000521791,
				2.45392e-06, 0.0093787, 0.00477298, 0.0208532,
				3.10672e-06, 0.00468603, 0.0122384, 0.00265843,
				0.00290249, 0.0616771, 5.06651e-06, 0.0145151,
				0.0123652, 0.00384001, 0.00155366, 0.0076217,
				0.00655917, 0.00233634, 0.00236603, 0.00232518,
				0.230228, 0.15494, 5.18642e-06, 0.041092,
				0.00179834, 0.00376751, 0.00194024, 0.00865715,
				0.00531009, 0.00271029, 0.00324158, 0.00622216,
				0.00810899, 0.00239124, 0.00260961, 0.00561161,
				0.0100903, 5.2188e-06, 0.00155199, 0.0110477,
				0.008179, 0.00243374, 7.10029e-06, 0.00564752,
				0.00572178, 0.000636204, 0.00294513, 0.00747863,
				0.00599501, 0.0015727, 3.15367e-06, 0.0227001,
				0.00673514, 0.00436663, 0.00157727, 0.00679389,
				0.00177297, 2.87674e-06, 0.00213159, 0.0170601,
				0.00818122, 0.00269533, 0.00316683, 0.00124861,
				5.21105e-06, 0.0423671, 0.0296086, 0.00707204,
				0.00885535, 0.00198704, 0.00253887, 0.00198651,
				0.00323057, 0.000661728, 0.00125172,
				0.144125, 0.00516907, 0.0387073, 0.00741245,
				0.000734707, 0.00546152, 0.00865721, 0.0115164,
				0.000525076, 0.00381279, 0.0040623, 0.0120179,
				0.00531289, 4.68955e-06, 0.00504923, 0.0111222,
				0.00555543, 0.00240783, 0.00896007, 0.0189729,
				6.09351e-06, 0.00838852, 0.00337435, 0.00578634,
				0.00147078, 0.0045267, 0.00642884, 0.00844176,
				0.00345275, 0.00629231, 0.00912124, 0.0311881,
				2.74346e-06, 3.12952e-06, 0.00322293, 0.0172074,
				0.00197062, 0.00851921, 0.00386706, 0.0216284,
				0.000327028, 0.00280732, 0.00237003, 0.0217488,
				0.0160238, 0.0550955, 4.5329e-06, 0.00653855,
				0.00297908, 0.00373481, 3.3433e-06, 0.0021681,
				0.00521301, 0.0030757,
				0.00107541, 4.59333e-06, 0.0120103, 0.0656199,
				0.00224059, 2.29224e-06, 0.00505872, 0.0142953,
				0.00847376, 0.00283181, 0.00239357, 0.00604191,
				0.00523405, 0.00340703, 5.0926e-06, 0.00848207,
				0.00357722, 4.44433e-06, 8.11974e-06, 0.0123381,
				0.0066646, 3.43034e-06, 0.000981226, 0.00485661,
				0.00595683, 0.000986968, 0.00262568, 0.00630063,
				0.0107805, 2.21672e-06, 0.000168849, 0.0183734,
				0.0101592, 0.00299933, 4.88069e-06, 0.00260125,
				0.00438639, 0.011419, 2.38766e-06, 0.0164888,
				0.000850284, 0.00465135, 0.00956061, 0.0114697,
				6.9464e-06, 0.0572911, 0.00259402, 0.00133137,
				0.00931178, 0.00458421, 0.000903818, 0.00256923,
				0.00402821,
				4.59333e-06, 0.368258, 5.96519e-06, 0.00753525,
				2.29224e-06, 0.00349141, 0.00223322, 0.0157101,
				0.00358986, 0.00215141, 0.00635509, 0.00646132,
				5.49608e-06, 5.0926e-06, 0.00317306, 0.256947,
				0.0164898, 0.0449536, 0.176012, 0.00676847,
				3.43034e-06, 2.11573e-06, 0.00186116, 0.00687452,
				0.0010316, 0.00182539, 0.00479445, 0.0059234,
				2.21672e-06, 3.30771e-06, 0.00491008, 0.00731998,
				2.45392e-06, 0.00349226, 0.00186811, 0.00166744,
				0.00224042, 0.000104598, 0.00423584, 0.000491396,
				0.00568154, 0.0121378, 0.0114492, 6.9464e-06,
				0.0135421, 7.18661e-06, 0.00159765, 0.00501913,
				0.00470378, 2.52743e-06, 0.00511092, 0.0031502,
				7.54465e-06, 0.298131, 0.0031096, 0.000684644,
				0.00291345, 0.000943026, 0.00529868, 0.0102012,
				0.0107138, 0.004391, 0.00546055, 0.00376978,
				0.00549004, 0.00397325, 0.00409807, 0.0293401,
				0.0177765, 0.0118927, 0.00272929, 0.00029422,
				2.11573e-06, 0.00376317, 0.00528165, 0.0136215,
				0.00908916, 0.00739268, 0.00577779, 0.0160715,
				0.00973092, 0.00749326, 0.00937715, 0.0201472,
				0.0105161, 0.00309618, 0.00462901, 0.000698053,
				0.00150377, 0.00452619, 0.00285363, 0.00274373,
				0.0373434, 0.0901077, 0.0960484, 0.0282931,
				0.01716, 0.00171233, 0.00520762, 0.00428045,
				2.52743e-06, 0.00298831, 0.00349542,
				5.96519e-06, 0.0064128, 0.00134699, 0.00616133,
				2.03244e-06, 0.00125001, 0.00528668, 0.0167856,
				0.00691011, 0.0016397, 0.00695878, 5.0926e-06,
				0.0053739, 0.139728, 0.0583093, 0.193462,
				0.0348289, 0.00271841, 0.00247955, 2.11573e-06,
				0.00446443, 0.00327929, 0.00340976, 0.0142728,
				0.00473413, 0.00312433, 0.00138069, 0.00337536,
				0.00430443, 0.00611676, 2.45392e-06, 0.0102586,
				0.0014253, 0.00645208, 3.10672e-06, 0.0022375,
				0.00444939, 0.00538237, 0.0029964, 0.0145804,
				0.00607375, 0.0151551, 0.00589809, 0.00253054,
				0.00373989, 0.000602627, 0.004456, 2.52743e-06,
				0.00130837, 0.00232375,
				0.00628311, 2.29224e-06, 0.00375174, 0.00480384,
				0.0144646, 0.000358576, 0.00165042, 0.0120016,
				0.00674749, 2.13915e-05, 5.0926e-06, 0.0109248,
				0.0103255, 0.0132117, 0.00659592, 0.0209826,
				0.00356695, 3.43034e-06, 2.11573e-06, 0.00107129,
				0.0111072, 0.00248326, 0.00211844, 0.0153221,
				0.0187, 2.21672e-06, 0.00526061, 0.0160559,
				0.0139401, 2.45392e-06, 0.00950872, 0.0256527,
				0.00411975, 0.00166743, 0.00139364, 0.00516873,
				3.23012e-06, 0.00597444, 0.0840658, 0.0224028,
				0.0228417, 0.100523, 0.000353241, 0.00148249,
				0.0248564, 0.00993072, 2.52743e-06, 0.00274221,
				0.00506887,
				0.0648886, 0.0210983, 0.132402, 0.00444703,
				0.000434321, 0.000154837, 0.00275264, 0.000994323,
				0.00280844, 5.0926e-06, 0.0035828, 1.14565e-05,
				4.44433e-06, 8.11974e-06, 3.62199e-05, 0.0482902,
				0.0156415, 0.00162836, 0.0211527, 0.00261145,
				0.000250296, 0.0020639, 0.000779185, 0.0078059,
				0.00232057, 0.00370149, 0.00107403, 0.00276491,
				0.000859431, 0.00180507, 2.87674e-06, 0.0917294,
				0.00391427, 0.0192578, 0.0382627, 0.00247287,
				0.00777289, 0.00349787, 5.06651e-06, 6.9464e-06,
				0.0031236, 0.00135663, 0.00317824, 0.00662934,
				0.0385934, 0.00575791, 0.0112752, 0.00866898,
				0.0142425, 0.132786, 3.34174e-06, 0.00223824,
				0.0021289, 4.93782e-06, 4.68955e-06, 0.000521934,
				0.0028604, 0.00172123, 0.0019013, 0.00362065,
				0.00304447, 0.00157229, 0.00454399, 0.0324479,
				0.0262843, 0.00564159, 0.000757907, 0.000356158,
				0.00110547, 0.000352906, 0.00216838, 0.00395656,
				0.00310469, 0.00206001, 0.00114266, 0.00117323,
				4.88069e-06, 0.0011165, 0.00246143, 0.0724517,
				0.0353353, 0.00873025, 0.00499768, 0.00219771,
				0.000408165, 0.00278152, 6.9464e-06, 0.00314226,
				0.00531177, 0.00287512, 0.000761747, 0.00177756,
				0.00931261, 0.00899298, 0.00311088,
				0.0133702, 0.00557043, 0.0027418, 0.00657333,
				0.0030451, 0.00153863, 0.00112906, 0.00207036,
				0.00120056, 0.00506115, 0.00408099, 0.00340585,
				0.00288957, 0.0209425, 0.0198397, 0.0246785,
				0.0201495, 0.00314543, 2.30293e-06, 0.00375646,
				0.000182366, 0.00415885, 0.00391343, 0.00500111,
				0.00428553, 0.00165455, 0.00158905, 0.00165662,
				0.00115765, 0.0082001, 0.00700336, 0.0124213,
				0.00736095, 0.00398301, 0.0040573, 0.00368615,
				0.00317206, 0.00394811, 0.00310857, 0.00383357,
				0.00419079, 0.00468919, 0.0192812, 0.00767155,
				0.0276385, 0.00852614,
				0.00271974, 6.78272e-06, 0.00093514, 0.00223728,
				0.00289396, 0.00164983, 0.000484803, 0.000620505,
				0.00204538, 6.60329e-05, 0.000446412, 0.00147832,
				0.0155652, 0.00323559, 0.00583735, 0.0322506,
				0.00178222, 0.00028941, 0.00050173, 0.0007932,
				0.00288332, 0.000947598, 0.00301512, 0.00432567,
				0.000283122, 0.000556178, 0.00283651, 0.000898205,
				0.0397206, 0.00823958, 0.0174465, 0.0593073,
				0.00290156, 0.00514278, 0.00180437, 6.2915e-05,
				0.00355951, 4.5329e-06, 0.00171778, 0.00297939,
				0.00538379, 0.0255752, 0.003272, 0.0199938,
				0.0113789,
				0.00733346, 0.174786, 0.0258343, 0.0098399,
				0.00260667, 0.000982358, 0.00618681, 0.0368034,
				0.00328099, 0.0011543, 0.0148426, 0.00913542,
				0.000669804, 0.00162417, 0.00352018, 0.0287572,
				0.00120079, 0.0102366, 0.00884749, 0.00809388,
				0.00171785, 0.00385861, 0.00661485, 0.00369083,
				0.000766466, 0.00125663, 0.00426344, 0.00509965,
				0.000867063, 0.00117244, 0.0030297, 0.00259496,
				0.00387331, 0.0123301, 0.00255574, 0.00215801,
				0.00945509, 0.00094231, 0.00147559, 0.00276044,
				0.00590419, 0.000414655, 0.00320597, 0.002622,
				0.0237749, 0.345143, 0.000645032, 0.00872707,
				0.00299891, 0.00109559, 0.00553351, 0.0234751,
				0.0133311, 0.00275092, 0.00281232, 0.00556328,
				0.00438449, 0.00190703, 0.00431054, 0.00812005,
				0.00503244, 0.0032216, 0.00262377, 0.00604775,
				0.00491032, 0.002073, 0.000862127, 0.00477014,
				0.00341374, 2.87674e-06, 0.000397954, 0.00241625,
				0.00186493, 2.67283e-06, 0.0237123, 0.003442,
				0.00207807, 0.00689209, 0.00477156, 0.00363315,
				0.00489314, 0.00346404, 0.00156326, 0.000703843,
				0.00624816, 0.00262577, 0.00209393,
				0.00998364, 0.0027953, 0.00564165, 0.00911654,
				0.00247771, 0.00634848, 0.0184823, 0.0405862,
				0.00434524, 0.00119113, 0.00422882, 0.00594981,
				0.00277941, 0.0127273, 0.00846498, 0.0263574,
				0.00229236, 0.00268018, 0.00808855, 0.0097226,
				0.00282755, 0.00199164, 0.0044116, 0.00475081,
				2.87674e-06, 4.92032e-06, 0.00290081, 0.0036802,
				0.00098221, 0.00266258, 0.00237428, 0.00308574,
				0.00858225, 0.011728, 0.0034115, 0.00196543,
				0.00240741, 0.00091327, 0.00129629, 0.00194535,
				0.00254196, 0.00094088,
				0.00494415, 5.49608e-06, 0.00336624, 0.00704169,
				0.0159641, 0.00134835, 0.00933039, 0.0199181,
				0.00309979, 0.00158796, 0.00203036, 0.00600721,
				0.00530016, 0.00376821, 0.00447567, 0.00805016,
				0.00342304, 0.00188881, 0.00366926, 0.00409,
				0.00269499, 0.000272951, 0.0015912, 0.00358658,
				0.00186556, 0.00145265, 0.00119215, 0.00261653,
				0.00495491, 0.0260377, 0.00831284, 0.0015064,
				0.00456484, 0.00580041, 0.00228714, 0.00363507,
				0.00492707, 0.0036755, 0.002288, 0.00354502,
				0.00615802,
				0.146807, 0.171262, 0.33984, 0.00852905,
				4.44433e-06, 8.11974e-06, 0.00323051, 0.00937948,
				0.00200202, 4.68955e-06, 0.00174783, 0.00664885,
				0.00178669, 0.00183969, 0.00493014, 0.0120258,
				0.00209676, 3.30771e-06, 0.00683196, 0.00328206,
				0.000835275, 0.000525028, 0.000367072, 0.0042943,
				0.00159319, 0.00147667, 0.00293994, 0.000226292,
				0.00156709, 0.0246592, 0.00370557, 6.9464e-06,
				0.00229872, 0.00120052, 0.00231443, 0.0011656,
				0.00120082, 0.00134077, 0.00151818, 2.42125e-06,
				0.303702, 0.236341, 8.53069e-05, 0.0110588,
				0.00702832, 0.000366909, 7.10029e-06, 0.0044638,
				0.00303838, 0.000197368, 0.00219393, 0.00869295,
				0.00752936, 0.00136473, 3.15367e-06, 0.0161657,
				0.0068754, 0.00233641, 0.00179453, 0.00281675,
				0.00169049, 0.00211014, 0.000348599, 0.00438086,
				0.00507544, 2.67283e-06, 0.000822758, 0.00104084,
				0.000876581, 0.0256616, 0.0067406, 0.00171418,
				0.00233014, 0.00227738, 0.000787287, 0.00121334,
				0.000619665, 0.00234366, 0.000758125,
				0.134687, 0.00308723, 0.00953194, 0.0147845,
				0.00378533, 0.00112525, 0.00298947, 0.00449585,
				0.00176029, 0.00387019, 0.00767369, 0.00707261,
				0.00252941, 0.00340622, 0.00839213, 0.0217906,
				2.74346e-06, 0.00164068, 0.00565097, 0.00486899,
				2.87674e-06, 0.00123156, 0.0041374, 0.00238615,
				0.00289379, 0.00171984, 3.17051e-06, 0.00454231,
				0.00325396, 0.0259004, 0.00202506, 0.000102664,
				0.00283223, 0.001862, 0.000663555, 0.000140747,
				0.000610459, 0.00120084,
				0.00413072, 4.44433e-06, 0.000659772, 0.00465649,
				0.000662381, 3.43034e-06, 0.00129062, 0.00629449,
				0.00567765, 0.00106899, 0.00267358, 0.00579795,
				0.0069255, 9.26778e-06, 0.00337165, 0.0113428,
				3.12952e-06, 0.000617151, 0.00135549, 0.00332967,
				0.00323845, 0.000538016, 0.000710299, 0.00556316,
				0.00105258, 0.000983319, 0.00335075, 0.000133369,
				6.9464e-06, 0.0279953, 0.000554286, 0.00208874,
				0.00182621, 0.00203637, 0.000297999, 0.0021894,
				0.00121586,
				0.152115, 0.317398, 0.304396, 0.00543655,
				0.00217789, 2.11573e-06, 0.00288051, 0.00638909,
				0.00282566, 1.38421e-05, 0.00404922, 0.00726477,
				0.00244602, 0.000440837, 0.00198916, 0.00583153,
				2.45392e-06, 4.88069e-06, 0.00283043, 0.00849309,
				0.000521417, 0.00153747, 2.67283e-06, 0.00227652,
				0.00362582, 0.00451422, 0.00289658, 0.00799686,
				0.00398953, 0.00669503, 0.00227921, 7.8282e-06,
				0.0044697, 0.00103663, 0.00280573, 0.00180652,
				0.34868, 0.289831, 7.10029e-06, 0.00719444,
				0.00718574, 0.000128247, 0.00310633, 0.00910057,
				0.0063713, 0.000419051, 0.00119558, 0.0126113,
				0.0132575, 2.74346e-06, 3.12952e-06, 0.00760634,
				0.00464084, 0.000472514, 4.92032e-06, 0.00629676,
				0.00278264, 2.67283e-06, 0.00542696, 0.000727522,
				5.21105e-06, 0.00496522, 0.00495601, 4.5329e-06,
				0.00614667, 0.00288691, 7.8282e-06, 3.3433e-06,
				0.00290571, 3.32352e-06, 2.42125e-06,
				0.124429, 7.10029e-06, 0.00345649, 0.00911004,
				0.00183109, 0.0010578, 0.00945171, 0.0135346,
				0.00100247, 3.15367e-06, 0.0112337, 0.0245959,
				2.74346e-06, 3.12952e-06, 0.00704801, 0.0114703,
				2.87674e-06, 4.92032e-06, 0.00474715, 0.00514156,
				0.000247585, 0.00466688, 0.000325449, 5.21105e-06,
				0.00273585, 0.0116437, 4.5329e-06, 0.00133643,
				0.00723492, 0.00255935, 3.3433e-06, 0.00188554,
				0.00151699, 0.000347329,
				0.00296365, 0.000461071, 0.00197042, 0.00306383,
				0.00295264, 0.000597541, 0.00196576, 0.00512849,
				0.00252005, 0.00151038, 0.000901323, 0.00794467,
				0.00221726, 0.00136032, 4.88069e-06, 0.00226327,
				0.00106725, 3.10672e-06, 0.00197761, 0.00323778,
				0.000975473, 0.00554163, 0.00277183, 5.06651e-06,
				0.000281591, 4.5329e-06, 0.00168424, 0.00140321,
				0.0048579, 0.00108346, 0.0010925, 0.00213583,
				0.00102509,
				0.0961551, 0.0883064, 0.172126, 0.0022792,
				3.03998e-05, 0.00091625, 0.00136253, 0.00810238,
				0.00163281, 3.30771e-06, 0.00168389, 0.00401322,
				0.000467391, 4.88069e-06, 7.17976e-06, 0.0293727,
				0.00256812, 0.00448346, 0.00579373, 0.00215783,
				0.00523278, 0.00676607, 5.06651e-06, 6.9464e-06,
				0.00151914, 0.00386541, 0.00367634, 0.00386687,
				0.236588, 0.00741287, 0.124337, 0.0137262,
				0.139925, 0.165729, 0.00102805, 0.00254623,
				0.000612757, 0.000124728, 0.00268568, 0.00708332,
				0.00440564, 0.000925793, 0.00115845, 0.00131234,
				0.00148287, 0.000743112, 4.92032e-06, 0.0291422,
				0.00839046, 0.00338355, 0.00663626, 0.00126324,
				0.00210125, 0.00863056, 6.9464e-06, 0.00115572,
				0.00577933, 0.00401387, 0.00222174, 0.0189182,
				0.0207668, 0.0601963, 0.00393761,
				0.05652, 1.66844e-06, 0.00149538, 0.00221611,
				0.000681421, 0.000362389, 0.00752541, 0.00815344,
				0.000259384, 0.00107643, 0.00113099, 0.002422,
				0.000209155, 4.92032e-06, 0.0126487, 0.0231054,
				0.000238937, 0.00457515, 0.00191107, 0.000306847,
				0.00203937, 0.00637213, 0.00175417, 0.00454855,
				0.00507486, 0.0010786, 0.0247985, 0.0138873,
				0.108636, 0.00431926,
				0.00260218, 0.000739114, 1.99381e-06, 0.0012553,
				0.00435915, 2.21672e-06, 0.00379868, 0.00604403,
				0.000663782, 0.000480034, 4.88069e-06, 0.00194216,
				0.00693849, 0.00409157, 0.00290113, 0.0218953,
				0.0021438, 0.00603883, 0.000988772, 0.000386391,
				0.000367421, 0.00325252, 0.00274675, 0.00322489,
				0.00396829, 0.116166, 0.00569726, 0.110457,
				0.0192919,
				0.0127004, 0.125409, 0.0274497, 0.00830566,
				0.00380797, 0.00453157, 0.00607736, 0.00586186,
				0.00164203, 0.00107544, 0.00218852, 0.00493889,
				0.00102796, 0.000858854, 0.00343245, 0.00100537,
				0.00346004, 0.00853986, 0.00231376, 0.00163067,
				0.00610199, 0.000831366, 0.000793599, 0.000696521,
				0.002247, 0.000513293, 0.00110753, 0.00154071,
				0.0301052, 0.140696, 0.0003341, 0.00987023,
				0.00745933, 9.39777e-05, 0.00195294, 0.0109734,
				0.00399438, 0.000195249, 0.000827009, 0.00170169,
				0.000520062, 0.000129001, 0.00305595, 0.00029927,
				0.00158726, 0.0100601, 0.0110884, 0.00229542,
				0.00126952, 0.000604482, 0.000220891, 0.000549981,
				0.000541689, 0.000279326, 0.000265528,
				0.0137094, 0.00271523, 0.00817378, 0.0112352,
				0.00228548, 0.000803487, 0.00363573, 0.00971949,
				0.00133257, 0.000995622, 0.00262018, 0.00392852,
				0.00118305, 0.00259425, 0.00114199, 0.00300882,
				0.00679522, 0.00869252, 0.00279792, 0.000874779,
				0.00126366, 0.00103857, 3.3433e-06, 0.00110266,
				0.00112018, 0.000965065,
				0.00483748, 0.000197544, 0.00153544, 0.00604653,
				0.00487523, 0.00061898, 0.00364899, 0.00623246,
				0.00117526, 0.000350177, 0.000900496, 0.00198306,
				0.000674788, 0.00348116, 0.0080965, 0.00147679,
				0.00235568, 0.00829654, 0.000223278, 0.000481645,
				0.00132671, 0.00177565, 0.00016269, 0.00162955,
				0.00091823,
				0.075551, 0.135659, 0.196802, 0.0172055,
				0.00117493, 0.00531015, 0.00698007, 0.0226914,
				3.10672e-06, 0.00833167, 0.0116432, 0.0016204,
				0.00257485, 0.0401415, 0.00258056, 0.0189646,
				0.0166837, 0.00657125, 0.00195299, 0.0118529,
				0.00608244, 0.00182215, 0.00251033, 0.00317857,
				0.165077, 0.109935, 0.0022628, 0.0178198,
				0.00703412, 0.00246129, 4.92032e-06, 0.0218977,
				0.00869172, 0.00472389, 0.00343267, 0.000808171,
				0.00356888, 0.0380916, 0.0194151, 0.00697669,
				0.0143892, 0.0024129, 0.00312339, 0.000867294,
				0.00366776, 0.000211404, 0.00127585,
				0.0689972, 0.0024102, 0.0156886, 0.0177722,
				0.000628161, 0.00849392, 0.0111675, 0.0217512,
				2.67283e-06, 0.00177111, 0.00235101, 0.000593174,
				0.0236589, 0.0645283, 4.5329e-06, 0.0115371,
				0.00264595, 0.00339802, 0.000831016, 0.00338894,
				0.00462173, 0.00163956,
				0.00766194, 2.45392e-06, 0.00532826, 0.0167919,
				0.0129989, 0.00504893, 4.96998e-05, 0.0197959,
				0.00122994, 0.00282915, 0.0286581, 0.00592562,
				6.9464e-06, 0.0436504, 0.00449499, 0.000956743,
				0.0145307, 0.0049513, 0.000997309, 0.0050935,
				0.00397919,
				0.0714586, 0.223591, 0.171317, 0.00414659,
				3.10672e-06, 0.000597549, 0.00179983, 4.80492e-05,
				0.00155511, 0.011335, 5.06651e-06, 0.0055983,
				0.00229245, 0.00152198, 0.000738123, 0.00463594,
				3.3433e-06, 0.0011803, 0.000721222, 0.000737282,
				0.16707, 0.139106, 0.000342452, 0.00244157,
				0.00199011, 2.67283e-06, 0.00109571, 3.17051e-06,
				0.00188533, 0.00723016, 0.0143457, 0.00127479,
				0.00470136, 0.00102646, 0.000351659, 0.00168445,
				0.00131595, 0.00041581, 0.000302315,
				0.120301, 0.000473316, 0.00269653, 0.00238315,
				2.83778e-05, 0.000309454, 0.00138385, 5.21105e-06,
				0.010905, 6.9464e-06, 0.0093212, 0.00392181,
				0.0016419, 0.0016209, 2.30453e-05, 0.000959138,
				0.0018917, 0.000775296,
				0.000307043, 0.00144991, 0.000735498, 0.00230461,
				0.000548068, 0.000250589, 0.00378496, 0.00309009,
				0.000677937, 0.00695065, 0.000865514, 0.000373994,
				0.00448187, 0.00155499, 9.07436e-06, 0.000651881,
				0.00106767,
				0.0897912, 0.142536, 0.196062, 0.00177456,
				0.00730734, 0.00324998, 0.00410059, 6.9464e-06,
				0.00473053, 0.00491879, 0.00224197, 0.00824161,
				0.0246401, 0.00148987, 0.0137548, 0.00761396,
				0.15187, 0.126986, 0.00479553, 0.00288506,
				0.00394714, 0.0014028, 0.012614, 4.5329e-06,
				0.0100554, 0.00284395, 0.0038508, 0.00162569,
				0.00827208, 0.00202085, 0.00100974,
				0.0817158, 0.00408089, 0.00235372, 0.00305311,
				0.00143686, 0.00304231, 0.00229436, 0.00759399,
				0.00299396, 0.00369817, 0.000702916, 0.0049965,
				0.0131121, 0.00346241,
				0.00336986, 0.00423752, 5.21105e-06, 0.00182795,
				6.9464e-06, 0.00615866, 0.00364218, 0.00232649,
				0.0121517, 0.014624, 0.0020053, 0.0100886,
				0.00884702,
				0.225611, 0.00293355, 0.00588497, 0.002938,
				0.000136589, 0.0069982, 0.0142077, 0.00216253,
				0.000960203, 0.035891, 0.00332836, 0.0129462,
				0.00334853, 1.68737e-05, 0.00162516, 0.0053386,
				0.00248499, 0.0139777, 0.00807486, 0.00853486,
				0.0126122, 0.00411483, 0.0382064,
				0.162833, 0.202334, 0.251355, 0.00519773,
				0.00166002, 0.0100678, 0.0088729, 0.00216949,
				0.00311358, 0.00296466,
				0.276759, 0.219959, 0.0137666, 0.00141918,
				0.00379156, 3.3433e-06, 0.00639567, 3.32352e-06,
				0.00100625,
				0.141881, 0.00977106, 0.00362538, 0.00439701,
				3.3433e-06, 0.00363905, 0.00751457, 0.0010294,
				0.00430222, 0.00183112, 0.013676, 0.000544521,
				0.00235482, 0.00394066, 0.0047608,
				0.0028945, 0.488159, 0.00173419, 0.00615033,
				0.00291421, 0.0019556,
				0.00340078, 0.00408871, 0.0122046, 0.00636864,
				0.0109969,
				0.00498868, 0.0025861, 0.00568543, 0.0072135,
				0.00400551, 0.186087, 0.0222799,
				0.00909159, 0.149512,
				0.0149748
		};
		return new SubstitutionMatrixHandler("ArveCodon", SequenceType.CODON, R, Pi, cacheSize);
	}

	/**
	 * Returns a user-defined model type.
	 * @param seqType sequence type identifier ("DNA", "AA", "Codon").
	 * @param pi stationary frequencies (alphabet size n).
	 * @param r values of time-reversible rate matrix as if row-major, symmetric and lacking diagonal (size n*(n-1)/2).
	 * @param cacheSize matrix cache size. Probably not useful with more than twice the number
	 * of arcs in tree...?
	 * @return the model type.
	 */
	public static SubstitutionMatrixHandler createUserDefined(String seqType, double[] pi, double[] r, int cacheSize) {
		SequenceType st = SequenceType.getSequenceType(seqType);
		int dim = st.getAlphabetSize();
		int r_dim = dim * (dim - 1) / 2;
		if (pi.length != dim) {
			throw new IllegalArgumentException("Invalid size of stationary frequencies Pi: " + dim);
		} else if (r.length != r_dim) {
			throw new IllegalArgumentException("Invalid size of row-major time-reversible rate matrix R: " + r_dim);
		}
		return new SubstitutionMatrixHandler("USER-DEFINED", st, r, pi, cacheSize);
	}

}