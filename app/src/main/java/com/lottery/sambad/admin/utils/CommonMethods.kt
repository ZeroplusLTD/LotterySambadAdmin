package com.lottery.sambad.admin.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.webkit.MimeTypeMap
import com.lottery.sambad.admin.BuildConfig
import org.json.JSONObject
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

object CommonMethods {


    fun increaseDecreaseDaysUsingValue(days: Int): String {
        val calendar: Calendar =Calendar.getInstance()
        val simpleDateFormat= SimpleDateFormat(Constants.dayFormat,Locale.getDefault())
        calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)+days)
        return simpleDateFormat.format(calendar.time)
    }

    fun getCurrentTimeUsingFormat(format: String): String {
        val dateFormat: SimpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(Date()).toString()
    }

    fun getCurrentTime(): String {
        val date = Date()
        return date.time.toString()
    }

    fun getDayNameUsingDate(date: String): String {
        try {
            val format=SimpleDateFormat(Constants.dayFormat, Locale.getDefault())
            val formattedDate=format.parse(date)
            val simpleDateFormat=SimpleDateFormat("EEEE", Locale.getDefault())
            return simpleDateFormat.format(formattedDate)
        } catch (e: Exception) {
            return "day name not found"
        }
    }

    fun getHoursDifBetweenToTime(startTime: String, endTime: String): String? {
        return try {
            val date1 = startTime.toLong()
            val date2 = endTime.toLong()
            val difference = date2 - date1
            val differenceDates = difference / (60 * 60 * 1000)
            differenceDates.toString()
        } catch (exception: Exception) {
            null
        }
    }

    fun getMinuteDifBetweenToTime(startTime: String, endTime: String): String? {
        return try {
            val date1 = startTime.toLong()
            val date2 = endTime.toLong()
            val difference = date2 - date1
            val differenceDates = difference / (60 * 1000)
            differenceDates.toString()
        } catch (exception: Exception) {
            null
        }
    }

    fun getMimeTypeFromUrl(url: String): String? {
        var type: String?=null
        val typeExtension: String= MimeTypeMap.getFileExtensionFromUrl(url)
        if (!typeExtension.isNullOrEmpty()) {
            type=MimeTypeMap.getSingleton().getMimeTypeFromExtension(typeExtension)
        }
        return type
    }

    fun haveInternet(connectivityManager: ConnectivityManager?): Boolean {
        return when {
            connectivityManager==null -> {
                false
            }
            Build.VERSION.SDK_INT >= 23 -> {
                val network = connectivityManager.activeNetwork
                val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
            else -> {
                (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isAvailable
                        && connectivityManager.activeNetworkInfo!!.isConnected)
            }
        }
    }

//    fun sendNotification(title: String?, description: String?, targetUrl: String?): String? {
//        val message: String=if (description.isNullOrEmpty()) "Click here for more details." else description
//        if (title!=null) {
//            try {
//                var jsonResponse: String
//                val url: URL= URL("https://onesignal.com/api/v1/notifications")
//                val con: HttpURLConnection =url.openConnection() as HttpURLConnection
//                con.useCaches = false
//                con.doOutput = true
//                con.doInput = true
//
//                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
//                con.setRequestProperty("Authorization", "Basic NTM5NWFkMjItMjY3Yy00YWY1LWJhNzAtZTc4Mzk2YjMyNDlk")
//                con.setRequestProperty("Accept", "application/json")
//                con.requestMethod = "POST"
//
//                var notificationObject: JSONObject
//                if (targetUrl!=null) {
//                    notificationObject = JSONObject("{\n" +
//                            "    \"app_id\":\"556e5254-0798-4487-9cc6-e0946047c1d3\",\n" +
//                            "    \"included_segments\": [\"Subscribed Users\"],\n" +
//                            "    \"headings\": {\"en\":\"$title\"},\n" +
//                            "    \"app_url\":\"$targetUrl\",\n" +
////                        "    \"data\":{\"sendingTime\":\"$time\",\"developedBy\":\"EasySoftBd\"}\n" +
//                            "    \"contents\":{\"en\":\"$message\"}\n" +
//                            "}")
//                } else {
//                    notificationObject = JSONObject("{\n" +
//                            "    \"app_id\":\"556e5254-0798-4487-9cc6-e0946047c1d3\",\n" +
//                            "    \"included_segments\": [\"Subscribed Users\"],\n" +
//                            "    \"headings\": {\"en\":\"$title\"},\n" +
////                        "    \"data\":{\"sendingTime\":\"$time\",\"developedBy\":\"EasySoftBd\"}\n" +
//                            "    \"contents\":{\"en\":\"$message\"}\n" +
//                            "}")
//                }
//                val sendBytes: ByteArray= notificationObject.toString().toByteArray(Charsets.UTF_8)
//                con.setFixedLengthStreamingMode(sendBytes.size)
//                val outputStream: OutputStream =con.outputStream
//                outputStream.write(sendBytes)
//                val httpResponse: Int=con.responseCode
//                outputStream.close()
//                if (httpResponse>=HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
//                    val scanner: Scanner= Scanner(con.inputStream,"UTF-8")
//                    jsonResponse= if (scanner.useDelimiter("\\A").hasNext()) scanner.next() else ""
//                    scanner.close()
//                } else {
//                    val scanner: Scanner= Scanner(con.errorStream,"UTF-8")
//                    jsonResponse= if (scanner.useDelimiter("\\A").hasNext()) scanner.next() else ""
//                    scanner.close()
//                }
//                con.disconnect()
//                return jsonResponse
//            } catch (e: Exception) {
//                return null
//            }
//        }
//        return null
//    }




    fun sendNotification(title: String?, description: String?, targetUrl: String?): String? {
//        val message: String=if (description.isNullOrEmpty()) "Click here for more details." else description
        if (title!=null) {
            try {
                var jsonResponse: String
                val url: URL= URL("https://fcm.googleapis.com/fcm/send")
                val con: HttpURLConnection =url.openConnection() as HttpURLConnection
                con.useCaches = false
                con.doOutput = true
                con.doInput = true

                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                con.setRequestProperty("Authorization", "key=AAAAQYhqASI:APA91bFnEIkJDeoNuk-H_3R7QOR65HCxGe2RWl6HEcsfdI7ok5BQYUvenn6reyWWR8a_lva9zrV0nlcXBlwOezT-I0z4fXkMAl8Jexdln4iL0CDXKrN9B7U8EVPUt_wY2AMf0JM8MciS")
                con.setRequestProperty("Accept", "application/json")
                con.requestMethod = "POST"

                var rootObject: JSONObject= JSONObject()
                if (targetUrl==null) {
                    try {
                        val notificationObject: JSONObject= JSONObject()
                        notificationObject.put("title",title)
                        notificationObject.put("body",description)

                        rootObject.put("to","/topics/${Constants.userTypeFree}")
                        rootObject.put("notification",notificationObject)
                        rootObject.put("direct_boot_ok",true)
                    } catch (e: Exception) {
                        Log.d(Constants.TAG,"failed to send notification for:- ${e.message}")
                    }

                } else {
//                    rootObject = JSONObject("{\n" +
//                            "    \"app_id\":\"556e5254-0798-4487-9cc6-e0946047c1d3\",\n" +
//                            "    \"included_segments\": [\"Subscribed Users\"],\n" +
//                            "    \"headings\": {\"en\":\"$title\"},\n" +
////                        "    \"data\":{\"sendingTime\":\"$time\",\"developedBy\":\"EasySoftBd\"}\n" +
//                            "    \"contents\":{\"en\":\"$message\"}\n" +
//                            "}")
                }
                val sendBytes: ByteArray= rootObject.toString().toByteArray(Charsets.UTF_8)
                con.setFixedLengthStreamingMode(sendBytes.size)
                val outputStream: OutputStream =con.outputStream
                outputStream.write(sendBytes)
                val httpResponse: Int=con.responseCode
                outputStream.close()
                if (httpResponse>=HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                    val scanner: Scanner= Scanner(con.inputStream,"UTF-8")
                    jsonResponse= if (scanner.useDelimiter("\\A").hasNext()) scanner.next() else ""
                    scanner.close()
                } else {
                    val scanner: Scanner= Scanner(con.errorStream,"UTF-8")
                    jsonResponse= if (scanner.useDelimiter("\\A").hasNext()) scanner.next() else ""
                    scanner.close()
                }
                con.disconnect()
                return jsonResponse
            } catch (e: Exception) {
                return null
            }
        }
        return null
    }



}