package com.cqx.testspring.webservice.common.util.other;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;

/**
 * Guid
 *
 * @author chenqixu
 */
public class Guid {
    private static final char[] HEXADECIMAL_CHARACTERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final long UTC_OFFSET = 122192928000000000L;
    private static final short GUID_VERSION_1 = 4096;
    private static final byte GUID_RESERVED = -64;
    private static final int MAX_CLOCK_SEQ_ADJUST = 9999;
    private static final long MAX_WAIT_TIME = 1000L;
    private static final Object lockObject = new Object();
    private static boolean _internalsSet = false;
    private static long _lastTimestamp = 0L;
    private static int _clock_seq_adjust = 0;
    private static int _osProcessId = 0;
    private static int _rand_ia = 0;
    private static int _rand_ib = 0;
    private static int _rand_irand = 0;
    private static int _rand_m = 0;
    private static long clockSeq = 0L;
    private int time_low = 0;
    private short time_mid = 0;
    private short time_hi_and_version = 0;
    private byte clock_seq_hi_and_reserved = 0;
    private byte clock_seq_low = 0;
    private static byte[] _ieee802Addr = new byte[6];

    public Guid() {
        long adjustedTimestamp = 0L;
        synchronized (lockObject) {
            if (!_internalsSet) {
                this.getPseudoIEEE802Address(_ieee802Addr);
                _osProcessId = this.getPseudoOSProcessId();
                this.initTrueRandom(this.getAdjustedTimestamp());
                clockSeq = (long) this.getTrueRandom();
                _internalsSet = true;
            }

            boolean timeIsValid = true;

            do {
                adjustedTimestamp = this.getAdjustedTimestamp();
                if (adjustedTimestamp < _lastTimestamp) {
                    clockSeq = (long) this.getTrueRandom();
                    _clock_seq_adjust = 0;
                }

                if (adjustedTimestamp > _lastTimestamp) {
                    _clock_seq_adjust = 0;
                }

                if (adjustedTimestamp == _lastTimestamp) {
                    if (_clock_seq_adjust < 9999) {
                        ++_clock_seq_adjust;
                    } else {
                        timeIsValid = false;
                    }
                }
            } while (!timeIsValid);

            _lastTimestamp = adjustedTimestamp;
            if (_clock_seq_adjust != 0) {
                adjustedTimestamp += (long) _clock_seq_adjust;
            }

            long tempValue = adjustedTimestamp & -1L;
            this.time_low = (int) tempValue;
            tempValue = adjustedTimestamp >>> 32 & 65535L;
            this.time_mid = (short) ((int) tempValue);
            tempValue = adjustedTimestamp >>> 48 & 4095L;
            this.time_hi_and_version = (short) ((int) tempValue);
            this.time_hi_and_version = (short) (this.time_hi_and_version | 4096);
            tempValue = clockSeq & 255L;
            this.clock_seq_low = (byte) ((int) tempValue);
            tempValue = (clockSeq & 16128L) >>> 8;
            this.clock_seq_hi_and_reserved = (byte) ((int) tempValue);
            this.clock_seq_hi_and_reserved |= -64;
        }
    }

    private synchronized void initTrueRandom(long adjustedTimestamp) {
        _rand_m = 971;
        _rand_ia = 11113;
        _rand_ib = 104322;
        _rand_irand = 4181;
        int seed = (int) (adjustedTimestamp >>> 48) ^ (int) (adjustedTimestamp >>> 32) ^ (int) (adjustedTimestamp >>> 16) ^ (int) (adjustedTimestamp & 65535L);
        _rand_irand = _rand_irand + seed + _osProcessId;
    }

    private synchronized short getTrueRandom() {
        _rand_m += 7;
        _rand_ia += 1907;
        _rand_ib += 73939;
        if (_rand_m >= 9973) {
            _rand_m -= 9871;
        }

        if (_rand_ia >= 99991) {
            _rand_ia -= 89989;
        }

        if (_rand_ib >= 224729) {
            _rand_ib -= 96233;
        }

        _rand_irand = _rand_irand * _rand_m + _rand_ia + _rand_ib;
        _rand_irand = _rand_irand >>> 16 ^ _rand_irand & 16383;
        return (short) _rand_irand;
    }

    private synchronized int getPseudoOSProcessId() {
        return (int) (this.getUniqueTimeStamp() % 1000000000L);
    }

    private synchronized void getPseudoIEEE802Address(byte[] ieee802Addr) {
        byte[] currentTime = String.valueOf(this.getUniqueTimeStamp()).getBytes();
        byte[] localHostAddress = this.getLocalHostAddress();
        byte[] inMemObj = (new Object()).toString().getBytes();
        byte[] freeMemory = String.valueOf(Runtime.getRuntime().freeMemory()).getBytes();
        byte[] totalMemory = String.valueOf(Runtime.getRuntime().totalMemory()).getBytes();
        byte[] hashcode = null;
        byte[] bytes = new byte[freeMemory.length + totalMemory.length + currentTime.length + localHostAddress.length + inMemObj.length];
        int bytesPos = 0;
        System.arraycopy(currentTime, 0, bytes, bytesPos, currentTime.length);
        bytesPos = bytesPos + currentTime.length;
        System.arraycopy(localHostAddress, 0, bytes, bytesPos, localHostAddress.length);
        bytesPos += localHostAddress.length;
        System.arraycopy(inMemObj, 0, bytes, bytesPos, inMemObj.length);
        bytesPos += inMemObj.length;
        System.arraycopy(freeMemory, 0, bytes, bytesPos, freeMemory.length);
        bytesPos += freeMemory.length;
        System.arraycopy(totalMemory, 0, bytes, bytesPos, totalMemory.length);
        int var10000 = bytesPos + totalMemory.length;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            hashcode = md5.digest(bytes);
        } catch (Exception var11) {
        }

        System.arraycopy(hashcode, 0, ieee802Addr, 0, 6);
        ieee802Addr[0] = (byte) (ieee802Addr[0] | 128);
    }

    private synchronized byte[] getLocalHostAddress() {
        try {
            return InetAddress.getLocalHost().getAddress();
        } catch (UnknownHostException var2) {
            return new byte[]{127, 0, 0, 1};
        }
    }

    private synchronized long getAdjustedTimestamp() {
        return System.currentTimeMillis() * 10000L + 122192928000000000L;
    }

    private synchronized long getUniqueTimeStamp() {
        File lockFile = new File(System.getProperty("java.io.tmpdir"), "guid.lock");
        long timeStamp = System.currentTimeMillis();

        try {
            try {
                long lastModified = lockFile.lastModified();
                long maxWaitTimeStamp = System.currentTimeMillis() + 1000L;

                while (true) {
                    try {
                        if (lockFile.createNewFile()) {
                            break;
                        }

                        if (System.currentTimeMillis() > maxWaitTimeStamp) {
                            if (lockFile.lastModified() > lastModified) {
                                long var8 = System.currentTimeMillis();
                                return var8;
                            }

                            lockFile.delete();
                            lastModified = -1L;
                        }
                    } catch (IOException var23) {
                    }
                }

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException var22) {
                }

                timeStamp = System.currentTimeMillis();
            } catch (SecurityException var24) {
            }

            return timeStamp;
        } finally {
            try {
                if (lockFile != null) {
                    lockFile.delete();
                }
            } catch (SecurityException var21) {
            }

        }
    }

    public String toString() {
        char[] stringBuffer = new char[32];
        int pos = 0;
        pos = pos + 1;
        stringBuffer[pos] = HEXADECIMAL_CHARACTERS[this.clock_seq_hi_and_reserved >>> 4 & 15];
        stringBuffer[pos++] = HEXADECIMAL_CHARACTERS[this.clock_seq_hi_and_reserved & 15];
        stringBuffer[pos++] = HEXADECIMAL_CHARACTERS[this.clock_seq_low >>> 4 & 15];
        stringBuffer[pos++] = HEXADECIMAL_CHARACTERS[this.clock_seq_low & 15];

        for (int i = 0; pos < 16; pos += 2) {
            stringBuffer[pos] = HEXADECIMAL_CHARACTERS[_ieee802Addr[i] >>> 4 & 15];
            stringBuffer[pos + 1] = HEXADECIMAL_CHARACTERS[_ieee802Addr[i] & 15];
            ++i;
        }

        int shift;
        for (shift = 28; pos < 24; ++pos) {
            stringBuffer[pos] = HEXADECIMAL_CHARACTERS[this.time_low >>> shift & 15];
            shift -= 4;
        }

        for (shift = 12; pos < 28; ++pos) {
            stringBuffer[pos] = HEXADECIMAL_CHARACTERS[this.time_mid >>> shift & 15];
            shift -= 4;
        }

        for (shift = 12; pos < 32; ++pos) {
            stringBuffer[pos] = HEXADECIMAL_CHARACTERS[this.time_hi_and_version >>> shift & 15];
            shift -= 4;
        }

        return (new String(stringBuffer)).trim();
    }

    public static String generate() {
        return (new Guid()).toString();
    }
}
