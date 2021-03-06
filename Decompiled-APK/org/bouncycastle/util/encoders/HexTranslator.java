package org.bouncycastle.util.encoders;

import com.americanexpress.mobilepayments.hceclient.context.ApplicationInfoManager;
import com.mastercard.mobile_api.utils.apdu.emv.PutTemplateApdu;

public class HexTranslator implements Translator {
    private static final byte[] hexTable;

    static {
        hexTable = new byte[]{(byte) 48, (byte) 49, (byte) 50, ApplicationInfoManager.TERM_XP3, (byte) 52, ApplicationInfoManager.MOB_CVM_TYP_DEV_PATTERN, (byte) 54, (byte) 55, (byte) 56, ApplicationInfoManager.EMV_MS, PutTemplateApdu.DIRECTORY_TEMPLATE_TAG, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102};
    }

    public int decode(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4 = i2 / 2;
        for (int i5 = 0; i5 < i4; i5++) {
            byte b = bArr[(i5 * 2) + i];
            byte b2 = bArr[((i5 * 2) + i) + 1];
            if (b < PutTemplateApdu.DIRECTORY_TEMPLATE_TAG) {
                bArr2[i3] = (byte) ((b - 48) << 4);
            } else {
                bArr2[i3] = (byte) (((b - 97) + 10) << 4);
            }
            if (b2 < PutTemplateApdu.DIRECTORY_TEMPLATE_TAG) {
                bArr2[i3] = (byte) (bArr2[i3] + ((byte) (b2 - 48)));
            } else {
                bArr2[i3] = (byte) (bArr2[i3] + ((byte) ((b2 - 97) + 10)));
            }
            i3++;
        }
        return i4;
    }

    public int encode(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4 = 0;
        int i5 = 0;
        while (i5 < i2) {
            bArr2[i3 + i4] = hexTable[(bArr[i] >> 4) & 15];
            bArr2[(i3 + i4) + 1] = hexTable[bArr[i] & 15];
            i++;
            i5++;
            i4 += 2;
        }
        return i2 * 2;
    }

    public int getDecodedBlockSize() {
        return 1;
    }

    public int getEncodedBlockSize() {
        return 2;
    }
}
