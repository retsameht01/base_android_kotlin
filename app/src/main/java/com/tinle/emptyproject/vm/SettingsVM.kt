package com.tinle.emptyproject.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.system.ErrnoException
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.core.PreferenceStore
import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Exception
import java.net.Socket
import javax.inject.Inject

class SettingsVM @Inject constructor(
        private val prefStore: PreferenceStore,
        private val executor:AppExecutor

):ViewModel() {

    private val DEFAULT_PORT = 54321
    private var serverString:MutableLiveData<String> = MutableLiveData()

    fun getServerString():LiveData<String> {
        return serverString
    }

    fun saveAPI(apiValue: String) {
        prefStore.saveAPI(apiValue)
    }

    fun getAPIValue():String{
        return prefStore.getAPI()
    }

    fun getCommIP():String{
        return prefStore.getCommIP()
    }

    fun saveCommIP(ip:String) {
        return prefStore.saveCommIP(ip)
    }

    private var socket: Socket? = null
    private var dataOutputStream: DataOutputStream? = null
    private var dataInputStream: DataInputStream? = null

    fun doSocketConnection(ip:String, msg: String) {
        executor.networkIO().execute {
            try {
                socket = Socket(ip, DEFAULT_PORT)
                dataOutputStream = DataOutputStream(socket?.getOutputStream())
                dataInputStream = DataInputStream(socket?.getInputStream())


                var inString = dataInputStream?.readUTF()
                serverString.postValue(inString)
                println("input string $inString")


                var outString = "txt from client: $msg"
                dataOutputStream?.writeUTF(outString)

                socket?.let {
                    it.close()
                }

                dataOutputStream?.let{
                    it.close()
                }

                dataInputStream?.let {
                    it.close()
                }
            }
            catch (e: ErrnoException) {
                println("Connection refused ${e.message}")
            }
            catch (ex:Exception) {
                println("Connection refused, msg: ${ex.message}")
            }
        }
    }


}