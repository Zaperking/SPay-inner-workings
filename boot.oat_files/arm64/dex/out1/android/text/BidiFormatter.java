package android.text;

import android.text.format.DateFormat;
import com.samsung.android.contextaware.dataprovider.sensorhubprovider.ISensorHubCmdProtocol;
import java.util.Locale;

public final class BidiFormatter {
    private static final int DEFAULT_FLAGS = 2;
    private static final BidiFormatter DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    private static final BidiFormatter DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    private static TextDirectionHeuristic DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristics.FIRSTSTRONG_LTR;
    private static final int DIR_LTR = -1;
    private static final int DIR_RTL = 1;
    private static final int DIR_UNKNOWN = 0;
    private static final String EMPTY_STRING = "";
    private static final int FLAG_STEREO_RESET = 2;
    private static final char LRE = '‪';
    private static final char LRM = '‎';
    private static final String LRM_STRING = Character.toString(LRM);
    private static final char PDF = '‬';
    private static final char RLE = '‫';
    private static final char RLM = '‏';
    private static final String RLM_STRING = Character.toString(RLM);
    private final TextDirectionHeuristic mDefaultTextDirectionHeuristic;
    private final int mFlags;
    private final boolean mIsRtlContext;

    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private TextDirectionHeuristic mTextDirectionHeuristic;

        public Builder() {
            initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
        }

        public Builder(boolean rtlContext) {
            initialize(rtlContext);
        }

        public Builder(Locale locale) {
            initialize(BidiFormatter.isRtlLocale(locale));
        }

        private void initialize(boolean isRtlContext) {
            this.mIsRtlContext = isRtlContext;
            this.mTextDirectionHeuristic = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public Builder stereoReset(boolean stereoReset) {
            if (stereoReset) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
            return this;
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristic heuristic) {
            this.mTextDirectionHeuristic = heuristic;
            return this;
        }

        private static BidiFormatter getDefaultInstanceFromContext(boolean isRtlContext) {
            return isRtlContext ? BidiFormatter.DEFAULT_RTL_INSTANCE : BidiFormatter.DEFAULT_LTR_INSTANCE;
        }

        public BidiFormatter build() {
            if (this.mFlags == 2 && this.mTextDirectionHeuristic == BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC) {
                return getDefaultInstanceFromContext(this.mIsRtlContext);
            }
            return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristic);
        }
    }

    private static class DirectionalityEstimator {
        private static final byte[] DIR_TYPE_CACHE = new byte[DIR_TYPE_CACHE_SIZE];
        private static final int DIR_TYPE_CACHE_SIZE = 1792;
        private int charIndex;
        private final boolean isHtml;
        private char lastChar;
        private final int length;
        private final String text;

        static {
            for (int i = 0; i < DIR_TYPE_CACHE_SIZE; i++) {
                DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
            }
        }

        DirectionalityEstimator(String text, boolean isHtml) {
            this.text = text;
            this.isHtml = isHtml;
            this.length = text.length();
        }

        int getEntryDir() {
            this.charIndex = 0;
            int embeddingLevel = 0;
            int embeddingLevelDir = 0;
            int firstNonEmptyEmbeddingLevel = 0;
            while (this.charIndex < this.length && firstNonEmptyEmbeddingLevel == 0) {
                switch (dirTypeForward()) {
                    case (byte) 0:
                        if (embeddingLevel != 0) {
                            firstNonEmptyEmbeddingLevel = embeddingLevel;
                            break;
                        }
                        return -1;
                    case (byte) 1:
                    case (byte) 2:
                        if (embeddingLevel != 0) {
                            firstNonEmptyEmbeddingLevel = embeddingLevel;
                            break;
                        }
                        return 1;
                    case (byte) 9:
                        break;
                    case (byte) 14:
                    case (byte) 15:
                        embeddingLevel++;
                        embeddingLevelDir = -1;
                        break;
                    case (byte) 16:
                    case (byte) 17:
                        embeddingLevel++;
                        embeddingLevelDir = 1;
                        break;
                    case (byte) 18:
                        embeddingLevel--;
                        embeddingLevelDir = 0;
                        break;
                    default:
                        firstNonEmptyEmbeddingLevel = embeddingLevel;
                        break;
                }
            }
            if (firstNonEmptyEmbeddingLevel == 0) {
                return 0;
            }
            if (embeddingLevelDir != 0) {
                return embeddingLevelDir;
            }
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case (byte) 14:
                    case (byte) 15:
                        if (firstNonEmptyEmbeddingLevel != embeddingLevel) {
                            embeddingLevel--;
                            break;
                        }
                        return -1;
                    case (byte) 16:
                    case (byte) 17:
                        if (firstNonEmptyEmbeddingLevel != embeddingLevel) {
                            embeddingLevel--;
                            break;
                        }
                        return 1;
                    case (byte) 18:
                        embeddingLevel++;
                        break;
                    default:
                        break;
                }
            }
            return 0;
        }

        int getExitDir() {
            this.charIndex = this.length;
            int embeddingLevel = 0;
            int lastNonEmptyEmbeddingLevel = 0;
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case (byte) 0:
                        if (embeddingLevel != 0) {
                            if (lastNonEmptyEmbeddingLevel != 0) {
                                break;
                            }
                            lastNonEmptyEmbeddingLevel = embeddingLevel;
                            break;
                        }
                        return -1;
                    case (byte) 1:
                    case (byte) 2:
                        if (embeddingLevel != 0) {
                            if (lastNonEmptyEmbeddingLevel != 0) {
                                break;
                            }
                            lastNonEmptyEmbeddingLevel = embeddingLevel;
                            break;
                        }
                        return 1;
                    case (byte) 9:
                        break;
                    case (byte) 14:
                    case (byte) 15:
                        if (lastNonEmptyEmbeddingLevel != embeddingLevel) {
                            embeddingLevel--;
                            break;
                        }
                        return -1;
                    case (byte) 16:
                    case (byte) 17:
                        if (lastNonEmptyEmbeddingLevel != embeddingLevel) {
                            embeddingLevel--;
                            break;
                        }
                        return 1;
                    case (byte) 18:
                        embeddingLevel++;
                        break;
                    default:
                        if (lastNonEmptyEmbeddingLevel != 0) {
                            break;
                        }
                        lastNonEmptyEmbeddingLevel = embeddingLevel;
                        break;
                }
            }
            return 0;
        }

        private static byte getCachedDirectionality(char c) {
            return c < '܀' ? DIR_TYPE_CACHE[c] : Character.getDirectionality(c);
        }

        byte dirTypeForward() {
            this.lastChar = this.text.charAt(this.charIndex);
            if (Character.isHighSurrogate(this.lastChar)) {
                int codePoint = Character.codePointAt(this.text, this.charIndex);
                this.charIndex += Character.charCount(codePoint);
                return Character.getDirectionality(codePoint);
            }
            this.charIndex++;
            byte dirType = getCachedDirectionality(this.lastChar);
            if (!this.isHtml) {
                return dirType;
            }
            if (this.lastChar == '<') {
                return skipTagForward();
            }
            if (this.lastChar == '&') {
                return skipEntityForward();
            }
            return dirType;
        }

        byte dirTypeBackward() {
            this.lastChar = this.text.charAt(this.charIndex - 1);
            if (Character.isLowSurrogate(this.lastChar)) {
                int codePoint = Character.codePointBefore(this.text, this.charIndex);
                this.charIndex -= Character.charCount(codePoint);
                return Character.getDirectionality(codePoint);
            }
            this.charIndex--;
            byte dirType = getCachedDirectionality(this.lastChar);
            if (!this.isHtml) {
                return dirType;
            }
            if (this.lastChar == '>') {
                return skipTagBackward();
            }
            if (this.lastChar == ';') {
                return skipEntityBackward();
            }
            return dirType;
        }

        private byte skipTagForward() {
            int initialCharIndex = this.charIndex;
            while (this.charIndex < this.length) {
                String str = this.text;
                int i = this.charIndex;
                this.charIndex = i + 1;
                this.lastChar = str.charAt(i);
                if (this.lastChar == '>') {
                    return ISensorHubCmdProtocol.TYPE_ENVIRONMENT_SENSOR_SERVICE;
                }
                if (this.lastChar == '\"' || this.lastChar == DateFormat.QUOTE) {
                    char quote = this.lastChar;
                    while (this.charIndex < this.length) {
                        str = this.text;
                        i = this.charIndex;
                        this.charIndex = i + 1;
                        char charAt = str.charAt(i);
                        this.lastChar = charAt;
                        if (charAt == quote) {
                            break;
                        }
                    }
                }
            }
            this.charIndex = initialCharIndex;
            this.lastChar = '<';
            return (byte) 13;
        }

        private byte skipTagBackward() {
            int initialCharIndex = this.charIndex;
            while (this.charIndex > 0) {
                String str = this.text;
                int i = this.charIndex - 1;
                this.charIndex = i;
                this.lastChar = str.charAt(i);
                if (this.lastChar == '<') {
                    return ISensorHubCmdProtocol.TYPE_ENVIRONMENT_SENSOR_SERVICE;
                }
                if (this.lastChar == '>') {
                    break;
                } else if (this.lastChar == '\"' || this.lastChar == DateFormat.QUOTE) {
                    char quote = this.lastChar;
                    while (this.charIndex > 0) {
                        str = this.text;
                        i = this.charIndex - 1;
                        this.charIndex = i;
                        char charAt = str.charAt(i);
                        this.lastChar = charAt;
                        if (charAt == quote) {
                            break;
                        }
                    }
                }
            }
            this.charIndex = initialCharIndex;
            this.lastChar = '>';
            return (byte) 13;
        }

        private byte skipEntityForward() {
            while (this.charIndex < this.length) {
                String str = this.text;
                int i = this.charIndex;
                this.charIndex = i + 1;
                char charAt = str.charAt(i);
                this.lastChar = charAt;
                if (charAt == ';') {
                    break;
                }
            }
            return ISensorHubCmdProtocol.TYPE_ENVIRONMENT_SENSOR_SERVICE;
        }

        private byte skipEntityBackward() {
            int initialCharIndex = this.charIndex;
            while (this.charIndex > 0) {
                String str = this.text;
                int i = this.charIndex - 1;
                this.charIndex = i;
                this.lastChar = str.charAt(i);
                if (this.lastChar != '&') {
                    if (this.lastChar == ';') {
                        break;
                    }
                }
                return ISensorHubCmdProtocol.TYPE_ENVIRONMENT_SENSOR_SERVICE;
            }
            this.charIndex = initialCharIndex;
            this.lastChar = ';';
            return (byte) 13;
        }
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    public static BidiFormatter getInstance(boolean rtlContext) {
        return new Builder(rtlContext).build();
    }

    public static BidiFormatter getInstance(Locale locale) {
        return new Builder(locale).build();
    }

    private BidiFormatter(boolean isRtlContext, int flags, TextDirectionHeuristic heuristic) {
        this.mIsRtlContext = isRtlContext;
        this.mFlags = flags;
        this.mDefaultTextDirectionHeuristic = heuristic;
    }

    public boolean isRtlContext() {
        return this.mIsRtlContext;
    }

    public boolean getStereoReset() {
        return (this.mFlags & 2) != 0;
    }

    public String markAfter(String str, TextDirectionHeuristic heuristic) {
        boolean isRtl = heuristic.isRtl((CharSequence) str, 0, str.length());
        if (!this.mIsRtlContext && (isRtl || getExitDir(str) == 1)) {
            return LRM_STRING;
        }
        if (!this.mIsRtlContext || (isRtl && getExitDir(str) != -1)) {
            return "";
        }
        return RLM_STRING;
    }

    public String markBefore(String str, TextDirectionHeuristic heuristic) {
        boolean isRtl = heuristic.isRtl((CharSequence) str, 0, str.length());
        if (!this.mIsRtlContext && (isRtl || getEntryDir(str) == 1)) {
            return LRM_STRING;
        }
        if (!this.mIsRtlContext || (isRtl && getEntryDir(str) != -1)) {
            return "";
        }
        return RLM_STRING;
    }

    public boolean isRtl(String str) {
        return this.mDefaultTextDirectionHeuristic.isRtl((CharSequence) str, 0, str.length());
    }

    public String unicodeWrap(String str, TextDirectionHeuristic heuristic, boolean isolate) {
        if (str == null) {
            return null;
        }
        boolean isRtl = heuristic.isRtl((CharSequence) str, 0, str.length());
        StringBuilder result = new StringBuilder();
        if (getStereoReset() && isolate) {
            result.append(markBefore(str, isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR));
        }
        if (isRtl != this.mIsRtlContext) {
            result.append(isRtl ? RLE : LRE);
            result.append(str);
            result.append(PDF);
        } else {
            result.append(str);
        }
        if (isolate) {
            result.append(markAfter(str, isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR));
        }
        return result.toString();
    }

    public String unicodeWrap(String str, TextDirectionHeuristic heuristic) {
        return unicodeWrap(str, heuristic, true);
    }

    public String unicodeWrap(String str, boolean isolate) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristic, isolate);
    }

    public String unicodeWrap(String str) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristic, true);
    }

    private static boolean isRtlLocale(Locale locale) {
        return TextUtils.getLayoutDirectionFromLocale(locale) == 1;
    }

    private static int getExitDir(String str) {
        return new DirectionalityEstimator(str, false).getExitDir();
    }

    private static int getEntryDir(String str) {
        return new DirectionalityEstimator(str, false).getEntryDir();
    }
}
