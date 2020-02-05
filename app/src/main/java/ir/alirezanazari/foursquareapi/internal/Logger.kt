package ir.alirezanazari.foursquareapi.internal

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ir.alirezanazari.foursquareapi.BuildConfig


class Logger {

    companion object{

        fun showToast(context: Context? , message: String){
            Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
        }

        fun showToast(context: Context? , message: Int){
            Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
        }

        fun showSnack(view: View , message: String){
            Snackbar.make(view , message , Snackbar.LENGTH_SHORT).show()
        }

        fun showSnack(view: View , message: Int){
            Snackbar.make(view , message , Snackbar.LENGTH_SHORT).show()
        }

        fun showLog(text: String?){
            if (BuildConfig.DEBUG) Log.wtf("FourSquareLog" , text)
        }
    }
}