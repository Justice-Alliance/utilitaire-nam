package ca.qc.inspq.nam.api.domaine.modele;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CaractereValidateur {
	
	private static final String ENCODAGE_EBCDIC = "Cp1047";	
	private static final Map<Integer, Integer> FACTEURS_MULTIPLICATEURS = Map.ofEntries(
			new AbstractMap.SimpleEntry<>(0, 1),
			new AbstractMap.SimpleEntry<>(1, 3),
			new AbstractMap.SimpleEntry<>(2, 7),
			new AbstractMap.SimpleEntry<>(3, 9),
			new AbstractMap.SimpleEntry<>(4, 1),
			new AbstractMap.SimpleEntry<>(5, 7),
			new AbstractMap.SimpleEntry<>(6, 1),
			new AbstractMap.SimpleEntry<>(7, 3),
			new AbstractMap.SimpleEntry<>(8, 4),
			new AbstractMap.SimpleEntry<>(9, 5),
			new AbstractMap.SimpleEntry<>(10, 7),
			new AbstractMap.SimpleEntry<>(11, 6),
			new AbstractMap.SimpleEntry<>(12, 9),
			new AbstractMap.SimpleEntry<>(13, 1));
	
	private final String nam;
	
	public int getValeur() throws UnsupportedEncodingException {
		byte[] namConvertiEnDecimal = nam.getBytes(ENCODAGE_EBCDIC);
		
		int somme = 0;
        for (int positionDuCaractere = 0; positionDuCaractere < 14; positionDuCaractere++) {
        	somme += (namConvertiEnDecimal[positionDuCaractere] & 0xff) * FACTEURS_MULTIPLICATEURS.get(positionDuCaractere);
        }
        
        return somme % 10;
	}
}
