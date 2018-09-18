package dcns.ru.gpsclient;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

public class PhoneInfo extends AppCompatActivity {

    private TelephonyManager tm;
    private static final int REQUEST_ACCESS_READ_PHONE_STATE = 1;

    public String[] allInfo() {

        int REQUEST_READ_PHONE_STATE = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (REQUEST_READ_PHONE_STATE != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_ACCESS_READ_PHONE_STATE);
        }

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String IMEINumber = tm.getDeviceId();
        String subscriberID = tm.getDeviceId();
        String SIMSerialNumber = tm.getSimSerialNumber();
        String networkCountryISO = tm.getNetworkCountryIso();
        String SIMCountryISO = tm.getSimCountryIso();
        String softwareVersion = tm.getDeviceSoftwareVersion();
        String voiceMailNumber = tm.getVoiceMailNumber();

        int phoneType = tm.getPhoneType();

        String phoneTypeInt = "";

        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                phoneTypeInt = "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                phoneTypeInt = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                phoneTypeInt = "NONE";
                break;
        }

        boolean isRoaming = tm.isNetworkRoaming();

        String[] info = {
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return;
        }
    }

}
