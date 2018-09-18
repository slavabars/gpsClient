package dcns.ru.gpsclient;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

public class PhoneInfo extends ActivityCompat{
    public String[] allInfo() {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String IMEINumber = tm.getDeviceId();
        String subscriberID = tm.getDeviceId();
        String SIMSerialNumber = tm.getSimSerialNumber();
        String networkCountryISO=tm.getNetworkCountryIso();
        String SIMCountryISO=tm.getSimCountryIso();
        String softwareVersion=tm.getDeviceSoftwareVersion();
        String voiceMailNumber=tm.getVoiceMailNumber();

        int phoneType=tm.getPhoneType();

        String phoneTypeInt="";

        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                phoneTypeInt="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                phoneTypeInt="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                phoneTypeInt="NONE";
                break;
        }

        boolean isRoaming=tm.isNetworkRoaming();

        String[] info={
                IMEINumber,
                subscriberID,
                SIMSerialNumber,
                networkCountryISO,
                SIMCountryISO,
                softwareVersion,
                voiceMailNumber,
                phoneTypeInt,
                String.valueOf(isRoaming)
        };

        return info;
    }

}
