package ca.qc.inspq.nam.api.modele;

public enum TypeRegex {
    REGEX_NAM_ALBERTA("^[1-9]{1}[0-9]{8}$"),
    REGEX_NAM_COLOMBIE_BRITANNIQUE("^9[0-9]{9}$"),
    REGEX_NAM_MANITOBA("^[0-9]{9}$"),
    REGEX_NAM_TERRITOIRES_NO("^[0-9]{7}$"),
    REGEX_NAM_NOUVELLE_ECOSSE("^[0-9]{10}$"),
    REGEX_NAM_NOUVEAU_BRUNSWICK("^[0-9]{9}$"),
    REGEX_NAM_TERRE_NEUVE_LABRADOR("^[0-9]{12}$"),
    REGEX_NAM_NUNAVUT("^1[0-9]{7}[2-7]{1}$"),
    REGEX_NAM_ONTARIO("^[0-9]{10}$"),
    REGEX_NAM_QUEBEC("^[A-Za-z]{4}[0-9]{6}[A-H-J-N-P-Z-1-9][0-9]$"),
    REGEX_NAM_ILE_PRINCE_EDOUARD("^[0-9]{8,9}$"),
    REGEX_NAM_SASKATCHEWAN("^[0-9]{9}$"),
    REGEX_NAM_YUKON("^[0-9]{9}$");

    public final String value;

    private TypeRegex(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}