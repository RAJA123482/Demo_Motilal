package com.example.demo_motilal.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.demo_motilal.service.RepoSyncService

class BootBroadcastReceiver: BroadcastReceiver() {
    val action = "android.intent.action.BOOT_COMPLETED"
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals(action)) {
            val serviceIntent = Intent(context, RepoSyncService::class.java)
            context?.let {
                it.startService(serviceIntent)
            }
        }
    }
}