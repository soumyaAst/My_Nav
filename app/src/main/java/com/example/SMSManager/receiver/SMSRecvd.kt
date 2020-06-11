package com.example.SMSManager.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import androidx.annotation.RequiresApi
import com.example.SMSManager.objects.MsgObj
import com.example.SMSManager.util.MainActivity

class SMSRecvd: BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onReceive(context: Context?, intent: Intent?) {

        var instance= MainActivity.instance()
        var msg= Telephony.Sms.Intents.getMessagesFromIntent(intent)
            instance.addsome(
                MsgObj(
                    msg[0].displayOriginatingAddress,
                    msg[0].messageBody,
                    ""
                )
            )
        //instance.addsome(MsgObj("hi","hello"))
    }
}