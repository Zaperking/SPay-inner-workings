package com.samsung.android.spayfw.appinterface;

public interface ISO7816 {
    public static final short APDU_GENERAL_ERROR = (short) 4;
    public static final short COMPUTE_CRYPTO_CHECKSUM = (short) -32726;
    public static final short EXCHANGE_RELAY_RESISTANCE_DATA = (short) -32534;
    public static final short GENERATE_AC = (short) -32594;
    public static final short GET_DATA = (short) -32566;
    public static final short GET_PROCESSING_OPTIONS = (short) -32600;
    public static final short MC_SW_APPLET_SELECT_FAILED = (short) 27033;
    public static final short MC_SW_PIN_BLOCKED = (short) 27267;
    public static final short NFC_IN_DISCOVER_MODE = (short) 5;
    public static final short NFC_TERMINAL_DETACHED = (short) 3;
    public static final short NO_ERROR = (short) 1;
    public static final short READ_RECORD = (short) 178;
    public static final short SELECT = (short) 164;
    public static final short SELECT_PPSE = (short) 165;
    public static final short SW_BYTES_REMAINING_00 = (short) 24832;
    public static final short SW_CLA_NOT_SUPPORTED = (short) 28160;
    public static final short SW_COMMAND_INCOMPATIBLE = (short) -30335;
    public static final short SW_COMMAND_NOT_ALLOWED = (short) 27014;
    public static final short SW_CONDITIONS_NOT_SATISFIED = (short) 27013;
    public static final short SW_CORRECT_LENGTH_00 = (short) 27648;
    public static final short SW_DATA_INVALID = (short) 27012;
    public static final short SW_DATA_NOT_FOUND = (short) 27272;
    public static final short SW_FILE_FULL = (short) 27268;
    public static final short SW_FILE_INVALID = (short) 27011;
    public static final short SW_FILE_NOT_FOUND = (short) 27266;
    public static final short SW_FUNC_NOT_SUPPORTED = (short) 27265;
    public static final short SW_INCORRECT_P1P2 = (short) 27270;
    public static final short SW_INS_NOT_SUPPORTED = (short) 27904;
    public static final short SW_LC_INCONSISTENT_P1P2 = (short) 27271;
    public static final short SW_LC_INCONSISTENT_TLV = (short) 27269;
    public static final short SW_LOGICAL_CHANNEL_NOT_SUPPORTED = (short) 26753;
    public static final short SW_NO_ERROR = (short) -28672;
    public static final short SW_RECORD_NOT_FOUND = (short) 27267;
    public static final short SW_SECURE_MESSAGING_NOT_SUPPORTED = (short) 26754;
    public static final short SW_SECURITY_STATUS_NOT_SATISFIED = (short) 27010;
    public static final short SW_SELECTED_FILE_INVALIDATED = (short) 25219;
    public static final short SW_SM_INCORRECT = (short) 27016;
    public static final short SW_SM_MISSING = (short) 27015;
    public static final short SW_UNKNOWN = (short) 28416;
    public static final short SW_WARNING_STATE_UNCHANGED = (short) 25088;
    public static final short SW_WRONG_DATA = (short) 27264;
    public static final short SW_WRONG_LENGTH = (short) 26368;
    public static final short SW_WRONG_P1P2 = (short) 27392;
    public static final short TRANSACTION_COMPLETE = (short) 2;
    public static final short VISA_SW_AUTHENTICATION_FAILED = (short) 25344;
}
