package com.fit2081.fit2081_a1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    // used as a channel to broadcast the message
    // Any application aware of this channel can listen to the broadcasts
    public static final String SMS_FILTER = "SMS_FILTER";

    // Within the broadcast, we would like to send information
    // and this will be the key to identifying that information, in this case, the SMS message
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for (int i = 0; i < messages.length; i++) {
            SmsMessage currentMessage = messages[i];
            String senderNum = currentMessage.getDisplayOriginatingAddress();
            String message = currentMessage.getDisplayMessageBody();
            Toast.makeText(context,"Sender: "+ senderNum + ", message: " + message, Toast.LENGTH_LONG).show();

            sendBroadcastSMSReceived(context, message);
        }
    }

    public void sendBroadcastSMSReceived(Context context, String message) {
        /*
         * For each new message, send a broadcast that contains the new message to Category/Event
         * The Category/Event will tokenize the new message
         * */
        Intent msgIntent = new Intent();
        msgIntent.setAction(SMS_FILTER);
        msgIntent.putExtra(SMS_MSG_KEY, message);
        context.sendBroadcast(msgIntent);
    }
}